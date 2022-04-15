package com.designpattern.creational.singleton;

public class SingletonThreadSafeDemo {

	/**
	 * volatile insures that instance will remain singleton through any of the
	 * changes inside of the JVM. When a variable is declared volatile we suggest
	 * the compiler to never store the value of the variable in cache memory.
	 */
	private static volatile SingletonThreadSafeDemo instance = null;

	/**
	 * This will protect us from having a reflection class go ahead and create
	 * instance of this.
	 */
	private SingletonThreadSafeDemo() {
		if (instance != null) {
			throw new RuntimeException("Use getInstance() method to create");
		}
	}

	/**
	 * Method to check if instance is not null, if it is then we synchronize
	 * on it. Reason - It maybe null ,but if two thread are trying to go at it, In
	 * that case the second thread is going to create a new instance of Singleton
	 * class and override the existing instance which has been created by the first
	 * thread. In order to improve that we are going to implement a synchronization.
	 * 
	 */
	public static SingletonThreadSafeDemo getInstance() {
		if (instance == null) {
			synchronized (SingletonThreadSafeDemo.class) {
				if (instance == null) {
					instance = new SingletonThreadSafeDemo();
				}
			}
		}
		return instance;
	}
}
