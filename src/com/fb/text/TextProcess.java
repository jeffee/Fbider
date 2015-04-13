/***********************************************
     @ date  : 2015年4月1日,下午3:11:29   
     @ author: Jeffee Chen                  
 ***********************************************
 *   Copyright © Jeffee, All Rights Reserved   *
 ************************************************/
package com.fb.text;

public class TextProcess {

	public static String join(String arg1, String... args){
		StringBuilder sb = new StringBuilder(arg1);
		for(String str:args){
			sb.append(";").append(str);
		}
		return sb.toString();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
