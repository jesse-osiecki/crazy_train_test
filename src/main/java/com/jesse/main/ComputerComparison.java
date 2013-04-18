//Jesse Osiecki
//Sunday April 14, 2013
package com.jesse.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import com.jesse.main.Computer;
import com.jesse.main.IncorrectDRLException;


public class ComputerComparison {

	private StatefulKnowledgeSession ksession;
	private ArrayList<Computer> computerList;
	private Stack<Computer> safeCanGame;
	private Stack<Computer> riskyNumberCrunchers;
	private boolean hotGamingComputer;
	
	public static ComputerComparison makeComparisonObj(String drl, ArrayList<Computer> computers){
		return new ComputerComparison(drl, computers);
	}
	public void computerConclusions(){
		//What do we know now?
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
				//Answer Questions at end of Exercise
				ArrayList<Computer.Attribute> a = c.getAttributes();
				//Safe and Can Game?
				if(a.contains(Computer.Attribute.CAN_GAME) && !a.contains(Computer.Attribute.RISKY)){
					safeCanGame.push(c);
				}
				//risky and can crunch numbers
				if(a.contains(Computer.Attribute.CRUNCH_NUMBERS) && a.contains(Computer.Attribute.RISKY)){
					riskyNumberCrunchers.push(c);
				}
				//All Gaming computers hot?
				if(a.contains(Computer.Attribute.CAN_GAME) && !a.contains(Computer.Attribute.HOT)){
					hotGamingComputer = false;
				}
			}
		}
	}
	public void rankComputers(){
		//ranking system for the bonus problem
		Collections.sort(computerList);
		for(Computer c: computerList){
			System.out.println(c + " " + c.getRanking());
		}
	}
	private ComputerComparison(String drl, ArrayList<Computer> computers){
		//new knowlegebuilder using the drl
		//Can add more later here if needed
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add( ResourceFactory.newClassPathResource(drl),ResourceType.DRL);
		//check for errors
		try {
			if(kbuilder.hasErrors()) {
				System.out.println(kbuilder.getErrors());
				throw new IncorrectDRLException(kbuilder.getErrors());
			}
		} catch (IncorrectDRLException e) {
			//best way to handle this?
			e.printStackTrace();
			System.exit(1);
		}
		//KnowlegeBase
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		//knowlegesession
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		//put all objects to be tested in the session
		for(Computer c : computers){
			ksession.insert(c);
		}
		this.ksession = ksession;
		computerList = computers;
		safeCanGame = new Stack<Computer>();
		riskyNumberCrunchers = new Stack<Computer>();
		hotGamingComputer = true;
	}
	
	public StatefulKnowledgeSession getSession(){
		return ksession;
	}
	public void safeGamingComputer(){
		if(safeCanGame.isEmpty()){
			System.out.println("There are no safe gaming computers");
		}
		else{
			while(!safeCanGame.isEmpty()){
				System.out.println(safeCanGame.pop() + " is a safe Gaming computer");
			}
		}
		
	}
	public void riskyNumberCruncher(){
		if(riskyNumberCrunchers.isEmpty()){
			System.out.println("There are no risky number crunchers");
		}
		else{
			while(!riskyNumberCrunchers.isEmpty()){
				System.out.println(riskyNumberCrunchers.pop() + " is a risky number cruncher");
			}
		}
	}
	public void hotGamingComputerAll(){
		if(hotGamingComputer){
			System.out.println("All gaming computers are hot");
		}
		else{
			System.out.println("All gaming computers are NOT hot");
		}
	}
	
}
