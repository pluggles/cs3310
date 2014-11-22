/*
 * Assignment 4 priority queue 
 * Written By Aron Lawrence
 * Customer class-the nodes
 * 
 */


package edu.wmich.cs3310.A4;

public class Customer {
	private int priority;
	private String name;
	
	public Customer(){
		
	}
	//**********constructor*****************
	public Customer(int pri, String n){
		priority = pri;
		name = n;
		}
	//***********public getters****************
	
	//***************returns priority
	public int getPriority(){
		return priority;
	}
	//******************returns name************
	public String getName(){
		return name;
	}
	
}
