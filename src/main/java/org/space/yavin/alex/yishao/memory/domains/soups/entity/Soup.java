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

}
