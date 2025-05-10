package org.space.yavin.alex.yishao.memory.domains.common.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author yyHuangfu
 * @create 2025/5/10
 */
public class Page implements Pageable {
    private final int page;
    private final int size;
    private final Sort sort;

    public Page(int page, int size) {
        this(page, size, Sort.unsorted());
    }

    public Page(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        return (long) page * size;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new Page(page + 1, size, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return page > 0 ? new Page(page - 1, size, sort) : this;
    }

    @Override
    public Pageable first() {
        return new Page(0, size, sort);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new Page(pageNumber, size, sort);
    }

    @Override
    public boolean hasPrevious() {
        return page > 0;
    }
}