package com.designpattern.singleton;

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
	 * Method to check first if instance is not null, if it is then we synchronize
	 * on it. Reason - It maybe null ,but if two thread are trying to go at it, once
	 * we've synchronized and checked for null again, and if another class has lock
	 * on that, it will then block our code and create the instance and return that
	 * synchronized lock to where our code would now go back in and say, if this
	 * instance is null it would go, oh,no I'm already created and returned back out
	 * of this.
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
