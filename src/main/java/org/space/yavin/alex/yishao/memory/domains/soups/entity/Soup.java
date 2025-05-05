package org.space.yavin.alex.yishao.memory.domains.soups.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * @Author : Alex Huangfu
 * @Date: 2025/5/4 11:23
 * @Description: ToDo
 */
@Document(collection = "soups")
public class Soup {
    @Id
    private String soupId;

    @Field(name = "title")
    private String title;

    private String soupSurface;
    private String soupBottom;
    private List<String> tags;

    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime createdAt;

    public String getSoupId() {
        return soupId;
    }

    public void setSoupId(String soupId) {
        this.soupId = soupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSoupSurface() {
        return soupSurface;
    }

    public void setSoupSurface(String soupSurface) {
        this.soupSurface = soupSurface;
    }

    public String getSoupBottom() {
        return soupBottom;
    }

    public void setSoupBottom(String soupBottom) {
        this.soupBottom = soupBottom;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
