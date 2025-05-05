package org.space.yavin.alex.yishao.memory.domains.soups.repository;

import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoupRepository extends MongoRepository<Soup, String> {
}