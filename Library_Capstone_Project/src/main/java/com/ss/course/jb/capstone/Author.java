/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * A simple object to hold the data for an entry in the tbl_author table
 * @author Eric Colvin
 *
 */
public class Author {
	private int authorId;
	private String authorName;
	
	/**
	 * Simple Constructor
	 * @param authorId - Author's Primary Key
	 * @param authorName - Author's Name
	 */
	public Author(int authorId, String authorName) {
		this.authorId = authorId;
		this.authorName = authorName;
	}

	/**
	 * Simple getter for authorId
	 * @return - authorId
	 */
	public int getAuthorId() {
		return authorId;
	}
	
	/**
	 * Simple getter for authorName
	 * @return - authorName
	 */
	public String getAuthorName() {
		return authorName;
	}	
}
