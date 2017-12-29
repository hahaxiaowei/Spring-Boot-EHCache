package com.huntkey.rx.ehcache.common.util;

import java.util.List;

/**
 * Created by zhaomj on 2017/4/6.
 */
public class Pagination<T> {
    private List<T> list;
    private long total;

    /**
     *
     * @param list
     * @param total
     */
    public Pagination(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
