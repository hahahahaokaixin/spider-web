package com.guo.base.util;

public class RUtil {

	public static void main(String[] args) {

		String[] ns = { "node149" };
		for (int i = 0; i < 10; i++) {
			int ss = getRandomNumber(0, ns.length);
			if (ss > 0) {
				ss = ss - 1;
			}
			System.out.print(ss);
		}

	}

	public static int getRandomNumber(int min, int max) {
		double a = Math.random() * max;
		a = Math.ceil(a);
		int randomNum = new Double(a).intValue();
		while (randomNum < min) {
			a = Math.random() * max;
			a = Math.ceil(a);
			randomNum = new Double(a).intValue();
		}
		return randomNum;

	}

	public static int getRandomNumber() {
		double a = Math.random() * 10;
		a = Math.ceil(a);
		int randomNum = new Double(a).intValue();
		return randomNum;

	}

	public static int getTestRandomNumber() {
		double a = Math.random() * 10;
		a = Math.ceil(a);
		int randomNum = new Double(a).intValue();
		return randomNum;

	}

	public static int getRandomNumber(int size) {
		double a = Math.random() * size;
		a = Math.ceil(a);
		int randomNum = new Double(a).intValue();
		return randomNum;

	}

}
