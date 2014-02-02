package com.model;

/**
 */
public class Match {
    public String id;
    public String team1;
    public String team2;
    public Integer team1_score;
    public Integer team2_score;
    public String winnerTeam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Integer getTeam1_score() {
        return team1_score;
    }

    public void setTeam1_score(Integer team1_score) {
        this.team1_score = team1_score;
    }

    public Integer getTeam2_score() {
        return team2_score;
    }

    public void setTeam2_score(Integer team2_score) {
        this.team2_score = team2_score;
    }

    public String getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(String winnerTeam) {
        this.winnerTeam = winnerTeam;
    }
}
