## SpringBoot DDD海龟汤游戏主持人记忆服务架构设计

海龟汤游戏是一种基于问答的逻辑推理游戏，其核心机制是主持人提供一个离奇的事件描述（汤面），玩家通过"是/否/无关"形式的提问来逐步缩小范围，最终推理出事件背后的真实原因（汤底）。**海龟汤游戏的记忆服务需要高效处理用户身份验证、权限管理、题目存储与对话上下文记录等关键功能**，并确保在高并发场景下的性能和一致性。本方案基于SpringBoot框架和DDD（领域驱动设计）规范，采用MySQL、MongoDB和Redis三种数据库的最佳实践，为海龟汤游戏主持人记忆服务构建了一个可扩展、高可用的架构。

### 一、领域模型设计与上下文边界划分

海龟汤游戏的记忆服务可划分为两个核心子域：用户数据子域和海龟汤数据子域。通过明确的上下文边界划分，确保了系统的模块化和可维护性。

用户数据子域主要关注用户身份验证、角色权限管理及游戏会话状态。其核心聚合根包括：

```java
@Document
public class UserAggregate {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private List<Role> roles;
    private List<GameSession> gameSessions;
}

@Document
public class Role {
    @Id
    private ObjectId id;
    private String name;
    private List<Permission> permissions;
}

@Document
public class Permission {
    @Id
    private ObjectId id;
    private String name;
    private String description;
}
```

海龟汤数据子域则聚焦于题目内容管理、对话上下文记录和答案验证。其核心聚合根包括：

```java
@Document
public class QuestionAggregate {
    @Id
    private ObjectId id;
    private String story;
    private String truth;
    private List<String> tips;
    private String difficulty;
    private List<String> tags;
    private boolean isSolved;
}

@Document
public class DialogueAggregate {
    @Id
    private ObjectId id;
    private ObjectId questionId;
    private String sessionId;
    private List<DialogueRecord> dialogueRecords;
    private int currentProgress;
    private int remainingQuestions;
}

public class DialogueRecord {
    private String questionText;
    private String answer; // "是/否/无关"
    private LocalDateTime timestamp;
}
```

**两个子域通过明确的上下文边界实现解耦**，用户子域通过GameSession与海龟汤子域关联，确保权限控制（如DM角色才能主持游戏）。领域事件机制（如QuestionSolvedEvent）用于跨上下文通信，当题目被解答时，会触发用户子域的积分更新或成就解锁，而无需直接依赖。

### 二、数据库选择与最佳实践

根据海龟汤游戏的特点，选择MySQL、MongoDB和Redis三种数据库，并针对每个数据库的特点设计最佳实践方案。

用户数据存储于MySQL中，主要优势在于其ACID特性，适合处理需要事务支持的场景，如用户权限变更。用户表结构示例：

| 字段 | 类型 | 描述 |
|------|------|------|
| id | BIGINT | 自增主键 |
| username | VARCHAR(64) | 用户名 |
| password | VARCHAR(128) | 密码（哈希存储） |
| role_id | BIGINT | 角色外键 |

角色与权限采用多对多关系，通过中间表实现：

```sql
CREATE TABLE user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE role_permission (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);
```

海龟汤题目数据存储于MongoDB中，主要优势在于其灵活的文档模型，适合处理非结构化或半结构化数据，如汤面、汤底等文本内容。题目集合结构示例：

```json
{
    "_id": ObjectId("..."),
    "story": "某人在雪地里发现一只死龟",
    "truth": "海龟是被冻死的，因为其体内含有抗冻蛋白",
    "tips": [
        "海龟生存环境与温度有关",
        "海龟体内含有特殊蛋白质"
    ],
    "difficulty": "简单",
    "tags": [
        "动物",
        "生物学"
    ],
    "isSolved": false
}
```

**对话上下文缓存于Redis中**，主要优势在于其高性能的读写能力，适合处理高并发场景下的对话记录。对话上下文采用有序集合和哈希两种数据结构：

| 数据结构 | 键 | 字段/元素 | 用途 |
|----------|----|-----------|------|
| 有序集合 | dialogue:question_123:session_456 | question_text: "海龟是被冻死的吗？", timestamp | 记录提问序列 |
| 哈希 | session:456 | current_progress: 5, remaining_questions: 10 | 存储会话状态 |

**索引策略**对于提升数据库性能至关重要。在MongoDB中，为tags和difficulty字段创建复合索引：

