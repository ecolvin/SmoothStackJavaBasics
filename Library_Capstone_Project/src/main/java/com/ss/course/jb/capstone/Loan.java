/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * A simple object to hold the data for an entry in the tbl_book_loans table
 * @author Eric Colvin
 *
 */
public class Loan {
	private int bookId;
	private int branchId;
	private int cardNo;
	private String dateOut;
	private String dueDate;
	
	/**
	 * Simple Constructor
	 * @param bookId - Foreign Key of the book that was checked out
	 * @param branchId - Foreign Key of the branch the book was checked out from
	 * @param cardNo - Foreign Key of the borrower checking out the book
	 * @param dateOut - Date the book was checked out
	 * @param dueDate - Date the book is due to be returned
	 */
	public Loan(int bookId, int branchId, int cardNo, String dateOut, String dueDate) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
	}

	/**
	 * Simple getter for bookId
	 * @return - bookId
	 */
	public int getBookId() {
		return bookId;
	}

	/**
	 * Simple getter for branchId
	 * @return - branchId
	 */
	public int getBranchId() {
		return branchId;
	}

	/**
	 * Simple getter for cardNo
	 * @return - cardNo
	 */
	public int getCardNo() {
		return cardNo;
	}

	/**
	 * Simple getter for dateOut
	 * @return - dateOut
	 */
	public String getDateOut() {
		return dateOut;
	}

	/**
	 * Simple getter for dueDate
	 * @return - dueDate
	 */
	public String getDueDate() {
		return dueDate;
	}
}
