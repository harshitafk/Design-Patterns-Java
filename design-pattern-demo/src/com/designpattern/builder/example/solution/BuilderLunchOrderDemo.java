package com.designpattern.builder.example.solution;

public class BuilderLunchOrderDemo {

public static void main(String args[]) {
		
		LunchOrder.Builder builder = new LunchOrder.Builder();
		
		builder.bread("Wheat").dressing("Mayo").meat("Turkey");
		
		LunchOrder lunchOrder = builder.makeMeSandwich();
		
		System.out.println(lunchOrder.getBread());
		System.out.println(lunchOrder.getCondiments());
		System.out.println(lunchOrder.getDressing());
		System.out.println(lunchOrder.getMeat());	
	}
}
