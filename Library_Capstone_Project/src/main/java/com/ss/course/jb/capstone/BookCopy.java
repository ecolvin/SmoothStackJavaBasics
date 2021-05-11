/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * @author Eric Colvin
 *
 */
public class BookCopy {
	private int bookId;
	private int branchId;
	private int noOfCopies;
	
	public BookCopy(int bookId, int branchId, int noOfCopies) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.noOfCopies = noOfCopies;
	}

	public int getBookId() {
		return bookId;
	}

	public int getBranchId() {
		return branchId;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}
}
