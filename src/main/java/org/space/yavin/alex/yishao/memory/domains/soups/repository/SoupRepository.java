package org.space.yavin.alex.yishao.memory.domains.soups.repository;

import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoupRepository extends JpaRepository<Soup, Long> {


    Page<Soup> findByIdIn(@NonNull List<Long> ids, Pageable pageable);

}