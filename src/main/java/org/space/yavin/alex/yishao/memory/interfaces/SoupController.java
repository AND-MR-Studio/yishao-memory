package org.space.yavin.alex.yishao.memory.interfaces;

import lombok.RequiredArgsConstructor;
import org.space.yavin.alex.yishao.memory.domains.soups.dto.SoupDTO;
import org.space.yavin.alex.yishao.memory.domains.soups.dto.SoupResponseDTO;
import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.space.yavin.alex.yishao.memory.domains.soups.mapper.SoupMapper;
import org.space.yavin.alex.yishao.memory.domains.soups.service.SoupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : Alex Huangfu
 * @Date: 2025/5/5 18:25
 * @Description: 管理海龟汤数据
 */

@RestController
@RequestMapping("/api/soups")
@RequiredArgsConstructor
public class SoupController {

    private final SoupService soupService;
    private final SoupMapper soupMapper;

    @PostMapping("/create")
    public ResponseEntity<SoupResponseDTO> createSoup(@RequestBody SoupDTO soupDTO) {
        Soup createdSoup = soupService.createSoup(soupDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(soupMapper.toResponseDTO(createdSoup));
    }

    @GetMapping("/{soupId}")
    public ResponseEntity<SoupResponseDTO> getSoupById(@PathVariable Long soupId) {
        return soupService.getSoupById(soupId)
                .map(soup -> ResponseEntity.ok(soupMapper.toResponseDTO(soup)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/random")
    public ResponseEntity<SoupResponseDTO> getRandomSoup() {
        return soupService.getRandomSoup()
                .map(soup -> ResponseEntity.ok(soupMapper.toResponseDTO(soup)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{soupId}")
    public ResponseEntity<SoupResponseDTO> updateSoup(
            @PathVariable Long soupId,
            @RequestBody SoupDTO soupDetails) {
        Soup updatedSoup = soupService.updateSoup(soupId, soupDetails);
        return ResponseEntity.ok(soupMapper.toResponseDTO(updatedSoup));
    }

    @DeleteMapping("/{soupId}")
    public ResponseEntity<Void> deleteSoup(@PathVariable Long soupId) {
        soupService.deleteSoup(soupId);
        return ResponseEntity.noContent().build();
    }
}
