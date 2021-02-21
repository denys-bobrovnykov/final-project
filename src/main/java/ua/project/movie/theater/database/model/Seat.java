package ua.project.movie.theater.database.model;

import java.util.Objects;

public class Seat {
    private Integer id;
    private Integer row;
    private Integer number;

    public Seat() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    private Seat(Builder builder) {
        this.id = builder.id;
        this.row = builder.row;
        this.number = builder.number;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Integer id;
        Integer row;
        Integer number;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }
        public Builder row(Integer row) {
            this.row = row;
            return this;
        }
        public Builder number(Integer number) {
            this.number = number;
            return this;
        }
        public Seat build() {
            return new Seat(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (!Objects.equals(row, seat.row)) return false;
        return Objects.equals(number, seat.number);
    }

    @Override
    public int hashCode() {
        int result = row != null ? row.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
