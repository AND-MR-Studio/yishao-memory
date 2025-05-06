package org.space.yavin.alex.yishao.memory.domains.conversation.repository;

import org.space.yavin.alex.yishao.memory.domains.conversation.entity.MongoDialogHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MongoDB对话历史仓库接口
 */
@Repository
public interface MongoDialogHistoryRepository extends MongoRepository<MongoDialogHistory, String> {
    
    /**
     * 根据用户ID查找所有对话历史
     * @param userId 用户ID
     * @return 对话历史列表
     */
    List<MongoDialogHistory> findByUserId(String userId);
    
    /**
     * 根据汤ID查找所有对话历史
     * @param soupId 汤ID
     * @return 对话历史列表
     */
    List<MongoDialogHistory> findBySoupId(String soupId);
    
    /**
     * 根据用户ID和汤ID查找对话历史
     * @param userId 用户ID
     * @param soupId 汤ID
     * @return 对话历史（可能不存在）
     */
    Optional<MongoDialogHistory> findByUserIdAndSoupId(String userId, String soupId);
}