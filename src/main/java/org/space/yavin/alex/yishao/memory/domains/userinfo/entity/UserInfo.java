package org.space.yavin.alex.yishao.memory.domains.userinfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 用户信息实体
 * 使用MySQL存储用户基本信息
 */
@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户唯一标识，微信小程序的openId
     */
    @Column(nullable = false, unique = true)
    private String userId;

    /**
     * 用户昵称
     */
    @Column(nullable = false)
    private String nickname;

    /**
     * 用户头像URL
     */
    @Column(nullable = false)
    private String avatarUrl;

    /**
     * 创建时间
     */
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}