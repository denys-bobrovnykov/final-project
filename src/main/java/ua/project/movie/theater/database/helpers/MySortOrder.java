package ua.project.movie.theater.database.helpers;

public class MySortOrder {
    private String sortParam;
    private Direction direction;

    public MySortOrder(String sortParam, Direction direction) {
        this.sortParam = sortParam;
        this.direction = direction;
    }
    public MySortOrder(String sortParam) {
        this.sortParam = sortParam;
        this.direction = Direction.ASC;

    }

    public String getSortParam() {
        return sortParam;
    }

    public void setSortParam(String sortParam) {
        this.sortParam = sortParam;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        DESC, ASC;
    }

    @Override
    public String toString() {
        return (sortParam + " " + (direction == Direction.ASC ? "" : direction)).trim();
    }
}
