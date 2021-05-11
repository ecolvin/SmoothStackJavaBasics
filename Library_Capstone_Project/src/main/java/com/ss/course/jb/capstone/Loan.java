/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * @author Eric Colvin
 *
 */
public class Loan {
	private int bookId;
	private int branchId;
	private int cardNo;
	private String dateOut;
	private String dueDate;
	
	public Loan(int bookId, int branchId, int cardNo, String dateOut, String dueDate) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
	}

	public int getBookId() {
		return bookId;
	}

	public int getBranchId() {
		return branchId;
	}

	public int getCardNo() {
		return cardNo;
	}

	public String getDateOut() {
		return dateOut;
	}

	public String getDueDate() {
		return dueDate;
	}
}
