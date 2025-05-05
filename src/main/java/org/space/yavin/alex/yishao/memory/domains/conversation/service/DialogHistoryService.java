package org.space.yavin.alex.yishao.memory.domains.conversation.service;

import org.space.yavin.alex.yishao.memory.domains.conversation.entity.DialogHistory;
import org.space.yavin.alex.yishao.memory.domains.conversation.repository.DialogHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DialogHistoryService {
    
    @Autowired
    private DialogHistoryRepository repository;

    public DialogHistory save(DialogHistory history) {
        return repository.save(history);
    }

    public List<DialogHistory> getAll() {
        return repository.findAll();
    }

    public Optional<DialogHistory> getById(Long id) {
        return repository.findById(id);
    }

    public DialogHistory update(DialogHistory history) {
        return repository.save(history);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<DialogHistory> getByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public List<DialogHistory> getBySoupId(String soupId) {
        return repository.findBySoupId(soupId);
    }
}