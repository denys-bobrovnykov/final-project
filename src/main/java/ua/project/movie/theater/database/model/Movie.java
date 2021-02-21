package ua.project.movie.theater.database.model;


import java.util.Objects;

public class Movie {
    private Integer id;
    private String titleEn;
    private String titleUa;
    private Integer releaseYear;
    private Integer runningTime;
    private String poster;

    public static Builder builder() {
        return new Builder();
    }

    private Movie(Builder builder) {
        this.id = builder.id;
        this.titleEn = builder.titleEn;
        this.titleUa = builder.titleUa;
        this.releaseYear = builder.releaseYear;
        this.runningTime = builder.runningTime;
        this.poster = builder.poster;
    }

    public static class Builder {
         Integer id;
         String titleEn;
         String titleUa;
         Integer releaseYear;
         Integer runningTime;
         String poster;

         public Movie build() {
             return new Movie(this);
         }

         public Builder id(Integer id) {
             this.id = id;
             return this;
         }
         public Builder titleEn(String titleEn) {
             this.titleEn = titleEn;
             return this;
         }
         public Builder titleUa(String titleUa) {
             this.titleUa = titleUa;
             return this;
         }
         public Builder releaseYear(Integer releaseYear) {
             this.releaseYear = releaseYear;
             return this;
         }
         public Builder poster(String poster) {
             this.poster = poster;
             return this;
         }

        public Builder runningTime(Integer runningTime) {
             this.runningTime = runningTime;
             return this;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleUa() {
        return titleUa;
    }

    public void setTitleUa(String titleUa) {
        this.titleUa = titleUa;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Integer runningTime) {
        this.runningTime = runningTime;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!titleEn.equals(movie.titleEn)) return false;
        if (!Objects.equals(titleUa, movie.titleUa)) return false;
        if (!Objects.equals(releaseYear, movie.releaseYear)) return false;
        return Objects.equals(runningTime, movie.runningTime);
    }

    @Override
    public int hashCode() {
        int result = titleEn.hashCode();
        result = 31 * result + (titleUa != null ? titleUa.hashCode() : 0);
        result = 31 * result + (releaseYear != null ? releaseYear.hashCode() : 0);
        result = 31 * result + (runningTime != null ? runningTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "titleEn='" + titleEn + '\'' +
                '}';
    }
}
