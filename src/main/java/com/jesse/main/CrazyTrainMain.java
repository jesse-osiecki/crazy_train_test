//Jesse Osiecki
//Sunday April 14, 2013

package com.jesse.main;

import java.util.ArrayList;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import com.jesse.main.Computer.GraphicsCardType;

public class CrazyTrainMain {

	public static void main(String[] args) {
		
		//make list of Computers
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		computerList.add(new Computer("NIGELTHORNBERRY", 2, 5, GraphicsCardType.NONE, 800));
		computerList.add(new Computer("ARNOLD", 8, 3, GraphicsCardType.AVERAGE, 1500));
		computerList.add(new Computer("INVADERZIM", 1, 1, GraphicsCardType.NONE, 400));
		computerList.add(new Computer("BUTTUGLYMARTIAN", 16, 4, GraphicsCardType.PREMIUM, 3000));
		computerList.add(new Computer("ROCKO", 32, 5, GraphicsCardType.NONE, 8000));
		
		//Comparison obj
		ComputerComparison comparR = new ComputerComparison("ComputerRule.drl", computerList);
		
		//knowlegesession
		StatefulKnowledgeSession ksession = comparR.getSession();
		//log
		KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "log");
		//fire the all rules, give no sympathy
		ksession.fireAllRules();
		ksession.dispose();
		//new comparison just for post-comparison dependent comparisons
		//TODO is there a better way? Two files together fires them all at once still
		comparR = new ComputerComparison("SecondaryComputerRules.drl", computerList);
		ksession = comparR.getSession();
		ksession.fireAllRules();
		
		for (Computer c: computerList) {
			if(c.getChecked()){
				//print out what we know about the model
				System.out.print(c.getModel() + ": ");
				for(int i = 0; i < c.getAttributes().size(); i++){
					//special cases
					
					if(i == c.getAttributes().size() - 2){
						System.out.print(c.getAttributes().get(i) + ", and ");
					}
					else if(i != c.getAttributes().size() - 1){
						System.out.print(c.getAttributes().get(i) + ", ");
					}
					else{
						System.out.println(c.getAttributes().get(i));
					}
				}
			}
		}
		logger.close();

	}

}
