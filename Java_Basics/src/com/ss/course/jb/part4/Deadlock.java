/**
 * 
 */
package com.ss.course.jb.part4;

/**
 * Demonstrate deadlock with poorly implemented withdraw and deposit Runnable objects
 * @author Eric Colvin
 *
 */
public class Deadlock {

	static Integer pocket = 75000;
	static Integer bankAccount = 100000;
	
	/**
	 * Create a withdraw and deposit Runnable that both try to acquire locks in opposite order which leads to a deadlock
	 * @param args - Command line arguments (unused)
	 */
	public static void main(String[] args) {		
		
		Runnable withdraw = new Runnable() {
			@Override
			public void run() {
				synchronized(bankAccount) {
					System.out.println("Waiting for confirmation...");
					try {
						Thread.sleep(100);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Confirmed. Withdrawing money now.");
					synchronized(pocket) {
						pocket += 5000;
						bankAccount -= 5000;
						System.out.println("Money successfully withdrawn.");
						System.out.println("Bank Account: " + bankAccount + "; Pocket: " + pocket);
					}
				}
			}
		};
		
		Runnable deposit = new Runnable() {
			@Override
			public void run() {
				synchronized(pocket) {
					System.out.println("Waiting for confirmation...");
					try {
						Thread.sleep(100);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Confirmed. Depositing money now.");
					synchronized(bankAccount) {
						pocket -= 5000;
						bankAccount += 5000;
						System.out.println("Money successfully deposited.");
						System.out.println("Bank Account: " + bankAccount + "; Pocket: " + pocket);
					}
				}
			}			
		};
		
		Thread wd = new Thread(withdraw);
		Thread dep = new Thread(deposit);
		wd.start();
		dep.start();		
	}

}
