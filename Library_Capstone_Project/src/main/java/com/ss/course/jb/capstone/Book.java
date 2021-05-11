/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * @author Eric Colvin
 *
 */
public class Book {
	private int bookId;
	private String title;
	private int authId;
	private int pubId;
	
	public Book(int bookId, String title, int authId, int pubId) {
		this.bookId = bookId;
		this.title = title;
		this.authId = authId;
		this.pubId = pubId;
	}
	
	public int getBookId() {
		return bookId;
	}
	
	public String getTitle() {
		return title;
	}

	public int getAuthId() {
		return authId;
	}

	public int getPubId() {
		return pubId;
	}
}
