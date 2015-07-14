package com.taramt.popularmovies;

/**
 * Created by praveengarimella on 14/07/15.
 */
public class PopularMovie {
    private String poster;
    private String releaseData;
    private String plot;
    private String title;
    private String voteAverage;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleaseData() {
        return releaseData;
    }

    public void setReleaseData(String releaseData) {
        this.releaseData = releaseData;
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
                ", releaseData='" + releaseData + '\'' +
                ", plot='" + plot + '\'' +
                ", title='" + title + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                '}';
    }
}
