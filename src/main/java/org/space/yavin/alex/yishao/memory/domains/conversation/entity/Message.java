package org.space.yavin.alex.yishao.memory.domains.conversation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : Alex Huangfu
 * @Date: 2025/5/4 0:08
 * @Description: 对话消息实体，包含角色和内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    /**
     * 消息角色：user或agent
     */
    private String role;
    
    /**
     * 消息内容
     */
    private String content;
}
