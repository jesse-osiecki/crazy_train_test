package com.jesse.main;

import java.util.ArrayList;
import java.util.Stack;

//Jesse Osiecki
//Sunday April 14, 2013





public class Computer implements Comparable<Computer>{
	
	public enum Attribute{
		LOUD(0), CHEAP(1), EXPENSIVE(2), HOT(3), RISKY(4), CAN_GAME(5), CRUNCH_NUMBERS(6);
		
		private int value;
		
		Attribute(int number) { this.value = number; }
		
		public int valueOf(){ return this.value; }
		
		public static Attribute getAttribute(int value){
		    Attribute att = null;

		    for(Attribute a : Attribute.values()){
		        if(a.valueOf() == value){
		            att = a;
		            break;
		        }
		    }

		    return att;
		}
		
		public String toString(){
			String v = "";
			switch(value){
				case 0: v = "it is loud";
						break;
				case 1: v = "it is cheap";
						break;	
				case 2: v = "it is expensive";
						break;
				case 3: v = "it is hot";
						break;
				case 4: v = "it is risky";
						break;
				case 5: v = "it can game";
						break;
				case 6: v = "it can crunch numbers";
						break;
			}
			return v;
		}
	}
	
	public enum GraphicsCardType{
		//these have values b/c I was getting errors in the drl file
		//courtesy of http://stackoverflow.com/questions/8211536/drools-how-to-use-an-enum-in-the-lhs-of-a-rule
		NONE(0), AVERAGE(1), PREMIUM(2);
		
		private int value;
		
		GraphicsCardType(int number) { this.value = number; }
		
		public int valueOf(){ return this.value; }
		
		public static GraphicsCardType getCard(int value){
		    GraphicsCardType gCard = null;

		    for(GraphicsCardType g : GraphicsCardType.values()){
		        if(g.valueOf() == value){
		            gCard = g;
		            break;
		        }
		    }

		    return gCard;
		}
		public String toString(){
			String v = "";
			switch(value){
				case 0: v = "None";
						break;
				case 1: v = "Average";
						break;	
				case 2: v = "Premium";
						break;
			}
			return v;
		}
	}
	
	private int ranking;
	private String model;
	//memory is in Gigabytes
	private double memory;
	//speed is in Ghz
	private double speed;
	private GraphicsCardType card;
	//price is in USD
	private double price;
	//What kind of computer is is?
	private ArrayList<Attribute> attributes;
	//has it been checked
	private boolean checked;
	
	public Computer(String model, int memory, double speed, GraphicsCardType card, double price){
		this.model = model; this.memory = memory; this.speed = speed; this.card = card; this.price = price;
		attributes = new ArrayList<Attribute>();
		checked = false;
		ranking = 0;
	}
	public String getModel() {
		return model;
	}
	public double getMemory() {
		return memory;
	}
	public double getSpeed() {
		return speed;
	}
	public GraphicsCardType getCard() {
		return card;
	}
	public double getPrice() {
		return price;
	}
	public void addAttribute(Attribute a){
		attributes.add(a);
	}
	public ArrayList<Attribute> getAttributes(){
		return attributes;
	}
	public boolean getChecked(){
		return checked;
	}
	public void resetChecked(){
		checked = false;
	}
	public void armChecked(){
		checked = true;
	}
	public int getRanking() {
		return ranking;
	}
	public void addRankingPoints(int ranking) {
		this.ranking += ranking;
	}
	public String toString(){
		return model;
	}
	public static int assignPointValue(int ruleLevel){
		/*
		 * So that any fn+1 is always bigger than the last total

			Fn= fn-1 + fn-2.....+ f1 + 1
			
			Given that f2=2 and f1=1
			(f0 has to =0)
			F3=4
			F4=8
			F5=16
			
			That way you can have a hierarchy of rules that absolutely trump lower rules when each condition
			 is assigned a point value.
			
			Adapting this for the program would require high points (I actually mde them negative, so high absval) to be WORSE.
			 But if a computer is risky, no combination can trump that. The advantage to this solution
			  is that it can be able easily expanded to accommodate more rules. Furthermore, it not only
			  gives certain rules absolute precedence over others, it does so while still allowing
			  for subranking inbetween rule levels. 
		 */
		Stack<Integer> s = new Stack<Integer>();
		int fn = 0;
		for(int i = 0; i <= ruleLevel; i ++){
			@SuppressWarnings("unchecked")
			Stack<Integer> sTemp = (Stack<Integer>) s.clone();
			while(!sTemp.empty()){
				fn += sTemp.pop();
			}
			fn ++;
			s.push(fn);
		}
		return -1 * fn;
	}
	public int compareTo(Computer o) {
		
		return o.getRanking() - this.getRanking();
	}
}
