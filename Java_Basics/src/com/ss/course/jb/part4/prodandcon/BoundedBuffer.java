/**
 * 
 */
package com.ss.course.jb.part4.prodandcon;

import java.util.ArrayList;

/**
 * Bounded Buffer class for producer and consumer threads to share (basically a FIFO queue with a max size)
 * @author Eric Colvin
 *
 */
public class BoundedBuffer {
	private ArrayList<Integer> buffer;
	private int maxSize;
	
	/**
	 * Constructor for the BoundedBuffer class
	 * @param size - The max size of the buffer
	 */
	public BoundedBuffer(int size) {
		buffer = new ArrayList<Integer>();
		maxSize = size;
	}
	
	
	/**
	 * Add i to the end of the buffer if it isn't full
	 * @param i - The int to add to the buffer
	 */
	public void push(Integer i) {
		if(!full()) {
			buffer.add(i);
		}
	}
	
	/**
	 * Remove the first element of the buffer and return it
	 * @return - the first element of the buffer
	 */
	public Integer pop() {
		if(!empty()) {
			return buffer.remove(0);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Check if the buffer is empty
	 * @return - true if empty, false if not empty
	 */
	public Boolean empty() {
		return buffer.size() <= 0;
	}
	
	
	/**
	 * Check if the buffer is full (can hold 10 elements)
	 * @return - true if full, false if not full
	 */
	public Boolean full() {
		return buffer.size() >= maxSize;
	}
}
