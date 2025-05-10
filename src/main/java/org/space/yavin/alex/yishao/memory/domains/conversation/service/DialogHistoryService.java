package org.space.yavin.alex.yishao.memory.domains.conversation.service;

import lombok.RequiredArgsConstructor;
import org.space.yavin.alex.yishao.memory.domains.conversation.entity.Message;
import org.space.yavin.alex.yishao.memory.domains.conversation.entity.MongoDialogHistory;
import org.space.yavin.alex.yishao.memory.domains.conversation.repository.DialogHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * MongoDB对话历史服务
 */
@Service
@RequiredArgsConstructor
public class DialogHistoryService {

    private final DialogHistoryRepository repository;

    /**
     * 获取用户在特定汤下的对话历史
     * 如果不存在则创建新的对话历史
     *
     * @param userId 用户ID
     * @param soupId 汤ID
     * @return 对话历史
     */
    public MongoDialogHistory getOrCreateDialogHistory(String userId, String soupId) {
        return repository.findByUserIdAndSoupId(userId, soupId)
                .orElseGet(() -> {
                    MongoDialogHistory newHistory = new MongoDialogHistory();
                    newHistory.setUserId(userId);
                    newHistory.setSoupId(soupId);
                    newHistory.setCreatedAt(LocalDateTime.now());
                    newHistory.setUpdatedAt(LocalDateTime.now());
                    return repository.save(newHistory);
                });
    }

    /**
     * 添加消息到对话历史
     *
     * @param userId  用户ID
     * @param soupId  汤ID
     * @param message 消息
     * @return 更新后的对话历史
     */
    public MongoDialogHistory addMessage(String userId, String soupId, Message message) {
        MongoDialogHistory history = getOrCreateDialogHistory(userId, soupId);
        history.addMessage(message);
        return repository.save(history);
    }

    /**
     * 获取用户的所有对话历史
     *
     * @param userId 用户ID
     * @return 对话历史列表
     */
    public List<MongoDialogHistory> getDialogHistoriesByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    /**
     * 获取特定汤的所有对话历史
     *
     * @param soupId 汤ID
     * @return 对话历史列表
     */
    public List<MongoDialogHistory> getDialogHistoriesBySoupId(String soupId) {
        return repository.findBySoupId(soupId);
    }

    /**
     * 获取特定对话历史
     *
     * @param id 对话历史ID
     * @return 对话历史（可能不存在）
     */
    public Optional<MongoDialogHistory> getDialogHistoryById(String id) {
        return repository.findById(id);
    }

    /**
     * 删除对话历史
     *
     * @param id 对话历史ID
     */
    public void deleteDialogHistory(String id) {
        repository.deleteById(id);
    }
}