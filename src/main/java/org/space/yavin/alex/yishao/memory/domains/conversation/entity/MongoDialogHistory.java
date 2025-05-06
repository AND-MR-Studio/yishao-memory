package org.space.yavin.alex.yishao.memory.domains.conversation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * MongoDB存储的对话历史实体
 * 通过userId和soupId的复合索引可以快速查询用户在特定汤下的对话记录
 */
@Document(collection = "dialog_histories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
    @CompoundIndex(name = "user_soup_idx", def = "{userId: 1, soupId: 1}", unique = true)
})
public class MongoDialogHistory {
    
    @Id
    private String id;
    
    private String userId;
    
    private String soupId;
    
    private List<Message> messages = new ArrayList<>();
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    /**
     * 添加新消息到对话历史
     * @param message 要添加的消息
     */
    public void addMessage(Message message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
        this.updatedAt = LocalDateTime.now();
    }
}