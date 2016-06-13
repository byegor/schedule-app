package com.egor.schedule.app.model;

/**
 * Created by Egor on 26.05.2016.
 */
public class Game {

    private Long id;
    private Long startTime;

    private Team radiant;
    private Team dire;
    private League league;

    private String seriesType;
    private String seriesScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Team getRadiant() {
        return radiant;
    }

    public void setRadiant(Team radiant) {
        this.radiant = radiant;
    }

    public Team getDire() {
        return dire;
    }

    public void setDire(Team dire) {
        this.dire = dire;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    public String getSeriesScore() {
        return seriesScore;
    }

    public void setSeriesScore(String seriesScore) {
        this.seriesScore = seriesScore;
    }
}
