package org.space.yavin.alex.yishao.memory.domains.soups.entity;

// 添加缺失的导入

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;  // 添加集合相关导入
import java.util.Arrays;
import java.util.Collections;

/**
 * @Author : Alex Huangfu
 * @Date: 2025/5/4 11:23
 * @Description: ToDo
 */
@Entity
@Getter
@Setter
@Table(name = "soups")
public class Soup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "soup_surface", nullable = false)
    private String soupSurface;

    @Column(name = "soup_bottom", nullable = false)
    private String soupBottom;

    @Column(name = "tags")
    private String tags; // 存储为JSON字符串

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    // 替换所有soupId引用为id
    public String getSoupId() {
        return id != null ? id.toString() : null;
    }

    public void setSoupId(String soupId) {
        this.id = soupId != null ? Long.valueOf(soupId) : null;
    }

    // tags字段改为String类型处理
    public String getTags() {
        return tags;
    }

    // 修改后的标签处理方法
    public void setTags(List<String> tags) {
        this.tags = tags != null ? String.join(",", tags) : null;
    }

    public List<String> getParsedTags() {
        return tags != null ? Arrays.asList(tags.split(",")) : Collections.emptyList();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
