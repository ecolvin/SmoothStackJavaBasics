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
				input.nextLine();
				admin(input);
				break;
			case 3:
				borr(input);
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
		if("N/A".equals(newBranchName.toUpperCase())) {
			newBranchName = branchName;
		}
		if("N/A".equals(newBranchAddress.toUpperCase())) {
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
	
	
	public static void borr(Scanner input) {
		System.out.println("Enter your Card Number:");
		int cardNo = 0;
		try {
			cardNo = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			borr(input);
		}	
		if(cardNo > 0) {  //SQL: check if card number is valid
			borr1(input, cardNo);
		} else {
			System.out.println("Sorry, " + cardNo + " is not a valid card number.");
			borr(input);
		}
	}
	
	public static void borr1(Scanner input, int cardNo) {
		System.out.println("\t1. Check out a book");
		System.out.println("\t2. Return a book");
		System.out.println("\t3. Quit to Previous");
		System.out.println();
		int selection = 0;
		try {
			selection = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			borr1(input, cardNo);
		}
		switch(selection) {
			case 1:
				borrCheckOutBranch(input, cardNo);
				break;
			case 2:
				borrReturnBranch(input, cardNo);
				break;
			case 3:
				getUserCat(input);
				break;
			default:
				System.out.println("\'" + selection + "\' is not a valid selection.");
				System.out.println("Please select an option:");
				System.out.println();
				borr1(input, cardNo);
		}
	}
	
	public static void borrCheckOutBranch(Scanner input, int cardNo) {
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
		
		System.out.println("Pick the Branch you want to check out from:");
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
			borrCheckOutBranch(input, cardNo);
		}
		if(branchID > 0 && branchID <= branchNames.size()) {
			borrCheckOutBook(input, cardNo, branchID);
		} else if(branchID == (branchNames.size()+1)) {
			borr1(input, cardNo);
		} else {
			System.out.println("\'" + branchID + "\' is not a valid branch.");
			borrCheckOutBranch(input, cardNo);
		}
	}
	
	public static void borrCheckOutBook(Scanner input, int cardNo, int branchID) {
		List<String> bookNames = new ArrayList<String>(); //SQL: Get all books from the table
		bookNames.add("Lost Tribe");
		bookNames.add("The Haunting");
		bookNames.add("Microtrends");
		List<String> bookAuthors = new ArrayList<String>();
		bookAuthors.add("Sidney Sheldon");
		bookAuthors.add("Stephen King");
		bookAuthors.add("Mark Penn");		
		
		System.out.println("Pick the Book you want to check out:");
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
			borrCheckOutBook(input, cardNo, branchID);
		}
		if(bookID > 0 && bookID <= bookNames.size()) {
			//SQL: Check out the specified book
		} else if(bookID == (bookNames.size()+1)) {
			borr1(input, cardNo);
		} else {
			System.out.println("\'" + bookID + "\' is not a valid book.");
			borrCheckOutBook(input, cardNo, branchID);
		}
	}
	
	public static void borrReturnBranch(Scanner input, int cardNo) {
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
		
		System.out.println("Pick the Branch you want to return to:");
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
			borrReturnBranch(input, cardNo);
		}
		if(branchID > 0 && branchID <= branchNames.size()) {
			borrReturnBook(input, cardNo, branchID);
		} else if(branchID == (branchNames.size()+1)) {
			borr1(input, cardNo);
		} else {
			System.out.println("\'" + branchID + "\' is not a valid branch.");
			borrReturnBranch(input, cardNo);
		}
	}
	
	public static void borrReturnBook(Scanner input, int cardNo, int branchID) {
		List<String> bookNames = new ArrayList<String>(); //SQL: Get all books from the table
		bookNames.add("Lost Tribe");
		bookNames.add("The Haunting");
		bookNames.add("Microtrends");
		List<String> bookAuthors = new ArrayList<String>();
		bookAuthors.add("Sidney Sheldon");
		bookAuthors.add("Stephen King");
		bookAuthors.add("Mark Penn");		
		
		System.out.println("Pick the Book you want to return:");
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
			borrReturnBook(input, cardNo, branchID);
		}
		if(bookID > 0 && bookID <= bookNames.size()) {
			//SQL: return the specified book
		} else if(bookID == (bookNames.size()+1)) {
			borr1(input, cardNo);
		} else {
			System.out.println("\'" + bookID + "\' is not a valid book.");
			borrReturnBook(input, cardNo, branchID);
		}
	}
	
	public static void admin(Scanner input) {
		final String PASSWORD = "Password";
		System.out.println("Please enter the admin password (enter \'quit\' to quit):");
		String pass = input.nextLine();
		if(PASSWORD.equals(pass)) {
			admin1(input);
		} else if("QUIT".equals(pass.toUpperCase())) {
			getUserCat(input);
		} else {
			System.out.println("Sorry, that is incorrect. Please try again.");
			admin(input);
		}
	}
	
	public static void admin1(Scanner input) {
		System.out.println("What would you like to do:");
		System.out.println();
		System.out.println("\t1. Add Data");
		System.out.println("\t2. Update Data");
		System.out.println("\t3. Delete Data");
		System.out.println("\t4. Override the Due Date for a Book Loan");
		System.out.println("\t5. Quit to Previous");
		System.out.println();
		int selection = 0;
		try {
			selection = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			admin1(input);
		}
		switch(selection) {
			case 1:
				adminAdd(input);
				break;
			case 2:
				adminUpdate(input);
				break;
			case 3:
				adminDelete(input);
				break;
			case 4:
				adminOverrideDueDate(input);
				break;
			case 5:
				getUserCat(input);
				break;
			default:
				System.out.println("\'" + selection + "\' is not a valid selection.");
				admin1(input);
		}
	}
	
	public static void adminAdd(Scanner input) {
		System.out.println("Which table would you like to add to:");
		System.out.println();
		System.out.println("\t1. Books");
		System.out.println("\t2. Authors");
		System.out.println("\t3. Publishers");
		System.out.println("\t4. Library Branches");
		System.out.println("\t5. Borrowers");
		System.out.println("\t6. Quit to Previous");
		System.out.println();
		int table = 0;
		try {
			table = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminAdd(input);
		}
		switch(table) {
			case 1:
				input.nextLine();
				adminAddBookTitle(input);
				break;
			case 2:
				input.nextLine();
				adminAddAuthor(input);
				break;
			case 3:
				input.nextLine();
				adminAddPublisher(input);
				break;
			case 4:
				input.nextLine();
				adminAddBranch(input);
				break;
			case 5:
				input.nextLine();
				adminAddBorrower(input);
				break;
			case 6:
				admin1(input);
				break;
			default:
				System.out.println(table + " is not a valid table choice.");
				adminAdd(input);
		}		
	}
	
	public static void adminUpdate(Scanner input) {
		System.out.println("Which table would you like to add to:");
		System.out.println();
		System.out.println("\t1. Books");
		System.out.println("\t2. Authors");
		System.out.println("\t3. Publishers");
		System.out.println("\t4. Library Branches");
		System.out.println("\t5. Borrowers");
		System.out.println("\t6. Quit to Previous");
		System.out.println();
		int table = 0;
		try {
			table = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminUpdate(input);
		}
		switch(table) {
			case 1:
				input.nextLine();
				adminUpdateBook(input);
				break;
			case 2:
				input.nextLine();
				adminUpdateAuthor(input);
				break;
			case 3:
				input.nextLine();
				adminUpdatePublisher(input);
				break;
			case 4:
				input.nextLine();
				adminUpdateBranch(input);
				break;
			case 5:
				input.nextLine();
				adminUpdateBorrower(input);
				break;
			case 6:
				admin1(input);
				break;
			default:
				System.out.println(table + " is not a valid table choice.");
				adminUpdate(input);
		}		
	}
	
	public static void adminDelete(Scanner input) {
		System.out.println("Which table would you like to add to:");
		System.out.println();
		System.out.println("\t1. Books");
		System.out.println("\t2. Authors");
		System.out.println("\t3. Publishers");
		System.out.println("\t4. Library Branches");
		System.out.println("\t5. Borrowers");
		System.out.println("\t6. Quit to Previous");
		System.out.println();
		int table = 0;
		try {
			table = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDelete(input);
		}
		switch(table) {
			case 1:
				adminDeleteBook(input);
				break;
			case 2:
				adminDeleteAuthor(input);
				break;
			case 3:
				adminDeletePublisher(input);
				break;
			case 4:
				adminDeleteBranch(input);
				break;
			case 5:
				adminDeleteBorrower(input);
				break;
			case 6:
				admin1(input);
				break;
			default:
				System.out.println(table + " is not a valid table choice.");
				adminDelete(input);
		}		
	}
	
	public static void adminAddBookTitle(Scanner input) {
		System.out.println("What is the title of the book (enter \'quit\' to quit):");
		String title = input.nextLine();
		if("QUIT".equals(title.toUpperCase())) {
			adminAdd(input);
		} else {
			adminAddBookAuthor(input, title);
		}
	}
	
	public static void adminAddBookAuthor(Scanner input, String title) {
		System.out.println("What is the name of the book's author (enter \'quit\' to quit):");
		String author = input.nextLine();
		if("QUIT".equals(author.toUpperCase())) {
			adminAdd(input);
		} else if(!("".equals(author))) {   //SQL: Check if valid author and get
			int authorID = -1; //SQL: get authorID
			adminAddBookPublisher(input, title, authorID); 
		} else {
			System.out.println("Could not find an author with the name: " + author);
			System.out.println("Please input a different name or quit and add them to the author table first.");
			adminAddBookAuthor(input, title);
		}
	}
	
	public static void adminAddBookPublisher(Scanner input, String title, int authorID) {
		System.out.println("What is the name of the book's publisher (enter \'quit\' to quit):");
		String publisher = input.nextLine();
		if("QUIT".equals(publisher.toUpperCase())) {
			adminAdd(input);
		} else if(!("".equals(publisher))) {  //SQL: Check if valid publisher
			//SQL: add a new book 
		} else {
			System.out.println("Could not find an publisher with the name: " + publisher);
			System.out.println("Please input a different name or quit and add them to the publisher table first.");
			adminAddBookAuthor(input, title);
		}
	}
	
	public static void adminAddAuthor(Scanner input) {
		System.out.println("What is the name of the author (enter \'quit\' to quit):");
		String authorName = input.nextLine();
		
		//SQL: Add author to table
		
		System.out.println("Would you like to add another author (y):");
		String response = input.nextLine();
		if("Y".equals(response.toUpperCase()) || "YES".equals(response.toUpperCase())) {
			adminAddAuthor(input);
		} else {
			adminAdd(input);
		}
	}
	
	public static void adminAddPublisher(Scanner input) {
		System.out.println("What is the name of the publisher (enter \'quit\' to quit):");
		String publisherName = input.nextLine();
		System.out.println("What is the address of the publisher (enter \'quit\' to quit):");
		String publisherAddress = input.nextLine();
		System.out.println("What is the phone number of the publisher (enter \'quit\' to quit):");
		String publisherPhoneNumber = input.nextLine();
		
		//SQL: Add publisher to table
		
		System.out.println("Would you like to add another publisher (y):");
		String response = input.nextLine();
		if("Y".equals(response.toUpperCase()) || "YES".equals(response.toUpperCase())) {
			adminAddPublisher(input);
		} else {
			adminAdd(input);
		}
	}
	
	public static void adminAddBranch(Scanner input) {
		System.out.println("What is the name of the branch (enter \'quit\' to quit):");
		String branchName = input.nextLine();
		System.out.println("What is the address of the branch (enter \'quit\' to quit):");
		String branchAddress = input.nextLine();
		
		//SQL: Add branch to table
		
		System.out.println("Would you like to add another branch (y):");
		String response = input.nextLine();
		if("Y".equals(response.toUpperCase()) || "YES".equals(response.toUpperCase())) {
			adminAddBranch(input);
		} else {
			adminAdd(input);
		}
	}
	
	public static void adminAddBorrower(Scanner input) {
		System.out.println("What is the name of the borrower (enter \'quit\' to quit):");
		String borrowerName = input.nextLine();
		System.out.println("What is the address of the borrower (enter \'quit\' to quit):");
		String borrowerAddress = input.nextLine();
		System.out.println("What is the phone number of the borrower (enter \'quit\' to quit):");
		String borrowerPhoneNumber = input.nextLine();
		
		//SQL: Add borrower to table (Print out cardNo to simulate printing a library card)
		
		System.out.println("Would you like to add another borrower (y):");
		String response = input.nextLine();
		if("Y".equals(response.toUpperCase()) || "YES".equals(response.toUpperCase())) {
			adminAddBorrower(input);
		} else {
			adminAdd(input);
		}
	}
	
	public static void adminUpdateBook(Scanner input) {
		List<String> bookNames = new ArrayList<String>(); //SQL: Get all books from the table
		bookNames.add("Lost Tribe");
		bookNames.add("The Haunting");
		bookNames.add("Microtrends");
		List<String> bookAuthors = new ArrayList<String>();
		bookAuthors.add("Sidney Sheldon");
		bookAuthors.add("Stephen King");
		bookAuthors.add("Mark Penn");		
		
		System.out.println("Pick the Book you want to update:");
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
			adminDeleteBook(input);
		}
		if(bookID > 0 && bookID <= bookNames.size()) {
			adminUpdateBookInput(input, bookID);
		} else if(bookID == (bookNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + bookID + "\' is not a valid book.");
			adminDeleteBook(input);
		}
	}
	
	public static void adminUpdateBookInput(Scanner input, int bookID) {
		String bookName = "Temp Book Name"; //SQL: Get the book name from the book table
		String bookAuthor = "Temp Book Address"; //SQL: Get the book author from the book table
		String bookPublisher = "Temp Book Publisher"; //SQL: Get the book publisher from the book table
		System.out.println("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".");
		System.out.println();
		System.out.println("Please enter a new title or enter N/A for no change:");
		String newBookName = input.nextLine();
		System.out.println("Please enter a new author or enter N/A for no change:");
		String newBookAuthor = input.nextLine();
		System.out.println("Please enter a new publisher or enter N/A for no change:");
		String newBookPublisher = input.nextLine();
		if("N/A".equals(newBookName.toUpperCase())) {
			newBookName = bookName;
		}
		if("N/A".equals(newBookAuthor.toUpperCase())) {
			newBookAuthor = bookAuthor;
		}
		if("N/A".equals(newBookPublisher.toUpperCase())) {
			newBookPublisher = bookPublisher;
		}
		
		//TWEAK THIS TO RESET IF INVALID AUTHOR/PUBLISHER INPUTTED
		
		//SQL: update book table with new values
		
		adminUpdateBook(input);
	}
	
	public static void adminUpdateAuthor(Scanner input) {
		List<String> authorNames = new ArrayList<String>(); //SQL: Get all authors from the table
		authorNames.add("Sidney Sheldon");
		authorNames.add("Stephen King");
		authorNames.add("Mark Penn");		
		
		System.out.println("Pick the Author you want to update:");
		System.out.println();
		int i;
		for(i = 0; i < authorNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + authorNames.get(i));
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int authorID = 0;
		try {
			authorID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteAuthor(input);
		}
		if(authorID > 0 && authorID <= authorNames.size()) {
			adminUpdateAuthorInput(input, authorID);
		} else if(authorID == (authorNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + authorID + "\' is not a valid author.");
			adminDeleteAuthor(input);
		}
	}
	
	public static void adminUpdateAuthorInput(Scanner input, int authorID) {
		String authorName = "Temp Author Name"; //SQL: Get the author name from the author table
		System.out.println("You have chosen to update the Author: " + authorName + ".");
		System.out.println();
		System.out.println("Please enter a new name or enter N/A for no change:");
		String newAuthorName = input.nextLine();
		if("N/A".equals(newAuthorName.toUpperCase())) {
			newAuthorName = authorName;
		}
		
		//SQL: update author table with new values
		
		adminUpdateAuthor(input);
	}
	
	public static void adminUpdatePublisher(Scanner input) {
		List<String> publisherNames = new ArrayList<String>(); //SQL: Get all publishers from the table
		publisherNames.add("Penguin Random House LLC");
		publisherNames.add("Pearson");
		publisherNames.add("Simon and Schuster");
		List<String> publisherAddresses = new ArrayList<String>();
		publisherAddresses.add("New York City NY");
		publisherAddresses.add("New York City NY");
		publisherAddresses.add("London UK");		
		
		System.out.println("Pick the Publisher you want to update:");
		System.out.println();
		int i;
		for(i = 0; i < publisherNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + publisherNames.get(i) + ", " + publisherAddresses.get(i));
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int publisherID = 0;
		try {
			publisherID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeletePublisher(input);
		}
		if(publisherID > 0 && publisherID <= publisherNames.size()) {
			adminUpdatePublisherInput(input, publisherID);
		} else if(publisherID == (publisherNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + publisherID + "\' is not a valid publisher.");
			adminDeletePublisher(input);
		}
	}
	
	public static void adminUpdatePublisherInput(Scanner input, int publisherID) {
		String publisherName = "Temp Publisher Name"; //SQL: Get the book name from the book table
		String publisherAddress = "1 Street Rd."; //SQL: Get the book author from the book table
		String publisherPhone = "(111)-111-1111"; //SQL: Get the book publisher from the book table
		System.out.println("You have chosen to update the Publisher: " + publisherName + ", " + publisherAddress + " - " + publisherPhone + ".");
		System.out.println();
		System.out.println("Please enter a new name or enter N/A for no change:");
		String newPublisherName = input.nextLine();
		System.out.println("Please enter a new address or enter N/A for no change:");
		String newPublisherAddress = input.nextLine();
		System.out.println("Please enter a new phone number or enter N/A for no change:");
		String newPublisherPhone = input.nextLine();
		if("N/A".equals(newPublisherName.toUpperCase())) {
			newPublisherName = publisherName;
		} 
		if("N/A".equals(newPublisherAddress.toUpperCase())) {
			newPublisherAddress = publisherAddress;
		}
		if("N/A".equals(newPublisherPhone.toUpperCase())) {
			newPublisherPhone = publisherPhone;
		}
		
		//SQL: update book table with new values
		
		adminUpdatePublisher(input);
	}
	
	public static void adminUpdateBranch(Scanner input) {
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
		
		System.out.println("Pick the Branch you want to update:");
		System.out.println();
		int i;
		for(i = 0; i < branchNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + branchNames.get(i) + ", " + branchAddresses.get(i));
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int branchID = 0;
		try {
			branchID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteBranch(input);
		}
		if(branchID > 0 && branchID <= branchNames.size()) {
			adminUpdateBranchInput(input, branchID);
		} else if(branchID == (branchNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + branchID + "\' is not a valid branch.");
			adminDeleteBranch(input);
		}
	}
	
	public static void adminUpdateBranchInput(Scanner input, int branchID) {
		String branchName = "Temp Branch Name"; //SQL: Get the branch name from the branch table
		String branchAddress = "Temp Branch Address"; //SQL Get the branch address from the branch table
		System.out.println("You have chosen to update the Branch: " + branchName + ", " + branchAddress + ".");
		System.out.println();
		System.out.println("Please enter new branch name or enter N/A for no change:");
		String newBranchName = input.nextLine();
		System.out.println("Please enter a new branch address or enter N/A for no change:");
		String newBranchAddress = input.nextLine();
		if("N/A".equals(newBranchName.toUpperCase())) {
			newBranchName = branchName;
		}
		if("N/A".equals(newBranchAddress.toUpperCase())) {
			newBranchAddress = branchAddress;
		}
		
		//SQL: update branch table with new values
		
		adminUpdateBranch(input);
	}
	
	public static void adminUpdateBorrower(Scanner input) {
		List<String> borrowerNames = new ArrayList<String>(); //SQL: Get all borrowers from the table
		borrowerNames.add("John Doe");
		borrowerNames.add("Craig Smith");
		borrowerNames.add("Sara Jones");
		List<String> borrowerCardNos = new ArrayList<String>();
		borrowerCardNos.add("3");
		borrowerCardNos.add("17");
		borrowerCardNos.add("69");		
		
		System.out.println("Pick the Borrower you want to update:");
		System.out.println();
		int i;
		for(i = 0; i < borrowerNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + borrowerNames.get(i) + ", Card #: " + borrowerCardNos.get(i));
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int borrowerID = 0;
		try {
			borrowerID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteBorrower(input);
		}
		if(borrowerID > 0 && borrowerID <= borrowerNames.size()) {
			adminUpdateBorrowerInput(input, borrowerID);
		} else if(borrowerID == (borrowerNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + borrowerID + "\' is not a valid borrower.");
			adminDeleteBorrower(input);
		}
	}
	
	public static void adminUpdateBorrowerInput(Scanner input, int borrowerID) {
		String borrowerName = "Temp Borrower Name"; //SQL: Get the borrower name from the borrower table
		String borrowerAddress = "1 Street Rd."; //SQL: Get the borrower address from the borrower table
		String borrowerPhone = "(111)-111-1111"; //SQL: Get the borrower phone # from the borrower table
		System.out.println("You have chosen to update the Borrower: " + borrowerName + ", " + borrowerAddress + " - " + borrowerPhone + ".");
		System.out.println();
		System.out.println("Please enter a new name or enter N/A for no change:");
		String newBorrowerName = input.nextLine();
		System.out.println("Please enter a new address or enter N/A for no change:");
		String newBorrowerAddress = input.nextLine();
		System.out.println("Please enter a new phone number or enter N/A for no change:");
		String newBorrowerPhone = input.nextLine();
		if("N/A".equals(newBorrowerName.toUpperCase())) {
			newBorrowerName = borrowerName;
		} 
		if("N/A".equals(newBorrowerAddress.toUpperCase())) {
			newBorrowerAddress = borrowerAddress;
		}
		if("N/A".equals(newBorrowerPhone.toUpperCase())) {
			newBorrowerPhone = borrowerPhone;
		}
		
		//SQL: update book table with new values
		
		adminUpdateBorrower(input);
	}
	
	public static void adminDeleteBook(Scanner input) {
		List<String> bookNames = new ArrayList<String>(); //SQL: Get all books from the table
		bookNames.add("Lost Tribe");
		bookNames.add("The Haunting");
		bookNames.add("Microtrends");
		List<String> bookAuthors = new ArrayList<String>();
		bookAuthors.add("Sidney Sheldon");
		bookAuthors.add("Stephen King");
		bookAuthors.add("Mark Penn");		
		
		System.out.println("Pick the Book you want to delete:");
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
			adminDeleteBook(input);
		}
		if(bookID > 0 && bookID <= bookNames.size()) {
			//SQL: delete the specified book
		} else if(bookID == (bookNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + bookID + "\' is not a valid book.");
			adminDeleteBook(input);
		}
	}
	
	public static void adminDeleteAuthor(Scanner input) {
		List<String> authorNames = new ArrayList<String>(); //SQL: Get all authors from the table
		authorNames.add("Sidney Sheldon");
		authorNames.add("Stephen King");
		authorNames.add("Mark Penn");		
		
		System.out.println("Pick the Author you want to delete:");
		System.out.println();
		int i;
		for(i = 0; i < authorNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + authorNames.get(i));
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int authorID = 0;
		try {
			authorID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteAuthor(input);
		}
		if(authorID > 0 && authorID <= authorNames.size()) {
			//SQL: delete the specified author
		} else if(authorID == (authorNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + authorID + "\' is not a valid author.");
			adminDeleteAuthor(input);
		}
	}
	
	public static void adminDeletePublisher(Scanner input) {
		List<String> publisherNames = new ArrayList<String>(); //SQL: Get all publishers from the table
		publisherNames.add("Penguin Random House LLC");
		publisherNames.add("Pearson");
		publisherNames.add("Simon and Schuster");
		List<String> publisherAddresses = new ArrayList<String>();
		publisherAddresses.add("New York City NY");
		publisherAddresses.add("New York City NY");
		publisherAddresses.add("London UK");		
		
		System.out.println("Pick the Publisher you want to delete:");
		System.out.println();
		int i;
		for(i = 0; i < publisherNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + publisherNames.get(i) + ", " + publisherAddresses.get(i));
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int publisherID = 0;
		try {
			publisherID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeletePublisher(input);
		}
		if(publisherID > 0 && publisherID <= publisherNames.size()) {
			//SQL: delete the specified publisher
		} else if(publisherID == (publisherNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + publisherID + "\' is not a valid publisher.");
			adminDeletePublisher(input);
		}
	}
	
	public static void adminDeleteBranch(Scanner input) {
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
		
		System.out.println("Pick the Branch you want to delete:");
		System.out.println();
		int i;
		for(i = 0; i < branchNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + branchNames.get(i) + ", " + branchAddresses.get(i));
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int branchID = 0;
		try {
			branchID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteBranch(input);
		}
		if(branchID > 0 && branchID <= branchNames.size()) {
			//SQL: delete the specified branch
		} else if(branchID == (branchNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + branchID + "\' is not a valid branch.");
			adminDeleteBranch(input);
		}
	}
	
	public static void adminDeleteBorrower(Scanner input) {
		List<String> borrowerNames = new ArrayList<String>(); //SQL: Get all borrowers from the table
		borrowerNames.add("John Doe");
		borrowerNames.add("Craig Smith");
		borrowerNames.add("Sara Jones");
		List<String> borrowerCardNos = new ArrayList<String>();
		borrowerCardNos.add("3");
		borrowerCardNos.add("17");
		borrowerCardNos.add("69");		
		
		System.out.println("Pick the Borrower you want to delete:");
		System.out.println();
		int i;
		for(i = 0; i < borrowerNames.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + borrowerNames.get(i) + ", Card #: " + borrowerCardNos.get(i));
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int borrowerID = 0;
		try {
			borrowerID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteBorrower(input);
		}
		if(borrowerID > 0 && borrowerID <= borrowerNames.size()) {
			//SQL: delete the specified borrower
		} else if(borrowerID == (borrowerNames.size()+1)) {
			adminDelete(input);
		} else {
			System.out.println("\'" + borrowerID + "\' is not a valid borrower.");
			adminDeleteBorrower(input);
		}
	}
	
	public static void adminOverrideDueDate(Scanner input) {
		List<String> booksBorrowed = new ArrayList<String>();
		booksBorrowed.add("Lost Tribe");
		booksBorrowed.add("The Haunting");
		List<String> branchesBorrowedFrom = new ArrayList<String>();
		branchesBorrowedFrom.add("University Library");
		branchesBorrowedFrom.add("Federal Library");		
		List<String> borrowers = new ArrayList<String>();
		borrowers.add("John Doe");
		borrowers.add("Craig Smith");		
		List<String> checkoutDates = new ArrayList<String>();
		checkoutDates.add("April 5, 2021");
		checkoutDates.add("December 25, 1997");
		List<String> dueDates = new ArrayList<String>();
		dueDates.add("May 5, 2021");
		dueDates.add("December 25, 2097");
		
		System.out.println("Which rental would you like to override the due date for:");
		System.out.println();
		int i;
		for(i = 0; i < booksBorrowed.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + booksBorrowed.get(i) + "; From: " + branchesBorrowedFrom.get(i) + "; By: " + borrowers.get(i) + "; (" + checkoutDates.get(i) + "-" + dueDates.get(i) + ")");                                     
		}
		System.out.println("\t" + (i+1) + "Quit to previous");
		System.out.println();
		int loanID = 0;
		try {
			loanID = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminOverrideDueDate(input);
		}
		if(loanID > 0 && loanID <= booksBorrowed.size()) {
			adminOverrideDueDateInput(input, loanID, loanID, loanID); //SQL: Update values passed
		} else if(loanID == (booksBorrowed.size()+1)) {
			admin1(input);
		} else {
			System.out.println("\'" + loanID + "\' is not a valid book rental.");
			adminOverrideDueDate(input);
		}
	}
	
	public static void adminOverrideDueDateInput(Scanner input, int bookID, int branchID, int cardNo) {
		String bookName = "The Haunting"; //SQL: Get from the table
		String branchName = "University Library"; //SQL: Get from the table
		String borrowerName = "Craig Smith"; //SQL: Get from the table
		String checkoutDate = "December 25, 1997";
		String dueDate = "December 25, 2097";
		System.out.println("You have chosen to update the loan of " + bookName + " from " + branchName + " by " + borrowerName + ".");
		System.out.println("This book was checked out " + checkoutDate + " and is due " + dueDate + ".");
		System.out.println();
		System.out.println("Please enter a new due date, enter Override to remove the due date, or enter N/A for no change:");
		String newDueDate = input.nextLine();
		if("N/A".equals(newDueDate.toUpperCase())) {
			newDueDate = borrowerName;
		}
		
		//SQL: update book table with new values
		
		adminOverrideDueDate(input);
	}
}
