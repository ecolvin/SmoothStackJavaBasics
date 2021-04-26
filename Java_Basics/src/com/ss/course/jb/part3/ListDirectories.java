/**
 * 
 */
package com.ss.course.jb.part3;

import java.io.File;

/**
 * Uses recursion to print out a list of every file/directory (including subdirectories) under the specified path
 * @author Eric Colvin
 *
 */
public class ListDirectories {
	/**
	 * Calls printFiles() with the provided path as the initial directory (count = 0)
	 * @param args - Command line argument for what path to start at
	 */
	public static void main(String[] args) {
		ListDirectories ld = new ListDirectories();
		ld.printFiles(args[0], 0);
	}
	
	/**
	 * Recursive function to print out the list of files in the current directory and call printFiles for each of them
	 * @param filename - Current directory to search in
	 * @param count - How many subdirectories down are we (for indentation to make it look neater)
	 */
	public void printFiles(String filename, int count) {
		try{
			File file = new File(filename);
			String[] fileList = file.list();
			if(fileList != null) {
				for(String f:fileList) {
					for(int i = 0; i < count; i++) {
						System.out.print("...");
					}
					System.out.println(f);
					StringBuilder newFilename = new StringBuilder(filename);
					if(!filename.endsWith("/")) {
						newFilename.append("/");
					}
					newFilename.append(f);
					printFiles(newFilename.toString(), count + 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
