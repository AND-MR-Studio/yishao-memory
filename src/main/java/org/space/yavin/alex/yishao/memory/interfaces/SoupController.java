package org.space.yavin.alex.yishao.memory.interfaces;

import org.space.yavin.alex.yishao.memory.domains.soups.dto.SoupDTO;
import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.space.yavin.alex.yishao.memory.domains.soups.service.SoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : Alex Huangfu
 * @Date: 2025/5/5 18:25
 * @Description: ToDo
 */

@RestController
@RequestMapping("/api/soups")
public class SoupController {
    @Autowired
    private SoupService soupService;

    @PostMapping
    public ResponseEntity<Soup> createSoup(@RequestBody SoupDTO soupDTO) {
        return ResponseEntity.status(201).body(soupService.createSoup(soupDTO));
    }

    @GetMapping("/{soupId}")
    public ResponseEntity<Soup> getSoupById(@PathVariable Long soupId) {
        return soupService.getSoupById(soupId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{soupId}")
    public ResponseEntity<Soup> updateSoup(
            @PathVariable Long soupId,
            @RequestBody SoupDTO soupDetails) {
        return ResponseEntity.ok(soupService.updateSoup(soupId, soupDetails));
    }

    @DeleteMapping("/{soupId}")
    public ResponseEntity<Void> deleteSoup(@PathVariable Long soupId) {
        soupService.deleteSoup(soupId);
        return ResponseEntity.noContent().build();
    }
}
