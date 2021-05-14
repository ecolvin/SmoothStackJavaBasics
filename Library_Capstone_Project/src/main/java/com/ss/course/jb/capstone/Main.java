package com.ss.course.jb.capstone;
/**
 * 
 */

import java.util.List;
import java.util.Scanner;

/**
 * A simple Library Management System
 * @author Eric Colvin
 */
public class Main {

	private static Database db = new Database();
	
	/**
	 * Print a welcome message and then call the getUserCat method to prompt the user for their category
	 * @param args - Command Line arguments (unused)
	 */
	public static void main(String[] args) {
		try {
			db.connectToDB();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.print("Welcome to the GCIT Library Management System. ");
		getUserCat(new Scanner(System.in));
		return;
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
		System.out.println("\t4. Quit");
		System.out.println();
		int userCat = 0;
		try {
			userCat = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			getUserCat(input);
			return;
		}
		switch(userCat) {
			case 1:
				lib1(input);
				return;
			case 2:
				input.nextLine();
				admin(input);
				return;
			case 3:
				borr(input);
				return;
			case 4:
				db.close();
				return;
			default:
				System.out.println("\'" + userCat + "\' is not a valid user category.");
				getUserCat(input);
				return;
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
			return;
		}
		switch(selection) {
			case 1:
				lib2(input);
				return;
			case 2:
				getUserCat(input);
				return;
			default:
				System.out.println("\'" + selection + "\' is not a valid selection.");
				System.out.println("Please select an option:");
				System.out.println();
				lib1(input);
				return;
		}
	}
	
	/**
	 * Prompt the user to select the branch they want to work with and then pass it to lib3 or return to lib1 if they choose to quit to previous
	 * @param input - Scanner for user input
	 */
	public static void lib2(Scanner input) {
		List<Branch> branches;
		try {
			branches = db.getBranches();   
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		int i;
		for(i = 0; i < branches.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + branches.get(i).getBranchName() + ", " + branches.get(i).getBranchAddress());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int branchId = 0;
		try{
			branchId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			lib2(input);
			return;
		}
		if(branchId > 0 && branchId <= branches.size()) {
			lib3(input, branches.get(branchId - 1).getBranchId());
			return;
		} else if(branchId == (branches.size()+1)) {
			lib1(input);
			return;
		} else {
			System.out.println("\'" + branchId + "\' is not a valid branch.");
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
	 * @param branchId - the id of the selected branch
	 */
	public static void lib3(Scanner input, int branchId) {
		System.out.println("\t1. Update the details of the Library");
		System.out.println("\t2. Add copies of a book to the Branch");
		System.out.println("\t3. Quit to previous");
		System.out.println("");
		int selection = 0; 
		try {
			selection = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			lib3(input, branchId);
			return;
		}
		switch(selection) {
			case 1:
				libUpdate(input, branchId);
				return;
			case 2:
				libAddBook(input, branchId);
				return;
			case 3:
				lib2(input);
				return;
			default:
				System.out.println("\'" + selection + "\' is not a valid selection.");
				System.out.println("Please select an option:");
				System.out.println();
				lib3(input, branchId);
				return;
		}
	}
	
	/**
	 * Provide the option to input a new branch name and branch address and update those values for the specified branchID in the database
	 * @param input - Scanner for user input
	 * @param branchId - the id of the selected branch
	 */
	public static void libUpdate(Scanner input, int branchId) {
		Branch branch;
		try {
			branch = db.getBranch(branchId);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		String branchName = branch.getBranchName();
		String branchAddress = branch.getBranchAddress();
		System.out.println("You have chosen to update the Branch with Branch Id: " + branchId + " and Branch Name: " + branchName + ".");
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
		
		try {
			db.updateBranch(branchId, newBranchName, newBranchAddress);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		lib3(input, branchId);
		return;
	}
	
	/**
	 * Prompt the user to input which book they want to add copies of and then pass that bookId to getNumCopies or call lib3 to quit to previous
	 * @param input - Scanner for user input
	 * @param branchId - the id of the selected branch
	 */
	public static void libAddBook(Scanner input, int branchId) {		
		List<Book> books;
		try {
			books = db.getBooks();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}		
		
		System.out.println("Pick the Book you want to add copies of to your branch:");
		System.out.println();
		int i;
		for(i = 0; i < books.size(); i++) {
			String authorName;
			try {
				Author auth = db.getAuthor(books.get(i).getAuthId());
				if(auth != null) {
					authorName = auth.getAuthorName();
				} else {
					authorName = "Unknown";
				}				
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
			System.out.println("\t" + (i+1) + ". " + books.get(i).getTitle() + " by " + authorName);
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int bookId = 0;
		try {
			bookId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			libAddBook(input, branchId);
			return;
		}
		if(bookId > 0 && bookId <= books.size()) {
			getNumCopies(input, branchId, books.get(bookId - 1).getBookId());	
			return;
		} else if(bookId == (books.size()+1)) {
			lib3(input, branchId);
			return;
		} else {
			System.out.println("\'" + bookId + "\' is not a valid book.");
			libAddBook(input, branchId);
			return;
		}
	}
	
	/**
	 * Prompt the user to input the new number of copies and update that value for the specified bookId and branchId in the database
	 * @param input - Scanner for user input
	 * @param branchId - the id of the selected branch
	 * @param bookId - the id of the selected book
	 */
	public static void getNumCopies(Scanner input, int branchId, int bookId) {
		Boolean newEntry = false;
		BookCopy bc;
		int numCopies = 0;
		try {
			bc = db.getBookCopy(branchId, bookId);
			if(bc != null) {
				numCopies = bc.getNoOfCopies();
			} else {
				newEntry = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}		
		System.out.println();
		System.out.println("Existing number of copies: " + numCopies);
		System.out.println();
		System.out.println("Enter new number of copies:");
		System.out.println();
		int newNumCopies = 0;
		try {
			newNumCopies = input.nextInt();
		}catch (Exception e) {
			System.out.println("That is not a valid integer.");
			getNumCopies(input, branchId, bookId);
			return;
		}
		
		try {
			if(!newEntry) {
				db.updateBookCopies(branchId, bookId, newNumCopies);
			} else {
				db.addBookCopy(branchId, bookId, newNumCopies);
			}
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		lib3(input, branchId);
		return;
	}
	
	/**
	 * Prompt the user to input their library card number to verify their identity and pass their cardNo to borr1
	 * @param input - Scanner for user input
	 */
	public static void borr(Scanner input) {
		System.out.println("Enter your Card Number:");
		int cardNo = 0;
		try {
			cardNo = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			borr(input);
		}	
		try {
			Borrower borr = db.getBorrower(cardNo);
			if(borr != null) {
				borr1(input, cardNo);
				return;
			} else {
				System.out.println("Sorry, " + cardNo + " is not a valid card number.");
				borr(input);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
	}
	
	/**
	 * Prompts the user to select whether they want to check out or return a book and call the respective function
	 * @param input - Scanner for user input
	 * @param cardNo - User's card number - their primary key in the tbl_borrower table
	 */
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
			return;
		}
		switch(selection) {
			case 1:
				borrCheckOutBranch(input, cardNo);
				return;
			case 2:
				borrReturnBranch(input, cardNo);
				return;
			case 3:
				getUserCat(input);
				return;
			default:
				System.out.println("\'" + selection + "\' is not a valid selection.");
				System.out.println("Please select an option:");
				System.out.println();
				borr1(input, cardNo);
				return;
		}
	}
	
	/**
	 * Displays the list of all branches and prompts the user to select one to check out a book from
	 * @param input - Scanner for user input
	 * @param cardNo - User's card number - their primary key in the tbl_borrower table
	 */
	public static void borrCheckOutBranch(Scanner input, int cardNo) {
		List<Branch> branches;
		try {
			branches = db.getBranches();   
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}

		System.out.println("Pick the Branch you want to check out from:");
		int i;
		for(i = 0; i < branches.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + branches.get(i).getBranchName() + ", " + branches.get(i).getBranchAddress());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		
		int branchId = 0;
		try{
			branchId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			borrCheckOutBranch(input, cardNo);
			return;
		}
		if(branchId > 0 && branchId <= branches.size()) {
			borrCheckOutBook(input, cardNo, branches.get(branchId - 1).getBranchId());
			return;
		} else if(branchId == (branches.size()+1)) {
			borr1(input, cardNo);
			return;
		} else {
			System.out.println("\'" + branchId + "\' is not a valid branch.");
			borrCheckOutBranch(input, cardNo);
			return;
		}
	}
	
	/**
	 * Displays the list of all books available at the given branch and prompts them to select one to check out
	 * @param input - Scanner for user input
	 * @param cardNo - User's card number - their primary key in the tbl_borrower table
	 * @param branchId - The branchId of the branch the user wants to check a book out from
	 */
	public static void borrCheckOutBook(Scanner input, int cardNo, int branchId) {
		List<Book> books;
		try {
			books = db.getBooks();
			for(int i = 0; i < books.size(); i++) {
				BookCopy bc = db.getBookCopy(branchId, books.get(i).getBookId());
				if(bc == null || bc.getNoOfCopies() == 0) {
					books.remove(i);
					i--;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}				
		
		System.out.println("Pick the Book you want to check out:");
		System.out.println();
		int i;
		for(i = 0; i < books.size(); i++) {
			String authorName;
			try {
				Author auth = db.getAuthor(books.get(i).getAuthId());
				if(auth != null) {
					authorName = auth.getAuthorName();
				} else {
					authorName = "Unknown";
				}				
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
			System.out.println("\t" + (i+1) + ". " + books.get(i).getTitle() + " by " + authorName);
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int bookId = 0;
		try {
			bookId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			borrCheckOutBook(input, cardNo, branchId);
			return;
		}
		if(bookId > 0 && bookId <= books.size()) {
			try {
				db.addBookLoan(branchId, books.get(bookId - 1).getBookId(), cardNo);
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
		} else if(bookId == (books.size()+1)) {
			borr1(input, cardNo);
			return;
		} else {
			System.out.println("\'" + bookId + "\' is not a valid book.");
			borrCheckOutBook(input, cardNo, branchId);
			return;
		}
		
		borrCheckOutBranch(input, cardNo);
		return;
	}

	/**
	 * Displays the list of all branches and prompts the user to select one to return a book to
	 * @param input - Scanner for user input
	 * @param cardNo - User's card number - their primary key in the tbl_borrower table
	 */
	public static void borrReturnBranch(Scanner input, int cardNo) {
		List<Branch> branches;
		try {
			branches = db.getBranches();   
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}

		System.out.println("Pick the Branch you want to return to:");
		int i;
		for(i = 0; i < branches.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + branches.get(i).getBranchName() + ", " + branches.get(i).getBranchAddress());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		
		int branchId = 0;
		try{
			branchId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			borrReturnBranch(input, cardNo);
			return;
		}
		if(branchId > 0 && branchId <= branches.size()) {
			borrReturnBook(input, cardNo, branches.get(branchId - 1).getBranchId());
			return;
		} else if(branchId == (branches.size()+1)) {
			borr1(input, cardNo);
			return;
		} else {
			System.out.println("\'" + branchId + "\' is not a valid branch.");
			borrReturnBranch(input, cardNo);
			return;
		}
	}

	/**
	 * Displays the list of all books the user has checked out from the given branch and prompts them to select one to return
	 * @param input - Scanner for user input
	 * @param cardNo - User's card number - their primary key in the tbl_borrower table
	 * @param branchId - The branchId of the branch the user wants to return a book to
	 */
	public static void borrReturnBook(Scanner input, int cardNo, int branchId) {
		List<Book> books;
		try {
			books = db.getBooks();
			for(int i = 0; i < books.size(); i++) {
				Loan bookLoan = db.getBookLoan(branchId, books.get(i).getBookId(), cardNo);
				if(bookLoan == null) {
					books.remove(i);
					i--;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}				
		
		if(books.size() == 0) {
			System.out.println();
			System.out.println("You do not appear to have any books checked out from this branch currently.");
			System.out.println();
			borrReturnBranch(input, cardNo);
			return;
		}
		
		System.out.println("Pick the Book you want to return:");
		System.out.println();
		int i;
		for(i = 0; i < books.size(); i++) {
			String authorName;
			try {
				Author auth = db.getAuthor(books.get(i).getAuthId());
				if(auth != null) {
					authorName = auth.getAuthorName();
				} else {
					authorName = "Unknown";
				}				
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
			System.out.println("\t" + (i+1) + ". " + books.get(i).getTitle() + " by " + authorName);
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		
		int bookId = 0;
		try {
			bookId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			borrReturnBook(input, cardNo, branchId);
			return;
		}
		if(bookId > 0 && bookId <= books.size()) {
			try {
				db.deleteBookLoan(branchId, books.get(bookId - 1).getBookId(), cardNo);
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
		} else if(bookId == (books.size()+1)) {
			borr1(input, cardNo);
			return;
		} else {
			System.out.println("\'" + bookId + "\' is not a valid book.");
			borrReturnBook(input, cardNo, branchId);
			return;
		}
		borrReturnBranch(input, cardNo);
		return;
	}

	/**
	 * Prompts the user to enter the administrative password in order to proceed
	 * @param input - Scanner for user input
	 */
	public static void admin(Scanner input) {
		final String PASSWORD = "Java_Basics_Pa$$word";  //Administrative password
		System.out.println("Please enter the admin password (enter \'quit\' to quit):");
		String pass = input.nextLine();
		if(PASSWORD.equals(pass)) {
			admin1(input);
			return;
		} else if("QUIT".equals(pass.toUpperCase())) {
			getUserCat(input);
			return;
		} else {
			System.out.println("Sorry, that is incorrect. Please try again.");
			admin(input);
			return;
		}
	}

	/**
	 * Prompts the admin to select what action they want to do (Add, Update, Delete, or Override a Due Date)
	 * @param input - Scanner for user input
	 */
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
			return;
		}
		switch(selection) {
			case 1:
				adminAdd(input);
				return;
			case 2:
				adminUpdate(input);
				return;
			case 3:
				adminDelete(input);
				return;
			case 4:
				adminOverrideDueDate(input);
				return;
			case 5:
				getUserCat(input);
				return;
			default:
				System.out.println("\'" + selection + "\' is not a valid selection.");
				admin1(input);
				return;
		}
	}

	/**
	 * Prompts the admin to select which table to add to
	 * @param input - Scanner for user input
	 */
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
			return;
		}
		switch(table) {
			case 1:
				input.nextLine();
				adminAddBookTitle(input);
				return;
			case 2:
				input.nextLine();
				adminAddAuthor(input);
				return;
			case 3:
				input.nextLine();
				adminAddPublisher(input);
				return;
			case 4:
				input.nextLine();
				adminAddBranch(input);
				return;
			case 5:
				input.nextLine();
				adminAddBorrower(input);
				return;
			case 6:
				admin1(input);
				return;
			default:
				System.out.println(table + " is not a valid table choice.");
				adminAdd(input);
				return;
		}		
	}

	/**
	 * Prompts the admin to select which table to update and entry from
	 * @param input - Scanner for user input
	 */
	public static void adminUpdate(Scanner input) {
		System.out.println("Which table would you like to update:");
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
			return;
		}
		switch(table) {
			case 1:
				input.nextLine();
				adminUpdateBook(input);
				return;
			case 2:
				input.nextLine();
				adminUpdateAuthor(input);
				return;
			case 3:
				input.nextLine();
				adminUpdatePublisher(input);
				return;
			case 4:
				input.nextLine();
				adminUpdateBranch(input);
				return;
			case 5:
				input.nextLine();
				adminUpdateBorrower(input);
				return;
			case 6:
				admin1(input);
				return;
			default:
				System.out.println(table + " is not a valid table choice.");
				adminUpdate(input);
				return;
		}		
	}

	/**
	 * Prompts the admin to select which table to delete from
	 * @param input - Scanner for user input
	 */
	public static void adminDelete(Scanner input) {
		System.out.println("Which table would you like to delete from:");
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
			return;
		}
		switch(table) {
			case 1:
				adminDeleteBook(input);
				return;
			case 2:
				adminDeleteAuthor(input);
				return;
			case 3:
				adminDeletePublisher(input);
				return;
			case 4:
				adminDeleteBranch(input);
				return;
			case 5:
				adminDeleteBorrower(input);
				return;
			case 6:
				admin1(input);
				return;
			default:
				System.out.println(table + " is not a valid table choice.");
				adminDelete(input);
				return;
		}		
	}

	/**
	 * Gets the user input for the title of the new book entry
	 * @param input - Scanner for user input
	 */
	public static void adminAddBookTitle(Scanner input) {
		System.out.println("What is the title of the book (enter \'quit\' to quit):");
		String title = input.nextLine();
		if("QUIT".equals(title.toUpperCase())) {
			adminAdd(input);
			return;
		} else {
			adminAddBookAuthor(input, title);
			return;
		}
	}

	/**
	 * Gets the user input for the name of the new book's author and verifies that they exist in the database
	 * @param input - Scanner for user input
	 * @param title - The title of the book to be added
	 */
	public static void adminAddBookAuthor(Scanner input, String title) {
		System.out.println("What is the name of the book's author (enter \'quit\' to quit):");
		String author = input.nextLine();
		if("QUIT".equals(author.toUpperCase())) {
			adminAdd(input);
			return;
		} else {
			try {
				Author auth = db.getAuthor(author);
				if(auth != null) {
					int authorId = auth.getAuthorId();
					adminAddBookPublisher(input, title, authorId); 
					return;
				} else {
					System.out.println("Could not find an author with the name: " + author);
					System.out.println("Please input a different name or quit and add them to the author table first.");
					adminAddBookAuthor(input, title);
					return;
				}
			} catch(Exception e){
				e.printStackTrace();
				db.close();
				return;
			}
		}
	}

	/**
	 * Gets the user input for the name of the new book's publisher and verifies that they exist in the database
	 * @param input - Scanner for user input
	 * @param title - The title of the book to be added
	 * @param authorId - The authorId of the author of the book
	 */
	public static void adminAddBookPublisher(Scanner input, String title, int authorId) {
		System.out.println("What is the name of the book's publisher (enter \'quit\' to quit):");
		String publisher = input.nextLine();
		if("QUIT".equals(publisher.toUpperCase())) {
			adminAdd(input);
			return;
		} else {
			try {
				Publisher pub = db.getPublisher(publisher);
				if(pub != null) {
					db.addBook(title, authorId, pub.getPublisherId());
				} else {
					System.out.println("Could not find an publisher with the name: " + publisher);
					System.out.println("Please input a different name or quit and add them to the publisher table first.");
					adminAddBookAuthor(input, title);
					return;
				}
			} catch(Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
		}
		adminAdd(input);
		return;
	}

	/**
	 * Gets the user input for the values of the new author entry
	 * @param input - Scanner for user input
	 */
	public static void adminAddAuthor(Scanner input) {
		System.out.println("What is the name of the author (enter \'quit\' to quit):");
		String authorName = input.nextLine();
		
		try {
			db.addAuthor(authorName);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		System.out.println("Would you like to add another author (y):");
		String response = input.nextLine();
		if("Y".equals(response.toUpperCase()) || "YES".equals(response.toUpperCase())) {
			adminAddAuthor(input);
			return;
		} else {
			adminAdd(input);
			return;
		}
	}

	/**
	 * Gets the user input for the values of the new publisher entry
	 * @param input - Scanner for user input
	 */
	public static void adminAddPublisher(Scanner input) {
		System.out.println("What is the name of the publisher (enter \'quit\' to quit):");
		String publisherName = input.nextLine();
		System.out.println("What is the address of the publisher (enter \'quit\' to quit):");
		String publisherAddress = input.nextLine();
		System.out.println("What is the phone number of the publisher (enter \'quit\' to quit):");
		String publisherPhoneNumber = input.nextLine();
		
		try {
			db.addPublisher(publisherName, publisherAddress, publisherPhoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		System.out.println("Would you like to add another publisher (y):");
		String response = input.nextLine();
		if("Y".equals(response.toUpperCase()) || "YES".equals(response.toUpperCase())) {
			adminAddPublisher(input);
			return;
		} else {
			adminAdd(input);
			return;
		}
	}

	/**
	 * Gets the user input for the values of the new branch entry
	 * @param input - Scanner for user input
	 */
	public static void adminAddBranch(Scanner input) {
		System.out.println("What is the name of the branch (enter \'quit\' to quit):");
		String branchName = input.nextLine();
		System.out.println("What is the address of the branch (enter \'quit\' to quit):");
		String branchAddress = input.nextLine();
		
		try {
			db.addBranch(branchName, branchAddress);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		System.out.println("Would you like to add another branch (y):");
		String response = input.nextLine();
		if("Y".equals(response.toUpperCase()) || "YES".equals(response.toUpperCase())) {
			adminAddBranch(input);
			return;
		} else {
			adminAdd(input);
			return;
		}
	}

	/**
	 * Gets the user input for the values of the new borrower entry
	 * @param input - Scanner for user input
	 */
	public static void adminAddBorrower(Scanner input) {
		System.out.println("What is the name of the borrower (enter \'quit\' to quit):");
		String borrowerName = input.nextLine();
		System.out.println("What is the address of the borrower (enter \'quit\' to quit):");
		String borrowerAddress = input.nextLine();
		System.out.println("What is the phone number of the borrower (enter \'quit\' to quit):");
		String borrowerPhoneNumber = input.nextLine();
		
		try {
			int cardNo = db.addBorrower(borrowerName, borrowerAddress, borrowerPhoneNumber);
			System.out.println("The card number for this new borrower is " + cardNo);
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		System.out.println("Would you like to add another borrower (y):");
		String response = input.nextLine();
		if("Y".equals(response.toUpperCase()) || "YES".equals(response.toUpperCase())) {
			adminAddBorrower(input);
			return;
		} else {
			adminAdd(input);
			return;
		}
	}

	/**
	 * Displays all entries from the tbl_book table and the admin picks one to update
	 * @param input - Scanner for user input
	 */
	public static void adminUpdateBook(Scanner input) {
		List<Book> books;
		try {
			books = db.getBooks();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}		
		
		System.out.println("Pick the Book you want to update:");
		System.out.println();
		int i;
		for(i = 0; i < books.size(); i++) {
			String authorName;
			try {
				Author auth = db.getAuthor(books.get(i).getAuthId());
				if(auth != null) {
					authorName = auth.getAuthorName();
				} else {
					authorName = "Unknown";
				}				
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
			System.out.println("\t" + (i+1) + ". " + books.get(i).getTitle() + " by " + authorName);
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int bookId = 0;
		try {
			bookId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminUpdateBook(input);
			return;
		}
		if(bookId > 0 && bookId <= books.size()) {
			input.nextLine();
			adminUpdateBookInput(input, books.get(bookId - 1).getBookId());
			return;
		} else if(bookId == (books.size()+1)) {
			adminUpdate(input);
			return;
		} else {
			System.out.println("\'" + bookId + "\' is not a valid book.");
			adminUpdateBook(input);
			return;
		}
	}

	/**
	 * Gets the user input for the new values of the updated book entry
	 * @param input - Scanner for user input
	 * @param bookId - The bookId for the book entry that the user wants to update
	 */
	public static void adminUpdateBookInput(Scanner input, int bookId) {
		Book b;
		String bookName;
		String bookAuthor;
		String bookPublisher;
		try {
			b = db.getBook(bookId);
			bookName = b.getTitle();
			Author auth = db.getAuthor(b.getAuthId());
			bookAuthor = auth.getAuthorName();
			Publisher pub = db.getPublisher(b.getPubId());
			bookPublisher = pub.getPublisherName();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}		
		System.out.println("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".");
		System.out.println();
		System.out.println("Please enter a new title or enter N/A for no change:");
		String newBookName = input.nextLine();
		System.out.println("Please enter a new author or enter N/A for no change:");
		String newBookAuthor = input.nextLine();
		System.out.println("Please enter a new publisher or enter N/A for no change:");
		String newBookPublisher = input.nextLine();
		try {
			int authId = -1;
			int pubId = -1;
			if("N/A".equals(newBookName.toUpperCase())) {
				newBookName = bookName;
			}
			if("N/A".equals(newBookAuthor.toUpperCase())) {
				newBookAuthor = bookAuthor;
			} else {
				Author auth = db.getAuthor(newBookAuthor);
				if(auth == null) {
					System.out.println("Could not find an author with the name " + newBookAuthor);
					System.out.println("Please input a different name or quit and add them to the author table first.");
					adminUpdateBookInput(input, bookId);
					return;
				} else {
					authId = auth.getAuthorId();
				}
			}
			if("N/A".equals(newBookPublisher.toUpperCase())) {
				newBookPublisher = bookPublisher;
			} else {
				Publisher pub = db.getPublisher(newBookPublisher);
				if(pub == null) {
					System.out.println("Could not find a publisher with the name " + newBookPublisher);
					System.out.println("Please input a different name or quit and add them to the publisher table first.");
					adminUpdateBookInput(input, bookId);
					return;
				} else {
					pubId = pub.getPublisherId();
				}
			}
			
			db.updateBook(bookId, newBookName, authId, pubId);
		} catch(Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		
		adminUpdateBook(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_author table and the admin picks one to update
	 * @param input - Scanner for user input
	 */
	public static void adminUpdateAuthor(Scanner input) {
		List<Author> authors;
		try {
			authors = db.getAuthors();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}	
		
		System.out.println("Pick the Author you want to update:");
		System.out.println();
		int i;
		for(i = 0; i < authors.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + authors.get(i).getAuthorName());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int authorId = 0;
		try {
			authorId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminUpdateAuthor(input);
			return;
		}
		if(authorId > 0 && authorId <= authors.size()) {
			input.nextLine();
			adminUpdateAuthorInput(input, authors.get(authorId-1).getAuthorId());
			return;
		} else if(authorId == (authors.size()+1)) {
			adminUpdate(input);
			return;
		} else {
			System.out.println("\'" + authorId + "\' is not a valid author.");
			adminUpdateAuthor(input);
			return;
		}
	}

	/**
	 * Gets the user input for the new values of the updated author entry
	 * @param input - Scanner for user input
	 * @param authorId - The authorId for the author entry that the user wants to update
	 */
	public static void adminUpdateAuthorInput(Scanner input, int authorId) {
		String authorName;
		try {
			Author auth = db.getAuthor(authorId);
			authorName = auth.getAuthorName();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		System.out.println("You have chosen to update the Author: " + authorName + ".");
		System.out.println();
		System.out.println("Please enter a new name or enter N/A for no change:");
		String newAuthorName = input.nextLine();
		if("N/A".equals(newAuthorName.toUpperCase())) {
			newAuthorName = authorName;
		}
		
		try {
			db.updateAuthor(authorId, newAuthorName);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		adminUpdateAuthor(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_publisher table and the admin picks one to update
	 * @param input - Scanner for user input
	 */
	public static void adminUpdatePublisher(Scanner input) {
		List<Publisher> publishers;
		try {
			publishers = db.getPublishers();
		} catch (Exception e){
			e.printStackTrace();
			db.close();
			return;
		}		
		
		System.out.println("Pick the Publisher you want to update:");
		System.out.println();
		int i;
		for(i = 0; i < publishers.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + publishers.get(i).getPublisherName() + ", " + publishers.get(i).getPublisherAddress());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int publisherId = 0;
		try {
			publisherId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminUpdatePublisher(input);
			return;
		}
		if(publisherId > 0 && publisherId <= publishers.size()) {
			input.nextLine();
			adminUpdatePublisherInput(input, publishers.get(publisherId - 1).getPublisherId());
			return;
		} else if(publisherId == (publishers.size()+1)) {
			adminUpdate(input);
			return;
		} else {
			System.out.println("\'" + publisherId + "\' is not a valid publisher.");
			adminUpdatePublisher(input);
			return;
		}
	}

	/**
	 * Gets the user input for the new values of the updated publisher entry
	 * @param input - Scanner for user input
	 * @param publisherId - The publisherId for the publisher entry that the user wants to update
	 */
	public static void adminUpdatePublisherInput(Scanner input, int publisherId) {
		String publisherName;
		String publisherAddress;
		String publisherPhone;
		try {
			Publisher pub = db.getPublisher(publisherId);
			publisherName = pub.getPublisherName();
			publisherAddress = pub.getPublisherAddress();
			publisherPhone = pub.getPublisherPhone();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
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
		
		try {
			db.updatePublisher(publisherId, newPublisherName, newPublisherAddress, newPublisherPhone);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		adminUpdatePublisher(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_branch table and the admin picks one to update
	 * @param input - Scanner for user input
	 */
	public static void adminUpdateBranch(Scanner input) {
		List<Branch> branches;
		try {
			branches = db.getBranches();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		System.out.println("Pick the Branch you want to update:");
		System.out.println();
		int i;
		for(i = 0; i < branches.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + branches.get(i).getBranchName() + ", " + branches.get(i).getBranchAddress());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int branchId = 0;
		try {
			branchId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminUpdateBranch(input);
			return;
		}
		if(branchId > 0 && branchId <= branches.size()) {
			input.nextLine();
			adminUpdateBranchInput(input, branches.get(branchId-1).getBranchId());
			return;
		} else if(branchId == (branches.size()+1)) {
			adminUpdate(input);
			return;
		} else {
			System.out.println("\'" + branchId + "\' is not a valid branch.");
			adminUpdateBranch(input);
			return;
		}
	}

	/**
	 * Gets the user input for the new values of the updated branch entry
	 * @param input - Scanner for user input
	 * @param branchId - The branchId for the branch entry that the user wants to update
	 */
	public static void adminUpdateBranchInput(Scanner input, int branchId) {
		String branchName;
		String branchAddress;
		try {
			Branch b = db.getBranch(branchId);
			branchName = b.getBranchName();
			branchAddress = b.getBranchAddress();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
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
		
		try {
			db.updateBranch(branchId, newBranchName, newBranchAddress);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		adminUpdateBranch(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_borrower table and the admin picks one to update
	 * @param input - Scanner for user input
	 */
	public static void adminUpdateBorrower(Scanner input) {
		List<Borrower> borrowers;
		try {
			borrowers = db.getBorrowers();
		}catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		System.out.println("Pick the Borrower you want to update:");
		System.out.println();
		int i;
		for(i = 0; i < borrowers.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + borrowers.get(i).getName() + ", Card #: " + borrowers.get(i).getCardNo());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int borrowerId = 0;
		try {
			borrowerId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminUpdateBorrower(input);
			return;
		}
		if(borrowerId > 0 && borrowerId <= borrowers.size()) {
			input.nextLine();
			adminUpdateBorrowerInput(input, borrowers.get(borrowerId-1).getCardNo());
			return;
		} else if(borrowerId == (borrowers.size()+1)) {
			adminUpdate(input);
			return;
		} else {
			System.out.println("\'" + borrowerId + "\' is not a valid borrower.");
			adminUpdateBorrower(input);
			return;
		}
	}

	/**
	 * Gets the user input for the new values of the updated borrower entry
	 * @param input - Scanner for user input
	 * @param cardNo - The cardNo for the borrower entry that the user wants to update
	 */
	public static void adminUpdateBorrowerInput(Scanner input, int cardNo) {
		String borrowerName;
		String borrowerAddress;
		String borrowerPhone;
		try {
			Borrower borr = db.getBorrower(cardNo);
			borrowerName = borr.getName();
			borrowerAddress = borr.getAddress();
			borrowerPhone = borr.getPhone();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
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
		
		try {
			db.updateBorrower(cardNo, newBorrowerName, newBorrowerAddress, newBorrowerPhone);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		adminUpdateBorrower(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_book table and the admin picks one to delete
	 * @param input - Scanner for user input
	 */
	public static void adminDeleteBook(Scanner input) {
		List<Book> books;
		try {
			books = db.getBooks();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}		
		
		System.out.println("Pick the Book you want to delete:");
		System.out.println();
		int i;
		for(i = 0; i < books.size(); i++) {
			String authorName;
			try {
				Author auth = db.getAuthor(books.get(i).getAuthId());
				if(auth != null) {
					authorName = auth.getAuthorName();
				} else {
					authorName = "Unknown";
				}				
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
			System.out.println("\t" + (i+1) + ". " + books.get(i).getTitle() + " by " + authorName);
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int bookId = 0;
		try {
			bookId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteBook(input);
			return;
		}
		if(bookId > 0 && bookId <= books.size()) {
			try {
				db.deleteBook(books.get(bookId-1).getBookId());
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
		} else if(bookId == (books.size()+1)) {
			adminDelete(input);
			return;
		} else {
			System.out.println("\'" + bookId + "\' is not a valid book.");
			adminDeleteBook(input);
			return;
		}
		adminDeleteBook(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_author table and the admin picks one to delete
	 * @param input - Scanner for user input
	 */
	public static void adminDeleteAuthor(Scanner input) {
		List<Author> authors;
		try {
			authors = db.getAuthors();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}		
		
		System.out.println("Pick the Author you want to delete:");
		System.out.println();
		int i;
		for(i = 0; i < authors.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + authors.get(i).getAuthorName());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int authorId = 0;
		try {
			authorId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteAuthor(input);
			return;
		}
		if(authorId > 0 && authorId <= authors.size()) {
			try {
				db.deleteAuthor(authors.get(authorId-1).getAuthorId());
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
		} else if(authorId == (authors.size()+1)) {
			adminDelete(input);
			return;
		} else {
			System.out.println("\'" + authorId + "\' is not a valid author.");
			adminDeleteAuthor(input);
			return;
		}
		adminDeleteAuthor(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_publisher table and the admin picks one to delete
	 * @param input - Scanner for user input
	 */
	public static void adminDeletePublisher(Scanner input) {
		List<Publisher> publishers;
		try {
			publishers = db.getPublishers();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}		
		
		System.out.println("Pick the Publisher you want to delete:");
		System.out.println();
		int i;
		for(i = 0; i < publishers.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + publishers.get(i).getPublisherName() + ", " + publishers.get(i).getPublisherAddress());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int publisherId = 0;
		try {
			publisherId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeletePublisher(input);
			return;
		}
		if(publisherId > 0 && publisherId <= publishers.size()) {
			try {
				db.deletePublisher(publishers.get(publisherId-1).getPublisherId());
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
		} else if(publisherId == (publishers.size()+1)) {
			adminDelete(input);
			return;
		} else {
			System.out.println("\'" + publisherId + "\' is not a valid publisher.");
			adminDeletePublisher(input);
			return;
		}
		adminDeletePublisher(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_library_branch table and the admin picks one to delete
	 * @param input - Scanner for user input
	 */
	public static void adminDeleteBranch(Scanner input) {
		List<Branch> branches;
		try {
			branches = db.getBranches();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		System.out.println("Pick the Branch you want to delete:");
		System.out.println();
		int i;
		for(i = 0; i < branches.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + branches.get(i).getBranchName() + ", " + branches.get(i).getBranchAddress());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int branchId = 0;
		try {
			branchId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteBranch(input);
			return;
		}
		if(branchId > 0 && branchId <= branches.size()) {
			try {
				db.deleteBranch(branches.get(branchId-1).getBranchId());
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
		} else if(branchId == (branches.size()+1)) {
			adminDelete(input);
			return;
		} else {
			System.out.println("\'" + branchId + "\' is not a valid branch.");
			adminDeleteBranch(input);
			return;
		}
		adminDeleteBranch(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_borrower table and the admin picks one to delete
	 * @param input - Scanner for user input
	 */
	public static void adminDeleteBorrower(Scanner input) {
		List<Borrower> borrowers;
		try {
			borrowers = db.getBorrowers();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}	
		
		System.out.println("Pick the Borrower you want to delete:");
		System.out.println();
		int i;
		for(i = 0; i < borrowers.size(); i++) {
			System.out.println("\t" + (i+1) + ". " + borrowers.get(i).getName() + ", Card #: " + borrowers.get(i).getCardNo());
		}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int borrowerId = 0;
		try {
			borrowerId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminDeleteBorrower(input);
			return;
		}
		if(borrowerId > 0 && borrowerId <= borrowers.size()) {
			try {
				db.deleteBorrower(borrowers.get(borrowerId-1).getCardNo());
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
		} else if(borrowerId == (borrowers.size()+1)) {
			adminDelete(input);
			return;
		} else {
			System.out.println("\'" + borrowerId + "\' is not a valid borrower.");
			adminDeleteBorrower(input);
			return;
		}
		adminDeleteBorrower(input);
		return;
	}

	/**
	 * Displays all entries from the tbl_book_loans table and the admin picks one to override the due date of
	 * @param input - Scanner for user input
	 */
	public static void adminOverrideDueDate(Scanner input) {
		List<Loan> loans;
		try {
			loans = db.getLoans();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		System.out.println("Which rental would you like to override the due date for:");
		System.out.println();
		int i;
		for(i = 0; i < loans.size(); i++) {
			try {
				Book bk = db.getBook(loans.get(i).getBookId());
				Branch bch = db.getBranch(loans.get(i).getBranchId());
				Borrower borr = db.getBorrower(loans.get(i).getCardNo());
				System.out.println("\t" + (i+1) + ". " + bk.getTitle() + "; From: " + bch.getBranchName() + "; By: " + borr.getName() + "; (" + loans.get(i).getDateOut() + " --- " + loans.get(i).getDueDate() + ")");                                     
			} catch (Exception e) {
				e.printStackTrace();
				db.close();
				return;
			}
			}
		System.out.println("\t" + (i+1) + ". Quit to previous");
		System.out.println();
		int loanId = 0;
		try {
			loanId = input.nextInt();
		} catch (Exception e) {
			System.out.println("That is not a valid integer.");
			adminOverrideDueDate(input);
			return;
		}
		if(loanId > 0 && loanId <= loans.size()) {
			Loan l = loans.get(loanId-1);
			adminOverrideDueDateInput(input, l.getBookId(), l.getBranchId(), l.getCardNo());
			return;
		} else if(loanId == (loans.size()+1)) {
			admin1(input);
			return;
		} else {
			System.out.println("\'" + loanId + "\' is not a valid book rental.");
			adminOverrideDueDate(input);
			return;
		}
	}

	/**
	 * Updates the chosen book loan so it no longer has a due date (Sets it to 12/31/9999)
	 * @param input - Scanner for user input
	 * @param bookId - The bookId of the book that was checked out
	 * @param branchId - The branchId of the branch that was checked out from
	 * @param cardNo - The cardNo of the borrower who checked out the book
	 */
	public static void adminOverrideDueDateInput(Scanner input, int bookId, int branchId, int cardNo) {
		String bookName;
		String branchName;
		String borrowerName;
		String checkoutDate;
		String dueDate;
		try {
			Loan l = db.getBookLoan(branchId, bookId, cardNo);
			Book bk = db.getBook(bookId);
			Branch bch = db.getBranch(branchId);
			Borrower borr = db.getBorrower(cardNo);			
			bookName = bk.getTitle();
			branchName = bch.getBranchName();
			borrowerName = borr.getName();
			checkoutDate = l.getDateOut();
			dueDate = l.getDueDate();
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		System.out.println("You have chosen to override the due date of " + bookName + ", loaned from " + branchName + " by " + borrowerName + ".");
		System.out.println("This book was checked out " + checkoutDate + " and was due " + dueDate + ".");
		System.out.println();
		
		try {
			db.overrideDueDate(bookId, branchId, cardNo);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return;
		}
		
		adminOverrideDueDate(input);
		return;
	}
}  



