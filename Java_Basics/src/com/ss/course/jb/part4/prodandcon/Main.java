/**
 * 
 */
package com.ss.course.jb.part4.prodandcon;

import java.util.ArrayList;
import java.util.Random;

/**
 * A producer produces ints, stores them in a buffer and a consumer takes the ints from the buffer and outputs them
 * @author Eric Colvin
 */
public class Main {

	static BoundedBuffer bb = new BoundedBuffer(10); //Shared bounded buffer with a size of 10
	static Integer doneProducing = 0; //How many producer threads are done producing (to determine when they are all done)
	static Boolean finished = false; //Set to true when all producers are done and the buffer is empty to
	
	/**
	 * Creates two types of threads - producers generate a random # 1-100 and add it to the buffer - consumers retrieve a number from the buffer and output it
	 * @param args - Command line arguments (unused)
	 */
	public static void main(String[] args) {
		final int numProducers = 10; //How many producers to create
		final int numConsumers = 5; //How many consumers to create
		
		/**
		 * Producer object that generates 100 random numbers between 1-100 and adds them to the buffer one at a time for the consumers to use.
		 */
		Runnable producer = new Runnable() {
			@Override
			public void run() {
				Random rand = new Random();
				for(int i = 0; i < 100; i++) {
					Integer num = rand.nextInt(100);
					Boolean pushed = false;
					while(!pushed) {
						synchronized(bb) {
							if(!bb.full()) {
								bb.push(num);
								pushed = true;
							}
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				synchronized(doneProducing) {
					doneProducing++;
				}
			}			
		};
		
		/**
		 * Consumer object that keeps retrieving numbers from the buffer and printing them out until the producers are done and the buffer is empty.
		 */
		Runnable consumer = new Runnable() {
			@Override
			public void run() {
				while(!finished) {
					Integer num = null;
					while(num == null && !finished) {
						synchronized(bb) {
							if(!bb.empty()) {
								num = bb.pop();
							}
							else {
								synchronized(doneProducing) {
									if(doneProducing >= numProducers) {
										finished = true;
									}
								}
							}
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if(num != null)
						System.out.println(num);
				}
			}			
		};
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for(int i = 0; i < numProducers; i++) {
			Thread t = new Thread(producer);
			t.start();
			threads.add(t);			
		}
		for(int i = 0; i < numConsumers; i++) {
			Thread t = new Thread(consumer);
			t.start();
			threads.add(t);
		}
	}
}
