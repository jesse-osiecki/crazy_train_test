//Jesse Osiecki
//Sunday April 14, 2013
package com.jesse.main;

import java.util.ArrayList;

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
	public ComputerComparison(String drl, ArrayList<Computer> computers){
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
	}
	
	public StatefulKnowledgeSession getSession(){
		return ksession;
	}
}