```java
@CompoundIndex def {"tags": 1, "difficulty": 1})
public class TortoiseSoupQuestion {
    // 字段定义
}
```

对话记录集合则需为question_id和session_id创建复合索引：

```java
@CompoundIndex def {"question_id": 1, "session_id": 1, "timestamp": -1})
public class DialogueRecord {
    // 字段定义
}
```

### 三、SpringBoot DDD分层架构实现

基于DDD规范的SpringBoot项目架构分为四层，每层承担特定职责，确保业务逻辑与技术实现的解耦。

领域层（domain）是项目的业务核心，包含领域模型、领域服务和聚合根。例如DialogueProgressService领域服务负责验证答案并更新对话上下文：

```java
@Service
public class DialogueProgressService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private DialogueRecordRepository dialogueRecordRepository;
    
    public void validateAnswer(String answer, ObjectId questionId, String sessionId) {
        TortoiseSoupQuestion question = questionRepository.findById(questionId);
        DialogueContext dialogueContext = dialogueRecordRepository.findByQuestionIdAndSessionId(questionId, sessionId);
        
        // 验证答案逻辑
        if (answer.equals(question.getTruth())) {
            question.setIsSolved(true);
            dialogueContext.markAsSolved();
            // 发布领域事件
            eventPublisher.publishEvent(new QuestionSolvedEvent(question));
        }
    }
}
```

应用层（application）负责协调领域服务完成业务用例。采用命令模式（Command/Query）分离设计，例如StartGameUseCase启动游戏会话：

```java
@UseCase
public class StartGameUseCase {
    @Autowired
    private DialogueProgressService dialogueProgressService;
    
    public void execute(String userId, ObjectId questionId) {
        dialogueProgressService.startNewDialogueContext(userId, questionId);
    }
}
```

基础设施层（infrastructure）提供技术相关的支持，包括数据库访问、缓存操作和事件处理。例如DialogueContextRepository实现Redis对话上下文操作：

```java
@Repository
public class RedisDialogueContextRepository implements DialogueContextRepository {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Override
    public void addDialogueRecord(String sessionId, DialogueRecord record) {
        String dialogueKey = "dialogue:" + sessionId;
        redisTemplate.opsForZSet().add(dialogueKey, record.getQuestionText(), record.getTimestamp().toEpochSecond());
    }
    
    @Override
    public Map<String, Object> getSessionState(String sessionId) {
        return redisTemplate.opsForHash().entries("session:" + sessionId);
    }
}
```

接口层（interface）负责与外部交互，包括接收HTTP请求和消息监听。例如DialogueRecordController处理对话记录提交：

```java
@Tag(name = "对话记录", description = "对话记录管理")
@RestController
@RequestMapping("/api/v1/dialogue")
public class DialogueRecordController {
    @Autowired
    private CommandExecutor commandExecutor;
    
    @PostMapping("/create")
    public ResponseEntity<DialogueResponse> createDialogueRecord(
        @RequestBody DialogueRecordRequest request
    ) {
        DialogueRecordCmd cmd = new DialogueRecordCmd(
            request.getQuestionId(), 
            request.getSessionId(), 
            request.getQuestionText()
        );
        DialogueRecord record = commandExecutor.execute(cmd);
        return ResponseEntity.ok(new DialogueResponse(record));
    }
}
```

### 四、缓存策略与API接口设计

**缓存策略**是确保系统在高并发场景下性能的关键。对话上下文采用Redis的有序集合记录提问序列，哈希结构存储会话状态。缓存失效策略包括主动失效（会话结束时触发）和TTL失效（24小时未活跃会话自动过期）。

```java
// 使用Lua脚本实现原子性操作
public class DialogueCacheService {
    private static final String LuaScript = "local dialogueKey = KEYS[1] " +
        "local sessionKey = KEYS[2] " +
        "local record = ARGV[1] " +
        "local timestamp = ARGV[2] " +
        "local remaining = ARGV[3] " +
        "redis.call('ZADD', dialogueKey, timestamp, record) " +
        "redis.call('HSET', sessionKey, 'remaining_questions', remaining) " +
        "return remaining";
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    public void updateDialogueCache(DialogueRecord record, String sessionId) {
        String dialogueKey = "dialogue:" + record.getSessionId();
        String sessionKey = "session:" + record.getSessionId();
        
        RedisScript<Long> script = new DefaultRedisScript<>(LuaScript, Long.class);
        redisTemplate.execute(
            script,
            Arrays.asList(dialogueKey, sessionKey),
            record.getQuestionText(),
            record.getTimestamp().toEpochSecond(),
            record.getRemainingQuestions()
        );
    }
}
```

