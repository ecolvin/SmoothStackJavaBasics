/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * @author Eric Colvin
 *
 */
public class Branch {
	private int branchId;
	private String branchName;
	private String branchAddress;
	
	public Branch(int branchId, String branchName, String branchAddress) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchAddress = branchAddress;
	}

	public int getBranchId() {
		return branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}
}
