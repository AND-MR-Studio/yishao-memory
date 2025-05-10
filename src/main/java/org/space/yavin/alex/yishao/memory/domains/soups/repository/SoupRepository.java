package org.space.yavin.alex.yishao.memory.domains.soups.repository;

import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoupRepository extends JpaRepository<Soup, Long> {


    Page<Soup> findByIdIn(@NonNull List<Long> ids, Pageable pageable);

    /**
     * 从数据库中随机获取一条Soup记录。
     * 使用原生SQL查询实现，通过ORDER BY RAND()随机排序，并限制结果为1条。
     */
    @Query(value = "SELECT * FROM soups ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Soup> findRandom();
}