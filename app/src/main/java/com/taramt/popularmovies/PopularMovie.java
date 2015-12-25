package com.taramt.popularmovies;

import java.io.Serializable;

/**
 * Created by praveengarimella on 14/07/15.
 */
public class PopularMovie implements Serializable {
    private String poster;
    private String releaseDate;
    private String plot;
    private String title;
    private String voteAverage;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        return "PopularMovie{" +
                "poster='" + poster + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", plot='" + plot + '\'' +
                ", title='" + title + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                '}';
    }
}
