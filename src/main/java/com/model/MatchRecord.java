package com.model;

/**
 */
public class MatchRecord {

    public String id;
    public String teamId;
    public String matchId;
    public String playerId;
    public Integer score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString(){
        String comma = " , ";
        StringBuilder sb = new StringBuilder();
        sb.append("Record Id : " + id + comma);
        sb.append("matchId : "+matchId + comma);
        sb.append("teamId : " + teamId + comma);
        sb.append("playerId : "+playerId + comma);
        sb.append("score : " + score);
        return sb.toString();
    }
}
