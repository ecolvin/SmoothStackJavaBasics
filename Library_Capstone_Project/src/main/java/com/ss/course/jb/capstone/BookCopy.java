/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * A simple object to hold the data for an entry in the tbl_book_copies table
 * @author Eric Colvin
 *
 */
public class BookCopy {
	private int bookId;
	private int branchId;
	private int noOfCopies;
	
	/**
	 * Simple Constructor
	 * @param bookId - Foreign Key of the book
	 * @param branchId - Foreign Key of the branch
	 * @param noOfCopies - How many copies of the book are at the branch currently
	 */
	public BookCopy(int bookId, int branchId, int noOfCopies) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.noOfCopies = noOfCopies;
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
	 * Simple getter for noOfCopies
	 * @return - noOfCopies
	 */
	public int getNoOfCopies() {
		return noOfCopies;
	}
}
