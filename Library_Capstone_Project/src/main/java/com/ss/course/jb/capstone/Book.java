/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * A simple object to hold the data for an entry in the tbl_book table
 * @author Eric Colvin
 *
 */
public class Book {
	private int bookId;
	private String title;
	private int authId;
	private int pubId;
	
	/**
	 * Simple Constructor
	 * @param bookId - Book's Primary Key
	 * @param title - Book's Title
	 * @param authId - The Foreign Key of the book's author
	 * @param pubId - The Foreign Key of the book's publisher
	 */
	public Book(int bookId, String title, int authId, int pubId) {
		this.bookId = bookId;
		this.title = title;
		this.authId = authId;
		this.pubId = pubId;
	}
	
	/**
	 * Simple getter for bookId
	 * @return - bookId
	 */
	public int getBookId() {
		return bookId;
	}
	
	/**
	 * Simple getter for title
	 * @return - title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Simple getter for authId
	 * @return - authId
	 */
	public int getAuthId() {
		return authId;
	}

	/**
	 * Simple getter for pubId
	 * @return - pubId
	 */
	public int getPubId() {
		return pubId;
	}
}
