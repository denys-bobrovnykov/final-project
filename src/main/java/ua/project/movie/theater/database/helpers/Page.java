package ua.project.movie.theater.database.helpers;

import java.util.ArrayList;

public class Page<T> extends ArrayList<T> {
    private Integer pageCount = 0;

    public Integer getPageCount() {
        return pageCount;
    }
    public Page<T> setPageCount(Integer pageSize, Integer totalPages) {
        this.pageCount = pageSize > 0
                ? (int) Math.ceil((double) totalPages / pageSize)
                : 0;
        return this;
    }

}
