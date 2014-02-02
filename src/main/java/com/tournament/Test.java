package com.tournament;

import java.io.IOException;

public class Test {
	
    protected static String TOURNAMENT_INPUT_FILE = "input.txt";
    
	public static void main(String[] args) throws IOException {
     //Integer[] winner = null;
     //winner = TournamentUtil.getWinner(TOURNAMENT_INPUT_FILE);
     //System.out.println("Team " + winner[0]+" won tournament. Matches won :- "+winner[1]);

        TournamentSummaryBuilderHashMapImpl tournamentSummaryBuilderHashMap = new TournamentSummaryBuilderHashMapImpl();
        tournamentSummaryBuilderHashMap.processEvent(TOURNAMENT_INPUT_FILE);
	}
}
