package com.designpattern.singleton;

public class SingletonMain {

	public static void main(String[] args) {
		SingletonDemo singleton = SingletonDemo.getInstance();
		System.out.println(singleton);
		SingletonDemo singleton2 = SingletonDemo.getInstance();
		System.out.println(singleton2);

		SingletonLazyLoadDemo singletonlazyloaddemo = SingletonLazyLoadDemo.getInstance();
		System.out.println(singleton);
		SingletonLazyLoadDemo singletonlazyloaddemo2 = SingletonLazyLoadDemo.getInstance();
		System.out.println(singleton2);
	}
}