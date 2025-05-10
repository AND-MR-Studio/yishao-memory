package org.space.yavin.alex.yishao.memory.interfaces;

import lombok.RequiredArgsConstructor;
import org.space.yavin.alex.yishao.memory.domains.conversation.entity.Message;
import org.space.yavin.alex.yishao.memory.domains.conversation.entity.MongoDialogHistory;
import org.space.yavin.alex.yishao.memory.domains.conversation.service.MongoDialogHistoryService;
import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.space.yavin.alex.yishao.memory.domains.soups.service.SoupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对话历史控制器
 * 提供获取汤和更新对话记录的接口
 */
@RestController
@RequestMapping("/api/v1/dialogs")
@RequiredArgsConstructor
public class DialogHistoryController {

    private final MongoDialogHistoryService dialogHistoryService;


    /**
     * 获取用户在特定汤下的对话历史
     *
     * @param userId 用户ID
     * @param soupId 汤ID
     * @return 对话历史
     */
    @GetMapping("/user/{userId}/soup/{soupId}")
    public ResponseEntity<MongoDialogHistory> getDialogHistory(
            @PathVariable String userId,
            @PathVariable String soupId) {
        return ResponseEntity.ok(dialogHistoryService.getOrCreateDialogHistory(userId, soupId));
    }

    /**
     * 添加消息到对话历史
     * 用户和海龟汤agent对话时调用
     *
     * @param userId 用户ID
     * @param soupId 汤ID
     * @param message 消息
     * @return 更新后的对话历史
     */
    @PostMapping("/user/{userId}/soup/{soupId}/message")
    public ResponseEntity<MongoDialogHistory> addMessage(
            @PathVariable String userId,
            @PathVariable String soupId,
            @RequestBody Message message) {
        return ResponseEntity.ok(dialogHistoryService.addMessage(userId, soupId, message));
    }

    /**
     * 获取用户的所有对话历史
     *
     * @param userId 用户ID
     * @return 对话历史列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MongoDialogHistory>> getUserDialogHistories(
            @PathVariable String userId) {
        return ResponseEntity.ok(dialogHistoryService.getDialogHistoriesByUserId(userId));
    }

    /**
     * 批量添加消息到对话历史
     *
     * @param userId 用户ID
     * @param soupId 汤ID
     * @param messages 消息列表
     * @return 更新后的对话历史
     */
    @PostMapping("/user/{userId}/soup/{soupId}/messages")
    public ResponseEntity<MongoDialogHistory> addMessages(
            @PathVariable String userId,
            @PathVariable String soupId,
            @RequestBody List<Message> messages) {
        MongoDialogHistory history = dialogHistoryService.getOrCreateDialogHistory(userId, soupId);
        for (Message message : messages) {
            history.addMessage(message);
        }
        return ResponseEntity.ok(dialogHistoryService.getOrCreateDialogHistory(userId, soupId));
    }
}