**API接口设计**采用RESTful风格，路径清晰且符合资源命名规范。主要接口包括：

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/v1/questions | 提交新题目（需DM权限） |
| GET | /api/v1/questions?tags=犯罪&difficulty=简单 | 按标签和难度查询题目 |
| POST | /api/v1/questions/{questionId}/dialogue | 提交提问，返回回答结果 |
| GET | /api/v1/sessions/{sessionId} | 查询会话状态（进度、剩余提问次数） |
| POST | /api/v1/questions/{questionId}/validate-answer | 验证答案并更新题目状态 |

API响应格式统一，包含status、message、data三个核心字段：

```json
{
    "status": "success",
    "message": "Dialogue record created",
    "data": {
        "answer": "是",
        "timestamp": "2025-05-04T10:00:00Z"
    }
}
```

**高并发写入优化**对于对话记录至关重要。通过RedisPipeline实现批量写入，减少网络往返次数：

```java
public void batchAddDialogueRecords(List<DialogueRecord> records) {
    String pipelineKey = "dialogue:" + records.get(0).getSessionId();
    
    redisTemplate.executePipelined((RedisCallback<String>) connection -> {
        records.forEach(record -> {
            connection.zAdd(
                pipelineKey.getBytes(),
                record.getTimestamp().toEpochSecond(),
                record.getQuestionText().getBytes()
            );
        });
        return null;
    });
}
```

### 五、领域事件驱动架构与一致性保障

**领域事件驱动架构**是实现跨上下文通信和数据同步的关键机制。当领域服务完成特定操作后，发布领域事件，基础设施层订阅并执行相应的缓存更新或数据同步操作。

领域事件示例：

```java
public class DialogueRecordCreatedEvent extends ApplicationEvent {
    private DialogueRecord dialogueRecord;
    
    public DialogueRecordCreatedEvent(Object source, DialogueRecord dialogueRecord) {
        super(source);
        this.dialogueRecord = dialogueRecord;
    }
    
    public DialogueRecord getDialogueRecord() {
        return dialogueRecord;
    }
}
```

事件监听器示例：

```java
@Component
public class DialogueCacheHandler {
    @Autowired
    private DialogueContextRepository dialogueContextRepository;
    
    @EventListener
    public void handleDialogueRecordCreated(DialogueRecordCreatedEvent event) {
        DialogueRecord record = event.getDialogueRecord();
        dialogueContextRepository.addDialogueRecord(
            record.getSessionId(),
            record
        );
    }
}
```

**数据一致性保障**采用最终一致性模型，确保缓存与数据库在一定时间内达到一致。具体策略包括：

1. **缓存更新顺序**：先更新MongoDB，再更新Redis缓存，确保缓存失效后能回源
2. **事件驱动同步**：通过领域事件触发缓存更新，减少延迟
3. **缓存预热**：启动时加载高频题目到Redis，提高响应速度
4. **击穿防护**：对热点题目使用互斥锁（SET question:{id} NX EX 3600）防止缓存穿透
5. **缓存失效**：设置TTL（如24小时），定期清理过期会话

### 六、性能优化与监控

**性能优化**是确保系统在高并发场景下稳定运行的关键。除了上述的缓存策略和数据库设计外，还可采用以下优化措施：

1. **连接池管理**：配置Redis连接池和MongoDB连接池，避免频繁创建连接
2. **异步写入**：将一些不需要实时响应的写操作异步处理，减少前台线程阻塞
3. **数据分片**：按session_id哈希分片到不同Redis节点，提升吞吐量
4. **限流控制**：使用Resilience4j或Ratelimiter4j实现接口限流，防止系统过载

**监控与日志**对于系统维护和问题排查至关重要。可通过Spring Boot Actuator和Prometheus实现监控：

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  metrics:
    export:
      prometheus:
        enabled: true
    tags:
      application: tortoisesoup-service
```

通过以上架构设计和实现方案，海龟汤游戏主持人记忆服务能够高效处理用户身份验证、权限管理、题目存储与对话上下文记录等核心功能，并在高并发场景下保持良好的性能和一致性。**这种基于DDD的多数据库混合架构不仅符合业务需求，也为系统的后续扩展和维护提供了坚实基础**。

说明：报告内容由通义AI生成，仅供参考。