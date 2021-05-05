/**
 * 
 */
package com.ss.course.jb.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple Library Management System
 * @author Eric Colvin
 */
public class Main {

	/**
	 * Print a welcome message and then call the getUserCat method to prompt the user for their category
	 * @param args - Command Line arguments (unused)
	 */
	public static void main(String[] args) {
		System.out.print("Welcome to the GCIT Library Management System. ");
		getUserCat(new Scanner(System.in));
	}
	
	/**
	 * Prompt the user to select their user category and then call its respective method
	 * @param input - Scanner for user input
	 */
	public static void getUserCat(Scanner input) {
		System.out.println("Which category of user are you:");
		System.out.println();
		System.out.println("\t1. Librarian");
		System.out.println("\t2. Administrator");
		System.out.println("\t3. Borrower");
		System.out.println();
		int userCat = 0;
		try {
			userCat = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			getUserCat(input);
		}
		switch(userCat) {
			case 1:
				lib1(input);
				break;
			case 2:
				//admin(input);
				break;
			case 3:
				//borr(input);
				break;
			default:
				System.out.println("\'" + userCat + "\' is not a valid user category.");
				getUserCat(input);
		}
	}
	
	/**
	 * Ask if the user wants to select a branch to edit or go back and then call the respective method (lib2 to pick a branch, getUserCat to go back)
	 * @param input - Scanner for user input
	 */
	public static void lib1(Scanner input) {
		System.out.println("\t1. Select Your Branch");
		System.out.println("\t2. Quit to previous");
		System.out.println();
		int selection = 0;
		try {
			selection = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			lib1(input);
		}
		switch(selection) {
			case 1:
				lib2(input);
				break;
			case 2:
				getUserCat(input);
				break;
			default:
				System.out.println("\'" + selection + "\' is not a valid selection.");
				System.out.println("Please select an option:");
				System.out.println();
				lib1(input);
		}
	}
	
	/**
	 * Prompt the user to select the branch they want to work with and then pass it to lib3 or return to lib1 if they choose to quit to previous
	 * @param input - Scanner for user input
	 */
	public static void lib2(Scanner input) {
		List<String> branchNames = new ArrayList<String>(); //SQL: Get all branches from the table
		branchNames.add("University Library");
		branchNames.add("State Library");
		branchNames.add("Federal Library");
		branchNames.add("County Library");
		List<String> branchAddresses = new ArrayList<String>();
		branchAddresses.add("Boston");
		branchAddresses.add("New York");
		branchAddresses.add("Washington DC");
		branchAddresses.add("McLean, VA");
		
		int i;
		for(i = 0; i < branchNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + branchNames.get(i) + ", " + branchAddresses.get(i));
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int branchID = 0;
		try{
			branchID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			lib2(input);
		}
		if(branchID > 0 && branchID <= branchNames.size()) {
			lib3(input, branchID);
		} else if(branchID == (branchNames.size()+1)) {
			lib1(input);
		} else {
			System.out.println("\'" + branchID + "\' is not a valid branch.");
			System.out.println("Please select your branch:");
			System.out.println();
		}
	}
	
	/**
	 * Ask the user whether they want to update the library details or add copies of a book and call the respective functions:
	 * 	- libUpdate: Update the library details
	 * 	- libAddBook: Add copies of a book
	 * 	- lib2: Quit to previous
	 * @param input - Scanner for user input
	 * @param branchID - the id of the selected branch
	 */
	public static void lib3(Scanner input, int branchID) {
		System.out.println("\t1. Update the details of the Library");
		System.out.println("\t2. Add copies of a book to the Branch");
		System.out.println("\t3. Quit to previous");
		System.out.println("");
		int selection = 0; 
		try {
			selection = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			lib3(input, branchID);
		}
		switch(selection) {
			case 1:
				libUpdate(input, branchID);
				break;
			case 2:
				libAddBook(input, branchID);
				break;
			case 3:
				lib2(input);
				break;
			default:
				System.out.println("\'" + selection + "\' is not a valid selection.");
				System.out.println("Please select an option:");
				System.out.println();
				lib3(input, branchID);
		}
	}
	
	/**
	 * Provide the option to input a new branch name and branch address and update those values for the specified branchID in the database
	 * @param input - Scanner for user input
	 * @param branchID - the id of the selected branch
	 */
	public static void libUpdate(Scanner input, int branchID) {
		String branchName = "Temp Branch Name"; //SQL: Get the branch name from the branch table
		String branchAddress = "Temp Branch Address"; //SQL Get the branch address from the branch table
		System.out.println("You have chosen to update the Branch with Branch ID: " + branchID + " and Branch Name: " + branchName + ".");
		System.out.println();
		System.out.println("Please enter new branch name or enter N/A for no change:");
		input.nextLine();
		String newBranchName = input.nextLine();
		System.out.println("Please enter a new branch address or enter N/A for no change:");
		String newBranchAddress = input.nextLine();
		if(newBranchName.toUpperCase().equals("N/A")) {
			newBranchName = branchName;
		}
		if(newBranchAddress.toUpperCase().equals("N/A")) {
			newBranchAddress = branchAddress;
		}
		
		//SQL: update branch table with new values
		
		lib3(input, branchID);
	}
	
	/**
	 * Prompt the user to input which book they want to add copies of and then pass that bookID to getNumCopies or call lib3 to quit to previous
	 * @param input - Scanner for user input
	 * @param branchID - the id of the selected branch
	 */
	public static void libAddBook(Scanner input, int branchID) {		
		List<String> bookNames = new ArrayList<String>(); //SQL: Get all books from the table
		bookNames.add("Lost Tribe");
		bookNames.add("The Haunting");
		bookNames.add("Microtrends");
		List<String> bookAuthors = new ArrayList<String>();
		bookAuthors.add("Sidney Sheldon");
		bookAuthors.add("Stephen King");
		bookAuthors.add("Mark Penn");		
		
		System.out.println("Pick the Book you want to add copies of to your branch:");
		System.out.println();
		int i;
		for(i = 0; i < bookNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + bookNames.get(i) + " by " + bookAuthors.get(i));
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int bookID = 0;
		try {
			bookID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			libAddBook(input, branchID);
		}
		if(bookID > 0 && bookID <= bookNames.size()) {
			getNumCopies(input, branchID, bookID);	
		} else if(bookID == (bookNames.size()+1)) {
			lib3(input, branchID);
		} else {
			System.out.println("\'" + bookID + "\' is not a valid book.");
			libAddBook(input, branchID);
		}
	}
	
	/**
	 * Prompt the user to input the new number of copies and update that value for the specified bookID and branchID in the database
	 * @param input - Scanner for user input
	 * @param branchID - the id of the selected branch
	 * @param bookID - the id of the selected book
	 */
	public static void getNumCopies(Scanner input, int branchID, int bookID) {
		int numCopies = 0; //SQL: get numCopies
		System.out.println();
		System.out.println("Existing number of copies: " + numCopies);
		System.out.println();
		System.out.println("Enter new number of copies:");
		System.out.println();
		try {
			numCopies = input.nextInt();
		}catch (Exception e) {
			System.out.println("That is not a valid integer.");
			getNumCopies(input, branchID, bookID);
		}
		
		//SQL: Do Book Stuff
		
		lib3(input, branchID);
	}

}
