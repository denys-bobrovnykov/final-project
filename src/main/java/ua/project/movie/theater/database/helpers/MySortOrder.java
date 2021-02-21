package ua.project.movie.theater.database.helpers;

/**
 * Helper class that takes sort params and returns string for SQL
 */
public class MySortOrder {
    private final String sortParam;
    private final Direction direction;

    public MySortOrder(String sortParam, Direction direction) {
        this.sortParam = sortParam;
        this.direction = direction;
    }

    public MySortOrder(String sortParam) {
        this.sortParam = sortParam;
        this.direction = Direction.ASC;

    }

    @Override
    public String toString() {
        return (sortParam + " " + (direction == Direction.ASC ? "" : direction)).trim();
    }

    public enum Direction {
        DESC, ASC
    }
}
