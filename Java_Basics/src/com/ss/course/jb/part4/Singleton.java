/**
 * 
 */
package com.ss.course.jb.part4;

/**
 * A simple Singleton with double-checked locking
 * @author Eric Colvin
 *
 */
public class Singleton {
	private static volatile Singleton instance = null;
	
	/**
	 * Private default constructor
	 */
	private Singleton() {
		
	}
	
	/**
	 * Static getInstance() method to retrieve the single instance of the class with double-checked locking
	 * @return - the instance of the object
	 */
	public static Singleton getInstance() {
		if(instance == null) {
			synchronized(instance) {
				if(instance == null)
					instance = new Singleton();
			}
		}		
		return instance;
	}

}
