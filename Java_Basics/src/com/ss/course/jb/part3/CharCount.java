/**
 * 
 */
package com.ss.course.jb.part3;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author Eric Colvin
 *
 */
public class CharCount {
	final static String FILENAME = "resources/input/CharCount.txt";
	
	/**
	 * @param args - Command line arguments - What character to search for
	 */
	public static void main(String[] args) {
		
		char target;
		if(args.length != 1) {
			System.err.println("Invalid number of command line arguments were specified.");
			System.err.println("This program takes in a single argument (the character to search for).");
			return;
		} else if(args[0].length() != 1) {
			System.err.println("\"" + args[0] + "\" is not a valid character.");
			return;
		} else {
			target = args[0].charAt(0);
		}
		
		try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))){
			int count = 0;
			String s;
			while((s = reader.readLine()) != null) {
				for(int i = 0; i < s.length(); i++) {
					if(s.charAt(i) == target) {
						count++;
					}
				}
			}
			System.out.println("The character \'" + target + "\' appeared " + count + " times in the file.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
