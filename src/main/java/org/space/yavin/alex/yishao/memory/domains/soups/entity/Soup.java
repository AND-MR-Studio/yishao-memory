package org.space.yavin.alex.yishao.memory.domains.soups.entity;

// 添加缺失的导入

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> tags;

    @Column(name = "liked_num")
    private Integer LikedNum;

    @Column(name = "collect_num")
    private Integer CollectNum;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
}
