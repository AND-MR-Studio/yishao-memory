package org.space.yavin.alex.yishao.memory.domains.conversation.repository;

import org.space.yavin.alex.yishao.memory.domains.conversation.entity.DialogHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DialogHistoryRepository extends JpaRepository<DialogHistory, Long> {
    List<DialogHistory> findByUserId(String userId);
    List<DialogHistory> findBySoupId(String soupId);
}