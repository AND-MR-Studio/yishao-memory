package org.space.yavin.alex.yishao.memory.domains.soups.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 海龟汤响应DTO
 * 用于向客户端返回海龟汤数据，避免直接暴露实体类
 */
@Getter
@Setter
public class SoupResponseDTO {
    private Long id;
    private String title;
    private String soupSurface;
    private String soupBottom;
    private List<String> tags;
    private Integer likes;
    private Integer favorites;
    
    // 不返回时间戳字段，避免暴露内部实现细节
}