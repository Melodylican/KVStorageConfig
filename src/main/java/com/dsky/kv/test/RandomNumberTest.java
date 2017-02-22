package com.dsky.kv.test;

import java.util.Random;

public class RandomNumberTest {
	
	public static void main(String[] args) {
		int i = (int)Math.random()*100;
		System.out.println(i);
		/*
		Random random = new Random();
		
		for(int i=0;i<50;i++) {
			int lineLenA = (int)Math.floor((random.nextDouble()*25.0));//每行的最大长度25
			System.out.println(lineLenA);
		}
		*/
	}

}
