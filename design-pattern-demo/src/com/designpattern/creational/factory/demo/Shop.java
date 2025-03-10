package com.designpattern.creational.factory.demo;

public class Shop extends Website {

	@Override
	public void createWebsite() {
		// TODO Auto-generated method stub
		pages.add(new CartPage());
		pages.add(new ItemPage());
		pages.add(new SearchPage());
	}

}
