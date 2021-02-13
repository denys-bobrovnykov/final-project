package ua.epam.project.movie.theater.database.helpers;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Page<?> page = (Page<?>) o;

        return pageCount.equals(page.pageCount);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + pageCount.hashCode();
        return result;
    }
}
