package org.space.yavin.alex.yishao.memory.domains.soups.service;

import org.space.yavin.alex.yishao.memory.domains.soups.dto.SoupDTO;
import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.space.yavin.alex.yishao.memory.domains.soups.repository.SoupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SoupService {
    @Autowired
    private SoupRepository soupRepository;

    public Soup createSoup(Soup soup) {
        return soupRepository.save(soup);
    }
    
    public Soup createSoup(SoupDTO soupDTO) {
        Soup soup = new Soup();
        soup.setTitle(soupDTO.getTitle());
        soup.setSoupSurface(soupDTO.getSoupSurface());
        soup.setSoupBottom(soupDTO.getSoupBottom());
        soup.setTags(soupDTO.getTags());
        
        // 设置默认值
        soup.setLikes(0);
        soup.setFavorites(0);
        soup.setCreatedAt(LocalDateTime.now());
        soup.setUpdatedAt(LocalDateTime.now());
        return soupRepository.save(soup);
    }

    public Optional<Soup> getSoupById(Long soupId) {
        return soupRepository.findById(soupId);
    }

    public Soup updateSoup(Long soupId, SoupDTO soupDetails) {
        Soup soup = soupRepository.findById(soupId).orElseThrow();
        soup.setTitle(soupDetails.getTitle());
        soup.setSoupSurface(soupDetails.getSoupSurface());
        soup.setSoupBottom(soupDetails.getSoupBottom());
        soup.setTags(soupDetails.getTags());
        return soupRepository.save(soup);
    }

    public void deleteSoup(Long soupId) {
        soupRepository.deleteById(soupId);
    }
}