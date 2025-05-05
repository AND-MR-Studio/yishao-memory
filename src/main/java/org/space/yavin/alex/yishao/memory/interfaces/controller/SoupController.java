package org.space.yavin.alex.yishao.memory.interfaces.controller;

import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.space.yavin.alex.yishao.memory.domains.soups.service.SoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/soups")
public class SoupController {
    @Autowired
    private SoupService soupService;

    @PostMapping
    public ResponseEntity<Soup> createSoup(@RequestBody Soup soup) {
        return ResponseEntity.status(201).body(soupService.createSoup(soup));
    }

    @GetMapping
    public ResponseEntity<List<Soup>> getAllSoups() {
        return ResponseEntity.ok(soupService.getAllSoups());
    }

    @GetMapping("/{soupId}")
    public ResponseEntity<Soup> getSoupById(@PathVariable String soupId) {
        return soupService.getSoupById(soupId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{soupId}")
    public ResponseEntity<Soup> updateSoup(
            @PathVariable String soupId,
            @RequestBody Soup soupDetails) {
        return ResponseEntity.ok(soupService.updateSoup(soupId, soupDetails));
    }

    @DeleteMapping("/{soupId}")
    public ResponseEntity<Void> deleteSoup(@PathVariable String soupId) {
        soupService.deleteSoup(soupId);
        return ResponseEntity.noContent().build();
    }
}