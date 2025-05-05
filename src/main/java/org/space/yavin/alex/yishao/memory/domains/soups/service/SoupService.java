package org.space.yavin.alex.yishao.memory.domains.soups.service;

import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.space.yavin.alex.yishao.memory.domains.soups.repository.SoupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SoupService {
    @Autowired
    private SoupRepository soupRepository;

    public Soup createSoup(Soup soup) {
        return soupRepository.save(soup);
    }

    public List<Soup> getAllSoups() {
        return soupRepository.findAll();
    }

    public Optional<Soup> getSoupById(String soupId) {
        return soupRepository.findById(soupId);
    }

    public Soup updateSoup(String soupId, Soup soupDetails) {
        Soup soup = soupRepository.findById(soupId).orElseThrow();
        soup.setTitle(soupDetails.getTitle());
        soup.setSoupSurface(soupDetails.getSoupSurface());
        soup.setSoupBottom(soupDetails.getSoupBottom());
        soup.setTags(soupDetails.getTags());
        return soupRepository.save(soup);
    }

    public void deleteSoup(String soupId) {
        soupRepository.deleteById(soupId);
    }
}