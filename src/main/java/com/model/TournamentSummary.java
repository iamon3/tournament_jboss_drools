package com.model;

/**
 */
public class TournamentSummary {
    private String teamId;
    private Integer matchesWon;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Integer getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(Integer matchesWon) {
        this.matchesWon = matchesWon;
    }

    @Override
    public String toString(){
        return "Team " + teamId + " won " + matchesWon +" matches.";
    }
}
