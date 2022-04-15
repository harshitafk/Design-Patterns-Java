package com.designpattern.creational.singleton;

public class SingletonLazyLoadDemo {

private static SingletonLazyLoadDemo instance = null;
	
	private SingletonLazyLoadDemo() {
		
	}
	
	public static SingletonLazyLoadDemo getInstance() {
		if(instance == null) {
			instance = new SingletonLazyLoadDemo();
		}
		return instance;
	}
}
