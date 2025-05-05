package org.space.yavin.alex.yishao.memory.domains.conversation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : Alex Huangfu
 * @Date: 2025/5/4 11:14
 * @Description: 对话历史实体
 */
@Entity
@Table(name = "dialogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DialogHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "soup_id", nullable = false)
    private String soupId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "history", columnDefinition = "JSON")
    private List<Message> history;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}