package org.space.yavin.alex.yishao.memory.domains.common.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author yyHuangfu
 * @create 2025/5/10
 */
public class Page implements Pageable {
    private int page;
    private int size;

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return 0;
    }

    @Override
    public long getOffset() {
        return 0;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
