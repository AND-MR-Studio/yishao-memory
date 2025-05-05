package org.space.yavin.alex.yishao.memory.domains.soups.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;

public interface SoupRepository extends MongoRepository<Soup, String> {
    // 自动包含以下方法：
    // - save()
    // - findById()
    // - findAll()
    // - deleteById()
    // 可扩展自定义查询方法
}