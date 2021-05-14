/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * A simple object to hold the data for an entry in the tbl_library_branch table
 * @author Eric Colvin
 *
 */
public class Branch {
	private int branchId;
	private String branchName;
	private String branchAddress;
	
	/**
	 * Simple Constructor
	 * @param branchId - Branch's Primary Key
	 * @param branchName - Branch's Name
	 * @param branchAddress - Branch's Location
	 */
	public Branch(int branchId, String branchName, String branchAddress) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchAddress = branchAddress;
	}

	/**
	 * Simple getter for branchId
	 * @return - branchId
	 */
	public int getBranchId() {
		return branchId;
	}

	/**
	 * Simple getter for branchName
	 * @return - branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * Simple getter for branchAddress
	 * @return - branchAddress
	 */
	public String getBranchAddress() {
		return branchAddress;
	}
}
