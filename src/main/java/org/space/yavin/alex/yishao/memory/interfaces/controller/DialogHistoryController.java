package org.space.yavin.alex.yishao.memory.interfaces.controller;

import lombok.AllArgsConstructor;
import org.space.yavin.alex.yishao.memory.domains.conversation.entity.DialogHistory;
import org.space.yavin.alex.yishao.memory.domains.conversation.service.DialogHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/dialogs")
public class DialogHistoryController {

    private final DialogHistoryService service;

    @PostMapping
    public ResponseEntity<DialogHistory> create(@RequestBody DialogHistory history) {
        return new ResponseEntity<>(service.save(history), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DialogHistory>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DialogHistory> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DialogHistory> update(@PathVariable Long id, @RequestBody DialogHistory history) {
        history.setId(id);
        return ResponseEntity.ok(service.update(history));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DialogHistory>> getByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @GetMapping("/soup/{soupId}")
    public ResponseEntity<List<DialogHistory>> getBySoupId(@PathVariable String soupId) {
        return ResponseEntity.ok(service.getBySoupId(soupId));
    }
}