package org.space.yavin.alex.yishao.memory.domains.soups.mapper;

import org.space.yavin.alex.yishao.memory.domains.soups.dto.SoupResponseDTO;
import org.space.yavin.alex.yishao.memory.domains.soups.entity.Soup;
import org.springframework.stereotype.Component;

/**
 * 海龟汤实体与DTO之间的转换器
 */
@Component
public class SoupMapper {

    /**
     * 将Soup实体转换为SoupResponseDTO
     *
     * @param soup 海龟汤实体
     * @return 海龟汤响应DTO
     */
    public SoupResponseDTO toResponseDTO(Soup soup) {
        if (soup == null) {
            return null;
        }
        
        SoupResponseDTO responseDTO = new SoupResponseDTO();
        responseDTO.setId(soup.getId());
        responseDTO.setTitle(soup.getTitle());
        responseDTO.setSoupSurface(soup.getSoupSurface());
        responseDTO.setSoupBottom(soup.getSoupBottom());
        responseDTO.setTags(soup.getTags());
        responseDTO.setLikes(soup.getLikes());
        responseDTO.setFavorites(soup.getFavorites());
        
        return responseDTO;
    }
}