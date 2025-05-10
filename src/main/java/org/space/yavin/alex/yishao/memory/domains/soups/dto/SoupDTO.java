package org.space.yavin.alex.yishao.memory.domains.soups.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SoupDTO {

    private String title;

    private String soupSurface;

    private String soupBottom;

    private List<String> tags;
}
