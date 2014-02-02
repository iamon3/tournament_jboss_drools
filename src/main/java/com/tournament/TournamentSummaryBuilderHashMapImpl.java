package com.tournament;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.model.MatchRecord;
import org.drools.KnowledgeBase;
import org.drools.builder.*;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;

public class TournamentSummaryBuilderHashMapImpl implements
TournamentSummaryBuilder {

    private static final String ruleFile = "tournamentWinner.drl";
    private static int counter = 1;
    private static KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    private static KnowledgeBase kbase ;

    static
    {
        try{
        kbuilder.add(ResourceFactory.newClassPathResource(ruleFile), ResourceType.DRL);
        //kbuilder.add( ResourceFactory.newInputStreamResource( TournamentSummaryBuilderHashMapImpl.class.getResourceAsStream("tournamentWinner.drl")),
          //      ResourceType.determineResourceType("tournamentWinner.drl" ));
        }
        catch(Exception e){
            System.out.println("Problem loading drools rule file." + e);
        }
        if(kbuilder.hasErrors()){
            KnowledgeBuilderErrors errors = kbuilder.getErrors();
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        kbase = kbuilder.newKnowledgeBase();
    }

    public void processEvent(String inputFile) throws IOException {

        WorkingMemoryEntryPoint matchRecordStream;
        WorkingMemoryEntryPoint matchStream;
        StatefulKnowledgeSession kbsession = kbase.newStatefulKnowledgeSession(null,null);
        matchRecordStream = kbsession.getWorkingMemoryEntryPoint("matchRecordStream");
        matchStream = kbsession.getWorkingMemoryEntryPoint("matchStream");
        com.model.TournamentSummary tournamentSummary = null;
        kbsession.setGlobal("tournamentSummary", tournamentSummary);

        File readFile = new File(inputFile);
        FileReader fileReader;
        try {
            fileReader = new FileReader(readFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String readLine ;
            readLine = bufferedReader.readLine();
            while((readLine = bufferedReader.readLine()) != null){
                System.out.println(readLine);
                String[] tokens = readLine.split(" ");
                MatchRecord matchRecord = createMatchRecords(tokens);
                matchRecordStream.insert(matchRecord);
            }
            bufferedReader.close();
            fileReader.close();

            kbsession.fireAllRules();
            if(null != matchRecordStream){
                System.out.println("Match records size : " + matchRecordStream.getFactCount());
            }
            if(null != matchStream){
                System.out.println("Match size : ");
            }
            if(null != tournamentSummary){
                System.out.print("Tournament Summary : " + tournamentSummary);
            }
            kbsession.dispose();
        } catch (FileNotFoundException e) {
            System.out.println("Some Exception occurred : " + e);
        }
        finally {
            matchRecordStream = null;
            matchStream = null;
            tournamentSummary = null;
        }
    }

    @Override
	public TournamentSummary buildSummary(String inputFile) {
		
	    TournamentSummaryHashMapImpl tournamentSummary = new TournamentSummaryHashMapImpl();	
		String currDir = System.getProperty("user.dir");
		try {
			Map<Integer, Map<Integer, Integer>> summary = null;
            summary = readFileAndCreatTournamentSummary(inputFile);
			//summary = readFileAndCreatTournamentSummary(currDir.concat(inputFile));
			tournamentSummary.setSummary(summary);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tournamentSummary;
	}

	
	private Map<Integer,Map<Integer,Integer>> readFileAndCreatTournamentSummary(String inputFile) throws IOException{
		
		Map<Integer,Map<Integer,Integer>> summary = new HashMap<Integer,Map<Integer,Integer>>(); 

		File readFile = new File(inputFile);
		FileReader fileReader;
		try {
			fileReader = new FileReader(readFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String readLine ;
            readLine = bufferedReader.readLine();
			while((readLine = bufferedReader.readLine()) != null){
				System.out.println(readLine);
				String[] tokens = readLine.split(" ");
				updateSummary(summary,tokens);
			}
			bufferedReader.close();
			fileReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
        return summary;
	}



    private MatchRecord createMatchRecords(String[] tokens){
        Integer matchId  = Integer.parseInt(tokens[0]);
        Integer teamId   = Integer.parseInt(tokens[1]);
        Integer playerId = Integer.parseInt(tokens[2]);
        Integer score  = Integer.parseInt(tokens[3]);

        MatchRecord matchRecord = new MatchRecord();
        matchRecord.setId(Integer.toString(counter++));
        matchRecord.setMatchId(matchId.toString());
        matchRecord.setTeamId(teamId.toString());
        matchRecord.setPlayerId(playerId.toString());
        matchRecord.setScore(score);

        return matchRecord;
    }

	private void updateSummary(Map<Integer,Map<Integer,Integer>> summary,String[] tokens){
		
		Integer matchId  = Integer.parseInt(tokens[0]);
		Integer teamId   = Integer.parseInt(tokens[1]);
		Integer playerId = Integer.parseInt(tokens[2]);
		Integer score  = Integer.parseInt(tokens[3]);

        MatchRecord matchRecord = new MatchRecord();
        matchRecord.setId(Integer.toString(counter++));
        matchRecord.setMatchId(matchId.toString());
        matchRecord.setTeamId(teamId.toString());
        matchRecord.setPlayerId(playerId.toString());
        matchRecord.setScore(score);

        //ksession.execute(matchRecord);

        if (false == summary.containsKey(matchId)){
			
			Map<Integer, Integer> teamScoreSummary = new HashMap<Integer, Integer>();
			teamScoreSummary.put(teamId, score);
			summary.put(matchId,teamScoreSummary);
		}
		else{
			Map<Integer, Integer> teamScoresSummary = summary.get(matchId);
			if(false == teamScoresSummary.containsKey(teamId)){
			  teamScoresSummary.put(teamId, score);	
			}
			else{
				Integer prevScore = teamScoresSummary.get(teamId);
				teamScoresSummary.put(teamId, prevScore + score);
			}
		}
	}

}
