//Jesse Osiecki
//Sunday April 14, 2013

package com.jesse.main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import com.jesse.main.Computer.GraphicsCardType;

public class CrazyTrainMain {
	private static void printSpecs(ArrayList<Computer> computerList){
		//print specs for reference
		DecimalFormat price = new DecimalFormat("$0.00");
		DecimalFormat speed = new DecimalFormat("0.0 GHz");
		DecimalFormat memory = new DecimalFormat("0.0 GB");
		for(Computer c: computerList){
			System.out.println("Model: " + c.getModel() + " | Memory: " + memory.format(c.getMemory())
					+ " | Speed: " + speed.format(c.getSpeed()) + " | GraphicsCard: " + 
					c.getCard() + " | Price: " + price.format(c.getPrice()));
			System.out.println();
		}
	}
	private static ComputerComparison drlComparison(ArrayList<Computer> computerList, String[] drlFiles, int log){
		//Comparison obj
		ComputerComparison comparR = null;
		//knowlegesession
		StatefulKnowledgeSession ksession;
		//log
		KnowledgeRuntimeLogger logger;
		int logSub = 0;
		//fire all the rules, give no sympathy
		for(String s: drlFiles){
			comparR = ComputerComparison.makeComparisonObj(s, computerList);
			ksession = comparR.getSession();
			logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "log"+log + "_" + logSub);
			ksession.fireAllRules();
			logger.close();
			logSub++;
		}
		return comparR;
	}
	public static void main(String[] args) {
		
		//make list of Computers
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		computerList.add(new Computer("NIGELTHORNBERRY", 2, 5, GraphicsCardType.NONE, 800));
		computerList.add(new Computer("ARNOLD", 8, 3, GraphicsCardType.AVERAGE, 1500));
		computerList.add(new Computer("INVADERZIM", 1, 1, GraphicsCardType.NONE, 400));
		computerList.add(new Computer("BUTTUGLYMARTIAN", 16, 4, GraphicsCardType.PREMIUM, 3000));
		computerList.add(new Computer("ROCKO", 32, 5, GraphicsCardType.NONE, 8000));
		
		//print specs
		printSpecs(computerList);
		
		//compare 1-5
		ComputerComparison comparR = drlComparison(computerList, new String[]{"ComputerRule.drl", "SecondaryComputerRules.drl"}, 0);
		
		//conclusions on first five
		System.out.println();
		comparR.computerConclusions();
		System.out.println();
		comparR.riskyNumberCruncher();
		comparR.safeGamingComputer();
		comparR.hotGamingComputerAll();
		System.out.println();
		
		
		System.out.println("Now for how this program feels about George Clinton" + "\n");
		
		
		//Machine6 also known as GEORGECLINTON
		
		ArrayList<Computer> justGeorge = new ArrayList<Computer>();
		justGeorge.add(new Computer("GEORGECLINTON", 2, 2, GraphicsCardType.PREMIUM, 5500));
		//George everybody
		printSpecs(justGeorge);
		//compare 6
		comparR = drlComparison(justGeorge, new String[]{"ComputerRule.drl", "SecondaryComputerRules.drl"}, 1);
		//conclusions on GeorgeClinton
		System.out.println("With George Clinton being the only one considered:");
		System.out.println();
		comparR.computerConclusions();
		System.out.println();
		comparR.riskyNumberCruncher();
		comparR.safeGamingComputer();
		comparR.hotGamingComputerAll();
		System.out.println();
		
		
		//Bonus section
		//combine the lists
		computerList.addAll(justGeorge);
		//fire ranking rules
		comparR = drlComparison(computerList, new String[]{"Ranking.drl"}, 2);
		//show ranking
		System.out.println("***THE COMPUTER RANKING SYSTEM*** (closer to 0 is better)");
		comparR.rankComputers();
	}

}
