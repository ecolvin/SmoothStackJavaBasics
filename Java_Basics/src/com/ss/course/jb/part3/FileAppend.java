/**
 * 
 */
package com.ss.course.jb.part3;

import java.io.FileWriter;

/**
 * Opens up a file and appends a string to it
 * @author Eric Colvin
 *
 */
public class FileAppend {
	final static String FILENAME = "resources/output/FileAppend.txt";
	final static String MESSAGE = "Add this string to the end of the file";
	
	/** 
	 * Appends a string (MESSAGE) to the file found at the specified path (FILENAME)
	 * @param args - Command line arguments (unused)
	 */
	public static void main(String[] args) {
		try(FileWriter writer = new FileWriter(FILENAME, true)) {
			writer.append(MESSAGE);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
