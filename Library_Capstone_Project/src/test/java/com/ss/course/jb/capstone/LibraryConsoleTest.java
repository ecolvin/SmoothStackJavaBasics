/**
 * 
 */
package com.ss.course.jb.capstone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Eric Colvin
 *
 */
public class LibraryConsoleTest {

	private final ByteArrayOutputStream systemOutput = new ByteArrayOutputStream();
	private final PrintStream originalOutput = System.out;
		
	@Before
	public void redirectOutput() {
		System.setOut(new PrintStream(systemOutput));
		LibraryConsole.initDatabase();
	}
	
	@After
	public void revertOutput() {
		LibraryConsole.closeDatabase();
		System.setOut(originalOutput);		
	}
	
	/**
	 * Test that main prints the correct output and calls the correct method
	 */
	@Test
	public void startConsoleTest() {
		ByteArrayInputStream input = new ByteArrayInputStream("4".getBytes());
		InputStream originalInput = System.in;
		System.setIn(input);
		LibraryConsole.startConsole();
		System.setIn(originalInput);		
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("Welcome to the GCIT Library Management System. Which category of user are you:", lines[0]);
		assertEquals("\t1. Librarian", lines[2]);
		assertEquals("\t2. Administrator", lines[3]);
		assertEquals("\t3. Borrower", lines[4]);
		assertEquals("\t4. Quit", lines[5]);
	}
	
	/**
	 * Test that getUserCat prints the correct output when the user inputs 1
	 */
	@Test
	public void getUserCatTest1() {
		LibraryConsole.getUserCat(new Scanner("1\n2\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("Which category of user are you:", lines[0]);
		assertEquals("\t1. Librarian", lines[2]);
		assertEquals("\t2. Administrator", lines[3]);
		assertEquals("\t3. Borrower", lines[4]);
		assertEquals("\t4. Quit", lines[5]);
		assertEquals("\t1. Select Your Branch", lines[7]);
		assertEquals("\t2. Quit to previous", lines[8]);
	}

	/**
	 * Test that getUserCat prints the correct output when the user inputs 2
	 */
	@Test
	public void getUserCatTest2() {
		LibraryConsole.getUserCat(new Scanner("2\nquit\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("Which category of user are you:", lines[0]);
		assertEquals("\t1. Librarian", lines[2]);
		assertEquals("\t2. Administrator", lines[3]);
		assertEquals("\t3. Borrower", lines[4]);
		assertEquals("\t4. Quit", lines[5]);
		assertEquals("Please enter the admin password (enter \'quit\' to quit):", lines[7]);
	}
	
	/**
	 * Test that getUserCat prints the correct output when the user inputs 3
	 */
	@Test
	public void getUserCatTest3() {
		LibraryConsole.getUserCat(new Scanner("3\n1\n3\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("Which category of user are you:", lines[0]);
		assertEquals("\t1. Librarian", lines[2]);
		assertEquals("\t2. Administrator", lines[3]);
		assertEquals("\t3. Borrower", lines[4]);
		assertEquals("\t4. Quit", lines[5]);
		assertEquals("Enter your Card Number:", lines[7]);		
	}
	
	/**
	 * Test that getUserCat prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void getUserCatTestNotInt() {
		LibraryConsole.getUserCat(new Scanner("Not an Int\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("Which category of user are you:", lines[0]);
		assertEquals("\t1. Librarian", lines[2]);
		assertEquals("\t2. Administrator", lines[3]);
		assertEquals("\t3. Borrower", lines[4]);
		assertEquals("\t4. Quit", lines[5]);
		assertEquals("That is not a valid integer.", lines[7]);
		assertEquals("Which category of user are you:", lines[8]);
	}

	/**
	 * Test that getUserCat prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void getUserCatTestOther() {
		LibraryConsole.getUserCat(new Scanner("5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("Which category of user are you:", lines[0]);
		assertEquals("\t1. Librarian", lines[2]);
		assertEquals("\t2. Administrator", lines[3]);
		assertEquals("\t3. Borrower", lines[4]);
		assertEquals("\t4. Quit", lines[5]);
		assertEquals("\'5\' is not a valid user category.", lines[7]);
		assertEquals("Which category of user are you:", lines[8]);		
	}

	/**
	 * Test that lib1 prints the correct output when the user inputs 1
	 */
	@Test
	public void lib1Test1() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		LibraryConsole.lib1(new Scanner("1\n" + (numBranches + 1) + "\n2\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("\t1. Select Your Branch", lines[0]);
		assertEquals("\t2. Quit to previous", lines[1]);
		assertEquals("\t" + (numBranches + 1) + ". Quit to previous", lines[numBranches + 3]);
		
		db.close();
	}

	/**
	 * Test that lib1 prints the correct output when the user inputs 2
	 */
	@Test
	public void lib1Test2() {
		LibraryConsole.lib1(new Scanner("2\n4"));
		String lines[] = systemOutput.toString().split("\r\n");

		assertEquals("\t1. Select Your Branch", lines[0]);
		assertEquals("\t2. Quit to previous", lines[1]);
		assertEquals("Which category of user are you:", lines[3]);
		assertEquals("\t1. Librarian", lines[5]);
		assertEquals("\t2. Administrator", lines[6]);
		assertEquals("\t3. Borrower", lines[7]);
		assertEquals("\t4. Quit", lines[8]);
	}

	/**
	 * Test that lib1 prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void lib1TestNotInt() {
		LibraryConsole.lib1(new Scanner("Not an Integer\n2\n4"));
		String lines[] = systemOutput.toString().split("\r\n");

		assertEquals("\t1. Select Your Branch", lines[0]);
		assertEquals("\t2. Quit to previous", lines[1]);
		assertEquals("That is not a valid integer.", lines[3]);
		assertEquals("\t1. Select Your Branch", lines[4]);
		assertEquals("\t2. Quit to previous", lines[5]);		
	}

	/**
	 * Test that lib1 prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void lib1TestOther() {
		LibraryConsole.lib1(new Scanner("3\n2\n4"));
		String lines[] = systemOutput.toString().split("\r\n");

		assertEquals("\t1. Select Your Branch", lines[0]);
		assertEquals("\t2. Quit to previous", lines[1]);
		assertEquals("\'3\' is not a valid selection.", lines[3]);
		assertEquals("Please select an option:", lines[4]);
		assertEquals("\t1. Select Your Branch", lines[6]);
		assertEquals("\t2. Quit to previous", lines[7]);
	}

	/**
	 * Test that lib2 prints the correct output when a branch is selected
	 */
	@Test
	public void lib2TestBranch() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches != 0) {  //Otherwise no branch to select so selecting a branch cannot be tested
			LibraryConsole.lib2(new Scanner("1\n3\n" + (numBranches+1) + "\n2\n4"));
			String lines[] = systemOutput.toString().split("\r\n");
			assertEquals("\t" + (numBranches + 1) + ". Quit to previous", lines[numBranches]);
			assertEquals("\t1. Update the details of the Library", lines[numBranches + 2]);
			assertEquals("\t2. Add copies of a book to the Branch", lines[numBranches + 3]);
			assertEquals("\t3. Quit to previous", lines[numBranches + 4]);
		}
		
		db.close();
	}

	/**
	 * Test that lib2 prints the correct output when the user decides to quit to previous
	 */
	@Test
	public void lib2TestQuit() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.lib2(new Scanner((numBranches+1) + "\n2\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		

		assertEquals("\t" + (numBranches + 1) + ". Quit to previous", lines[numBranches]);
		assertEquals("\t1. Select Your Branch", lines[numBranches + 2]);
		assertEquals("\t2. Quit to previous", lines[numBranches + 3]);
		
		db.close();
	}
	
	/**
	 * Test that lib2 prints the correct output when the user inputs a non-integer 
	 */
	@Test
	public void lib2TestNotInt() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.lib2(new Scanner("Not an Integer\n" + (numBranches+1) + "\n2\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		

		assertEquals("\t" + (numBranches + 1) + ". Quit to previous", lines[numBranches]);
		assertEquals("That is not a valid integer.", lines[numBranches + 2]);
		assertEquals("\t" + (numBranches + 1) + ". Quit to previous", lines[numBranches + 3 + numBranches]);
		
		db.close();
	}

	/**
	 * Test that lib2 prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void lib2TestOther() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.lib2(new Scanner((numBranches+2) + "\n" + (numBranches+1) + "\n2\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		

		assertEquals("\t" + (numBranches + 1) + ". Quit to previous", lines[numBranches]);
		assertEquals("\'" + (numBranches + 2) + "\' is not a valid branch.", lines[numBranches + 2]);
		assertEquals("Please select your branch:", lines[numBranches + 3]);
		assertEquals("\t" + (numBranches + 1) + ". Quit to previous", lines[numBranches + 5 + numBranches]);
		
		db.close();
	}

	/**
	 * Test that lib3 prints the correct output when the user inputs 1
	 */
	@Test
	public void lib3Test1() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		String branchName = "";
		try {
			db.connectToDB();
			List<Branch> branches = db.getBranches();
			numBranches = branches.size();
			if(numBranches != 0) {
				branchId = branches.get(0).getBranchId();
				branchName = branches.get(0).getBranchName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches != 0)  {   //Must have a valid branch to work
			LibraryConsole.lib3(new Scanner("1\nN/A\nN/A\n3\n" + (numBranches+1) + "\n2\n4"), branchId);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("\t1. Update the details of the Library", lines[0]);
			assertEquals("\t2. Add copies of a book to the Branch", lines[1]);
			assertEquals("\t3. Quit to previous", lines[2]);
			assertEquals("You have chosen to update the Branch with Branch Id: " + branchId + " and Branch Name: " + branchName + ".", lines[4]);
			assertEquals("Please enter a new branch name or enter N/A for no change:", lines[6]);
			assertEquals("Please enter a new branch address or enter N/A for no change:", lines[7]);
		}
		
		db.close();
	}
	
	/**
	 * Test that lib3 prints the correct output when the user inputs 2
	 */
	@Test
	public void lib3Test2() {
		Database db = new Database();
		int numBranches = 0;
		int numBooks = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
			numBooks = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.lib3(new Scanner("2\n" + (numBooks+1) + "\n3\n" + (numBranches+1) + "\n2\n4"), -1); //branchId not used in this test
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("\t1. Update the details of the Library", lines[0]);
		assertEquals("\t2. Add copies of a book to the Branch", lines[1]);
		assertEquals("\t3. Quit to previous", lines[2]);
		assertEquals("Pick the Book you want to add copies of to your branch:", lines[4]);
		assertEquals("\t" + (numBooks + 1) + ". Quit to previous", lines[numBooks + 6]);
		
		db.close();
	}
	
	/**
	 * Test that lib3 prints the correct output when the user inputs 3
	 */
	@Test
	public void lib3Test3() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.lib3(new Scanner("3\n" + (numBranches+1) + "\n2\n4"), -1); //branchId not used in this test
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("\t1. Update the details of the Library", lines[0]);
		assertEquals("\t2. Add copies of a book to the Branch", lines[1]);
		assertEquals("\t3. Quit to previous", lines[2]);
		assertEquals("\t" + (numBranches + 1) + ". Quit to previous", lines[numBranches + 4]);
		
		db.close();
	}

	/**
	 * Test that lib3 prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void lib3TestNotInt() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.lib3(new Scanner("Not an Integer\n3\n" + (numBranches+1) + "\n2\n4"), -1); //branchId not used in this test
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("\t1. Update the details of the Library", lines[0]);
		assertEquals("\t2. Add copies of a book to the Branch", lines[1]);
		assertEquals("\t3. Quit to previous", lines[2]);
		assertEquals("That is not a valid integer.", lines[4]);
		assertEquals("\t1. Update the details of the Library", lines[5]);
		assertEquals("\t2. Add copies of a book to the Branch", lines[6]);
		assertEquals("\t3. Quit to previous", lines[7]);
		
		db.close();
	}

	/**
	 * Test that lib3 prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void lib3TestOther() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.lib3(new Scanner("4\n3\n" + (numBranches+1) + "\n2\n4"), -1); //branchId not used in this test
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("\t1. Update the details of the Library", lines[0]);
		assertEquals("\t2. Add copies of a book to the Branch", lines[1]);
		assertEquals("\t3. Quit to previous", lines[2]);
		assertEquals("\'4\' is not a valid selection.", lines[4]);
		assertEquals("Please select an option:", lines[5]);
		assertEquals("\t1. Update the details of the Library", lines[7]);
		assertEquals("\t2. Add copies of a book to the Branch", lines[8]);
		assertEquals("\t3. Quit to previous", lines[9]);
		
		db.close();
	}

	/**
	 * Test that libUpdate successfully updates both name and address when new values for both are provided
	 */
	@Test
	public void libUpdateTestBoth() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		String branchName = "";
		String branchAddress = "";
		try {
			db.connectToDB();
			List<Branch> branches = db.getBranches();
			numBranches = branches.size();
			if(numBranches != 0) {
				branchId = branches.get(0).getBranchId();
				branchName = branches.get(0).getBranchName();
				branchAddress = branches.get(0).getBranchAddress();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches != 0)  {   //Must have a valid branch to work
			LibraryConsole.libUpdate(new Scanner("New Name\nNew Address\n3\n" + (numBranches+1) + "\n2\n4"), branchId);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("You have chosen to update the Branch with Branch Id: " + branchId + " and Branch Name: " + branchName + ".", lines[0]);
			assertEquals("Please enter a new branch name or enter N/A for no change:", lines[2]);
			assertEquals("Please enter a new branch address or enter N/A for no change:", lines[3]);
			try {
				Branch b = db.getBranch(branchId);
				assertEquals("New Name", b.getBranchName());
				assertEquals("New Address", b.getBranchAddress());
				db.updateBranch(branchId, branchName, branchAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("\t1. Update the details of the Library", lines[4]);
			assertEquals("\t2. Add copies of a book to the Branch", lines[5]);
			assertEquals("\t3. Quit to previous", lines[6]);		
		}
		
		db.close();
	}

	/**
	 * Test that libUpdate successfully updates just the name when only a new value for name is provided
	 */
	@Test
	public void libUpdateTestName() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		String branchName = "";
		String branchAddress = "";
		try {
			db.connectToDB();
			List<Branch> branches = db.getBranches();
			numBranches = branches.size();
			if(numBranches != 0) {
				branchId = branches.get(0).getBranchId();
				branchName = branches.get(0).getBranchName();
				branchAddress = branches.get(0).getBranchAddress();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches != 0)  {   //Must have a valid branch to work
			LibraryConsole.libUpdate(new Scanner("New Name\nN/A\n3\n" + (numBranches+1) + "\n2\n4"), branchId);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("You have chosen to update the Branch with Branch Id: " + branchId + " and Branch Name: " + branchName + ".", lines[0]);
			assertEquals("Please enter a new branch name or enter N/A for no change:", lines[2]);
			assertEquals("Please enter a new branch address or enter N/A for no change:", lines[3]);
			try {
				Branch b = db.getBranch(branchId);
				assertEquals("New Name", b.getBranchName());
				assertEquals(branchAddress, b.getBranchAddress());
				db.updateBranch(branchId, branchName, branchAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("\t1. Update the details of the Library", lines[4]);
			assertEquals("\t2. Add copies of a book to the Branch", lines[5]);
			assertEquals("\t3. Quit to previous", lines[6]);		
		}
		
		db.close();
	}

	/**
	 * Test that libUpdate successfully updates just the address when only a new value for address is provided
	 */
	@Test
	public void libUpdateTestAddress() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		String branchName = "";
		String branchAddress = "";
		try {
			db.connectToDB();
			List<Branch> branches = db.getBranches();
			numBranches = branches.size();
			if(numBranches != 0) {
				branchId = branches.get(0).getBranchId();
				branchName = branches.get(0).getBranchName();
				branchAddress = branches.get(0).getBranchAddress();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches != 0)  {   //Must have a valid branch to work
			LibraryConsole.libUpdate(new Scanner("N/A\nNew Address\n3\n" + (numBranches+1) + "\n2\n4"), branchId);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("You have chosen to update the Branch with Branch Id: " + branchId + " and Branch Name: " + branchName + ".", lines[0]);
			assertEquals("Please enter a new branch name or enter N/A for no change:", lines[2]);
			assertEquals("Please enter a new branch address or enter N/A for no change:", lines[3]);
			try {
				Branch b = db.getBranch(branchId);
				assertEquals(branchName, b.getBranchName());
				assertEquals("New Address", b.getBranchAddress());
				db.updateBranch(branchId, branchName, branchAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("\t1. Update the details of the Library", lines[4]);
			assertEquals("\t2. Add copies of a book to the Branch", lines[5]);
			assertEquals("\t3. Quit to previous", lines[6]);		
		}
		
		db.close();
	}

	/**
	 * Test that libUpdate does not update the branch if a new value is not provided for either field
	 */
	@Test
	public void libUpdateTestNeither() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		String branchName = "";
		String branchAddress = "";
		try {
			db.connectToDB();
			List<Branch> branches = db.getBranches();
			numBranches = branches.size();
			if(numBranches != 0) {
				branchId = branches.get(0).getBranchId();
				branchName = branches.get(0).getBranchName();
				branchAddress = branches.get(0).getBranchAddress();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches != 0)  {   //Must have a valid branch to work
			LibraryConsole.libUpdate(new Scanner("N/A\nN/A\n3\n" + (numBranches+1) + "\n2\n4"), branchId);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("You have chosen to update the Branch with Branch Id: " + branchId + " and Branch Name: " + branchName + ".", lines[0]);
			assertEquals("Please enter a new branch name or enter N/A for no change:", lines[2]);
			assertEquals("Please enter a new branch address or enter N/A for no change:", lines[3]);
			try {
				Branch b = db.getBranch(branchId);
				assertEquals(branchName, b.getBranchName());
				assertEquals(branchAddress, b.getBranchAddress());
				db.updateBranch(branchId, branchName, branchAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("\t1. Update the details of the Library", lines[4]);
			assertEquals("\t2. Add copies of a book to the Branch", lines[5]);
			assertEquals("\t3. Quit to previous", lines[6]);		
		}
		
		db.close();
	}	

	/**
	 * Test that libAddBook prints the correct output when the user selects a book
	 */
	@Test
	public void libAddBookTestBook() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		int bookId = -1;
		int numCopies = 0;
		try {
			db.connectToDB();
			List<Branch> branches = db.getBranches();
			numBranches = branches.size();
			branchId = branches.get(0).getBranchId();
			List<Book> books = db.getBooks();
			numBooks = books.size();
			bookId = books.get(0).getBookId();
			BookCopy bc = db.getBookCopy(branchId, bookId);
			if(bc != null)
				numCopies = bc.getNoOfCopies();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(numBranches != 0 && numBooks != 0) {
			LibraryConsole.libAddBook(new Scanner("1\n" + numCopies + "\n3\n" + (numBranches+1) + "\n2\n4"), branchId);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("Pick the Book you want to add copies of to your branch:", lines[0]);
			assertEquals("\t" + (numBooks + 1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("Existing number of copies: " + numCopies, lines[numBooks + 5]);
			assertEquals("Enter new number of copies:", lines[numBooks + 7]);
			assertEquals("\t1. Update the details of the Library", lines[numBooks + 9]);
			assertEquals("\t2. Add copies of a book to the Branch", lines[numBooks + 10]);
			assertEquals("\t3. Quit to previous", lines[numBooks + 11]);
		}
		
		db.close();
	}

	/**
	 * Test that libAddBook prints the correct output when the user decides to quit to previous	
	 */
	@Test
	public void libAddBookTestQuit() {
		Database db = new Database();
		int numBranches = 0;
		int numBooks = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
			numBooks = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(numBranches != 0 && numBooks != 0) {
			LibraryConsole.libAddBook(new Scanner((numBooks + 1) + "\n3\n" + (numBranches+1) + "\n2\n4"), -1);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("Pick the Book you want to add copies of to your branch:", lines[0]);
			assertEquals("\t" + (numBooks + 1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("\t1. Update the details of the Library", lines[numBooks + 4]);
			assertEquals("\t2. Add copies of a book to the Branch", lines[numBooks + 5]);
			assertEquals("\t3. Quit to previous", lines[numBooks + 6]);
		}
		
		db.close();
	}

	/**
	 * Test that libAddBook prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void libAddBookTestNotInt() {
		Database db = new Database();
		int numBranches = 0;
		int numBooks = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
			numBooks = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(numBranches != 0 && numBooks != 0) {
			LibraryConsole.libAddBook(new Scanner("Not an Integer\n" + (numBooks + 1) + "\n3\n" + (numBranches+1) + "\n2\n4"), -1);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("Pick the Book you want to add copies of to your branch:", lines[0]);
			assertEquals("\t" + (numBooks + 1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("That is not a valid integer.", lines[numBooks + 4]);
			assertEquals("Pick the Book you want to add copies of to your branch:", lines[numBooks + 5]);
			assertEquals("\t" + (numBooks + 1) + ". Quit to previous", lines[numBooks + numBooks + 7]);
		}
		
		db.close();
	}
	
	/**
	 * Test that libAddBook prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void libAddBookTestOther() {
		Database db = new Database();
		int numBranches = 0;
		int numBooks = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
			numBooks = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(numBranches != 0 && numBooks != 0) {
			LibraryConsole.libAddBook(new Scanner((numBooks + 2) + "\n" + (numBooks + 1) + "\n3\n" + (numBranches+1) + "\n2\n4"), -1);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("Pick the Book you want to add copies of to your branch:", lines[0]);
			assertEquals("\t" + (numBooks + 1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("\'" + (numBooks + 2) + "\' is not a valid book.", lines[numBooks + 4]);
			assertEquals("Pick the Book you want to add copies of to your branch:", lines[numBooks + 5]);
			assertEquals("\t" + (numBooks + 1) + ". Quit to previous", lines[numBooks + numBooks + 7]);
		}
		
		db.close();
	}
	
	/**
	 * Test that getNumCopies successfully updates the # of copies in the database when provided with an integer
	 */	
	@Test
	public void getNumCopiesTestInt() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		int bookId = -1;
		int numCopies = 0;
		try {
			db.connectToDB();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			branchId = branches.get(0).getBranchId();
			List <Book> books = db.getBooks();
			numBooks = books.size();
			bookId = books.get(0).getBookId();
			numCopies = db.getBookCopy(branchId, bookId).getNoOfCopies();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(numBranches != 0 && numBooks != 0) {
			LibraryConsole.getNumCopies(new Scanner("5\n3\n" + (numBranches+1) + "\n2\n4"), branchId, bookId);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("Existing number of copies: " + numCopies, lines[1]);
			assertEquals("Enter new number of copies:", lines[3]);
			assertEquals("\t1. Update the details of the Library", lines[5]);
			assertEquals("\t2. Add copies of a book to the Branch", lines[6]);
			assertEquals("\t3. Quit to previous", lines[7]);
			try {
				assertEquals(5, db.getBookCopy(branchId, bookId).getNoOfCopies());
				db.updateBookCopies(branchId, bookId, numCopies);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		db.close();
	}
	
	/**
	 * Test that getNumCopies prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void getNumCopiesTestNotInt() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		int bookId = -1;
		int numCopies = 0;
		try {
			db.connectToDB();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			branchId = branches.get(0).getBranchId();
			List <Book> books = db.getBooks();
			numBooks = books.size();
			bookId = books.get(0).getBookId();
			numCopies = db.getBookCopy(branchId, bookId).getNoOfCopies();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(numBranches != 0 && numBooks != 0) {
			LibraryConsole.getNumCopies(new Scanner("Not an Integer\n" + numCopies + "\n3\n" + (numBranches+1) + "\n2\n4"), branchId, bookId);
			String lines[] = systemOutput.toString().split("\r\n");
		
			assertEquals("Existing number of copies: " + numCopies, lines[1]);
			assertEquals("Enter new number of copies:", lines[3]);
			assertEquals("That is not a valid integer.", lines[5]);
			assertEquals("Existing number of copies: " + numCopies, lines[7]);
			assertEquals("Enter new number of copies:", lines[9]);
		}
		
		db.close();
	}
	
	/**
	 * Test that borr prints the correct output when the user inputs a valid cardNo
	 */
	@Test
	public void borrTestValid() {
		Database db = new Database();
		int cardNo = -1;
		try {
			db.connectToDB();
			List <Borrower> borrs = db.getBorrowers();
			if(borrs.size() != 0)
				cardNo = borrs.get(0).getCardNo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(cardNo != -1) {  //Otherwise no borrowers and no valid cardNos to check
			LibraryConsole.borr(new Scanner(cardNo + "\n3\n4"));
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Enter your Card Number:", lines[0]);
			assertEquals("\t1. Check out a book", lines[1]);
			assertEquals("\t2. Return a book", lines[2]);
			assertEquals("\t3. Quit to Previous", lines[3]);			
		}
		
		db.close();
	}
	
	/**
	 * Test that borr prints the correct output when the user inputs an invalid cardNo
	 */
	@Test
	public void borrTestInvalid() {
		Database db = new Database();
		int cardNo = -1;
		try {
			db.connectToDB();
			List <Borrower> borrs = db.getBorrowers();
			if(borrs.size() != 0)
				cardNo = borrs.get(0).getCardNo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(cardNo != -1) {  //Otherwise no borrowers and no valid cardNos to check
			LibraryConsole.borr(new Scanner("-1\n" + cardNo + "\n3\n4"));
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Enter your Card Number:", lines[0]);
			assertEquals("Sorry, -1 is not a valid card number.", lines[1]);
			assertEquals("Enter your Card Number:", lines[2]);			
		}
		
		db.close();
	}
	
	/**
	 * Test that borr prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void borrTestNotInt() {
		Database db = new Database();
		int cardNo = -1;
		try {
			db.connectToDB();
			List <Borrower> borrs = db.getBorrowers();
			if(borrs.size() != 0)
				cardNo = borrs.get(0).getCardNo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(cardNo != -1) {  //Otherwise no borrowers and no valid cardNos to check
			LibraryConsole.borr(new Scanner("Not an Integer\n" + cardNo + "\n3\n4"));
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Enter your Card Number:", lines[0]);
			assertEquals("That is not a valid integer.", lines[1]);
			assertEquals("Enter your Card Number:", lines[2]);			
		}
		
		db.close();
	}
	
	/**
	 * Test that borr1 prints the correct output when the user inputs 1
	 */
	@Test
	public void borr1Test1() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.borr1(new Scanner("1\n" + (numBranches+1) +"\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("\t1. Check out a book", lines[0]);
		assertEquals("\t2. Return a book", lines[1]);
		assertEquals("\t3. Quit to Previous", lines[2]);
		assertEquals("Pick the Branch you want to check out from:", lines[4]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 5]);
				
		db.close();
	}
	
	/**
	 * Test that borr1 prints the correct output when the user inputs 2
	 */
	@Test
	public void borr1Test2() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.borr1(new Scanner("2\n" + (numBranches+1) +"\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("\t1. Check out a book", lines[0]);
		assertEquals("\t2. Return a book", lines[1]);
		assertEquals("\t3. Quit to Previous", lines[2]);
		assertEquals("Pick the Branch you want to return to:", lines[4]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 5]);
				
		db.close();
	}
	
	/**
	 * Test that borr1 prints the correct output when the user inputs 3
	 */
	@Test
	public void borr1Test3() {
		LibraryConsole.borr1(new Scanner("3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("\t1. Check out a book", lines[0]);
		assertEquals("\t2. Return a book", lines[1]);
		assertEquals("\t3. Quit to Previous", lines[2]);
		assertEquals("Which category of user are you:", lines[4]);
		assertEquals("\t1. Librarian", lines[6]);
		assertEquals("\t2. Administrator", lines[7]);
		assertEquals("\t3. Borrower", lines[8]);
		assertEquals("\t4. Quit", lines[9]);
	}
	
	/**
	 * Test that borr1 prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void borr1TestNotInt() {
		LibraryConsole.borr1(new Scanner("Not an Integer\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("\t1. Check out a book", lines[0]);
		assertEquals("\t2. Return a book", lines[1]);
		assertEquals("\t3. Quit to Previous", lines[2]);
		assertEquals("That is not a valid integer.", lines[4]);
		assertEquals("\t1. Check out a book", lines[5]);
		assertEquals("\t2. Return a book", lines[6]);
		assertEquals("\t3. Quit to Previous", lines[7]);
	}
	
	/**
	 * Test that borr1 prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void borr1TestOther() {
		LibraryConsole.borr1(new Scanner("4\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("\t1. Check out a book", lines[0]);
		assertEquals("\t2. Return a book", lines[1]);
		assertEquals("\t3. Quit to Previous", lines[2]);
		assertEquals("\'4\' is not a valid selection.", lines[4]);
		assertEquals("Please select an option:", lines[5]);
		assertEquals("\t1. Check out a book", lines[7]);
		assertEquals("\t2. Return a book", lines[8]);
		assertEquals("\t3. Quit to Previous", lines[9]);
	}
	
	/**
	 * Test that borrCheckOutBranch prints the correct output when a branch is selected 
	 */
	@Test
	public void borrCheckOutBranchTestBranch() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		try {
			db.connectToDB();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			branchId = branches.get(0).getBranchId();
			List <Book> books = db.getBooks();
			numBooks = 0;
			for(Book b : books) {
				BookCopy bc = db.getBookCopy(branchId,  b.getBookId());
				if(bc != null && bc.getNoOfCopies() > 0) {
					numBooks++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches > 0) {
			LibraryConsole.borrCheckOutBranch(new Scanner("1\n" + (numBooks+1) +"\n3\n4"), -1);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Branch you want to check out from:", lines[0]);
			assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 1]);
			assertEquals("Pick the Book you want to check out:", lines[numBranches + 3]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBranches + numBooks + 5]);
		}
		
		db.close();
	}
	
	/**
	 * Test that borrCheckOutBranch prints the correct output when the user decides to quit to previous
	 */
	@Test
	public void borrCheckOutBranchTestQuit() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.borrCheckOutBranch(new Scanner((numBranches+1) +"\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("Pick the Branch you want to check out from:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 1]);
		assertEquals("\t1. Check out a book", lines[numBranches + 3]);
		assertEquals("\t2. Return a book", lines[numBranches + 4]);
		assertEquals("\t3. Quit to Previous", lines[numBranches + 5]);
		
		db.close();
	}
	
	/**
	 * Test that borrCheckOutBranch prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void borrCheckOutBranchTestNotInt() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.borrCheckOutBranch(new Scanner("Not an Integer\n" + (numBranches+1) +"\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("Pick the Branch you want to check out from:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 1]);
		assertEquals("That is not a valid integer.", lines[numBranches + 3]);
		assertEquals("Pick the Branch you want to check out from:", lines[numBranches + 4]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + numBranches + 5]);
		
		db.close();
	}
	
	/**
	 * Test that borrCheckOutBranch prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void borrCheckOutBranchTestOther() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.borrCheckOutBranch(new Scanner((numBranches+2) + "\n" + (numBranches+1) +"\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("Pick the Branch you want to check out from:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 1]);
		assertEquals("\'" + (numBranches+2) + "\' is not a valid branch.", lines[numBranches + 3]);
		assertEquals("Pick the Branch you want to check out from:", lines[numBranches + 4]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + numBranches + 5]);
		
		db.close();
	}
	
	/**
	 * Test that borrCheckOutBook prints the correct output when a book is selected 
	 */
	@Test
	public void borrCheckOutBookTestBook() {
		Database db = new Database();
		int cardNo = -1;
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		int bookId = -1;
		try {
			db.connectToDB();
			cardNo = db.getBorrowers().get(0).getCardNo();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			branchId = branches.get(0).getBranchId();
			List <Book> books = db.getBooks();
			numBooks = 0;
			for(Book b : books) {
				BookCopy bc = db.getBookCopy(branchId,  b.getBookId());
				if(bc != null && bc.getNoOfCopies() > 0 && db.getBookLoan(branchId, b.getBookId(), cardNo) == null) {
					if(bookId == -1) {
						bookId = b.getBookId();
					}
					numBooks++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches > 0) {
			LibraryConsole.borrCheckOutBook(new Scanner("1\n" + (numBranches+1) +"\n3\n4"), cardNo, branchId);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Book you want to check out:", lines[0]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("Pick the Branch you want to check out from:", lines[numBooks + 4]);
			assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBooks + numBranches + 5]);
			try {
				assertNotEquals(null, db.getBookLoan(branchId, bookId, cardNo));
				db.deleteBookLoan(branchId, bookId, cardNo);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		db.close();
	}
	
	/**
	 * Test that borrCheckOutBook prints the correct output when the user decides to quit to previous
	 */
	@Test
	public void borrCheckOutBookTestQuit() {
		Database db = new Database();
		int cardNo = -1;
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		int bookId = -1;
		try {
			db.connectToDB();
			cardNo = db.getBorrowers().get(0).getCardNo();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			branchId = branches.get(0).getBranchId();
			List <Book> books = db.getBooks();
			numBooks = 0;
			for(Book b : books) {
				BookCopy bc = db.getBookCopy(branchId,  b.getBookId());
				if(bc != null && bc.getNoOfCopies() > 0 && db.getBookLoan(branchId, b.getBookId(), cardNo) == null) {
					if(bookId == -1) {
						bookId = b.getBookId();
					}
					numBooks++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches > 0) {
			LibraryConsole.borrCheckOutBook(new Scanner((numBooks+1) + "\n3\n4"), cardNo, branchId);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Book you want to check out:", lines[0]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("\t1. Check out a book", lines[numBooks + 4]);
			assertEquals("\t2. Return a book", lines[numBooks + 5]);
			assertEquals("\t3. Quit to Previous", lines[numBooks + 6]);			
		}
		
		db.close();
	}
	
	/**
	 * Test that borrCheckOutBook prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void borrCheckOutBookTestNotInt() {
		Database db = new Database();
		int cardNo = -1;
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		int bookId = -1;
		try {
			db.connectToDB();
			cardNo = db.getBorrowers().get(0).getCardNo();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			branchId = branches.get(0).getBranchId();
			List <Book> books = db.getBooks();
			numBooks = 0;
			for(Book b : books) {
				BookCopy bc = db.getBookCopy(branchId,  b.getBookId());
				if(bc != null && bc.getNoOfCopies() > 0 && db.getBookLoan(branchId, b.getBookId(), cardNo) == null) {
					if(bookId == -1) {
						bookId = b.getBookId();
					}
					numBooks++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches > 0) {
			LibraryConsole.borrCheckOutBook(new Scanner("Not an Integer\n" + (numBooks+1) + "\n3\n4"), cardNo, branchId);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Book you want to check out:", lines[0]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("That is not a valid integer.", lines[numBooks + 4]);
			assertEquals("Pick the Book you want to check out:", lines[numBooks + 5]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + numBooks + 7]);			
		}
		
		db.close();
	}
	
	/**
	 * Test that borrCheckOutBook prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void borrCheckOutBookTestOther() {
		Database db = new Database();
		int cardNo = -1;
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		int bookId = -1;
		try {
			db.connectToDB();
			cardNo = db.getBorrowers().get(0).getCardNo();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			branchId = branches.get(0).getBranchId();
			List <Book> books = db.getBooks();
			numBooks = 0;
			for(Book b : books) {
				BookCopy bc = db.getBookCopy(branchId,  b.getBookId());
				if(bc != null && bc.getNoOfCopies() > 0 && db.getBookLoan(branchId, b.getBookId(), cardNo) == null) {
					if(bookId == -1) {
						bookId = b.getBookId();
					}
					numBooks++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(numBranches > 0) {
			LibraryConsole.borrCheckOutBook(new Scanner((numBooks+2) + "\n" + (numBooks+1) + "\n3\n4"), cardNo, branchId);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Book you want to check out:", lines[0]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("\'" + (numBooks+2) + "\' is not a valid book.", lines[numBooks + 4]);
			assertEquals("Pick the Book you want to check out:", lines[numBooks + 5]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + numBooks + 7]);			
		}
		
		db.close();
	}
	
	/**
	 * Test that borrReturnBranch prints the correct output when a branch is selected 
	 */
	@Test
	public void borrReturnBranchTestBranchLoans() {
		Database db = new Database();
		int cardNo = -1;
		int numBranches = 0;
		int branchId = -1;
		int branchIndex = 0; 
		int numBooks = 0;
		try {
			db.connectToDB();
			cardNo = db.getBorrowers().get(0).getCardNo();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			List <Book> books = db.getBooks();
			for(Branch br : branches) {
				numBooks = 0;
				for(Book b : books) {
					if(db.getBookLoan(br.getBranchId(), b.getBookId(), cardNo) != null) {
						numBooks++;
					}
				}
				if(numBooks > 0) {
					branchId = br.getBranchId();
					break;
				} else {
					branchIndex++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(branchId != -1) {  //Otherwise no books to return to any branches
			LibraryConsole.borrReturnBranch(new Scanner((branchIndex+1) + "\n" + (numBooks+1) +"\n3\n4"), cardNo);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Branch you want to return to:", lines[0]);
			assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 1]);
			assertEquals("Pick the Book you want to return:", lines[numBranches + 3]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBranches + numBooks + 5]);
		}
		
		db.close();
	}
	
	/**
	 * Test that borrReturnBranch prints the correct output when a branch is selected 
	 */
	@Test
	public void borrReturnBranchTestBranchNoLoans() {
		Database db = new Database();
		int cardNo = -1;
		int numBranches = 0;
		int branchId = -1;
		int branchIndex = 0; 
		int numBooks = 0;
		try {
			db.connectToDB();
			cardNo = db.getBorrowers().get(0).getCardNo();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			List <Book> books = db.getBooks();
			for(Branch br : branches) {
				numBooks = 0;
				for(Book b : books) {
					if(db.getBookLoan(br.getBranchId(), b.getBookId(), cardNo) != null) {
						numBooks++;
						break;
					}
				}
				if(numBooks == 0) {
					branchId = br.getBranchId();
					break;
				} else {
					branchIndex++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(branchId != -1) {  //Otherwise no books to return to any branches
			LibraryConsole.borrReturnBranch(new Scanner((branchIndex+1) + "\n" + (numBranches+1) +"\n3\n4"), cardNo);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Branch you want to return to:", lines[0]);
			assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 1]);
			assertEquals("You do not appear to have any books checked out from this branch currently.", lines[numBranches + 4]);
			assertEquals("Pick the Branch you want to return to:", lines[numBranches + 6]);
			assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + numBranches + 7]);
		}
		
		db.close();
	}
	
	/**
	 * Test that borrReturnBranch prints the correct output when the user decides to quit to previous
	 */
	@Test
	public void borrReturnBranchTestQuit() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.borrReturnBranch(new Scanner((numBranches+1) +"\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("Pick the Branch you want to return to:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 1]);
		assertEquals("\t1. Check out a book", lines[numBranches + 3]);
		assertEquals("\t2. Return a book", lines[numBranches + 4]);
		assertEquals("\t3. Quit to Previous", lines[numBranches + 5]);
		
		db.close();	
	}
	
	/**
	 * Test that borrReturnBranch prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void borrReturnBranchTestNotInt() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.borrReturnBranch(new Scanner("Not an Integer\n" + (numBranches+1) +"\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("Pick the Branch you want to return to:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 1]);
		assertEquals("That is not a valid integer.", lines[numBranches + 3]);
		assertEquals("Pick the Branch you want to return to:", lines[numBranches + 4]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + numBranches + 5]);
		
		db.close();		
	}
	
	/**
	 * Test that borrReturnBranch prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void borrReturnBranchTestOther() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.borrReturnBranch(new Scanner((numBranches+2) + "\n" + (numBranches+1) +"\n3\n4"), -1);
		String lines[] = systemOutput.toString().split("\r\n");
			
		assertEquals("Pick the Branch you want to return to:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 1]);
		assertEquals("\'" + (numBranches+2) + "\' is not a valid branch.", lines[numBranches + 3]);
		assertEquals("Pick the Branch you want to return to:", lines[numBranches + 4]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + numBranches + 5]);
		
		db.close();	
	}
	
	/**
	 * Test that borrReturnBook prints the correct output when the user decides to quit to previous
	 */
	@Test
	public void borrReturnBookTestQuit() {
		Database db = new Database();
		int cardNo = -1;
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		try {
			db.connectToDB();
			cardNo = db.getBorrowers().get(0).getCardNo();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			List <Book> books = db.getBooks();
			for(Branch br : branches) {
				numBooks = 0;
				for(Book b : books) {
					if(db.getBookLoan(br.getBranchId(), b.getBookId(), cardNo) != null) {
						numBooks++;
					}
				}
				if(numBooks > 0) {
					branchId = br.getBranchId();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(branchId != -1) {
			LibraryConsole.borrReturnBook(new Scanner((numBooks+1) + "\n" + (numBranches+1) +"\n3\n4"), cardNo, branchId);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Book you want to return:", lines[0]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("\t1. Check out a book", lines[numBooks + 4]);
			assertEquals("\t2. Return a book", lines[numBooks + 5]);
			assertEquals("\t3. Quit to Previous", lines[numBooks + 6]);
		}
		
		db.close();	
	}
	
	/**
	 * Test that borrReturnBook prints the correct output when the user inputs a non-integer
	 */
	@Test
	public void borrReturnBookTestNotInt() {
		Database db = new Database();
		int cardNo = -1;
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		try {
			db.connectToDB();
			cardNo = db.getBorrowers().get(0).getCardNo();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			List <Book> books = db.getBooks();
			for(Branch br : branches) {
				numBooks = 0;
				for(Book b : books) {
					if(db.getBookLoan(br.getBranchId(), b.getBookId(), cardNo) != null) {
						numBooks++;
					}
				}
				if(numBooks > 0) {
					branchId = br.getBranchId();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(branchId != -1) {
			LibraryConsole.borrReturnBook(new Scanner("Not an Integer\n" + (numBooks+1) + "\n" + (numBranches+1) +"\n3\n4"), cardNo, branchId);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Book you want to return:", lines[0]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("That is not a valid integer.", lines[numBooks + 4]);
			assertEquals("Pick the Book you want to return:", lines[numBooks + 5]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + numBooks + 7]);
		}
		
		db.close();
	}
	
	/**
	 * Test that borrReturnBook prints the correct output when the user inputs an invalid integer
	 */
	@Test
	public void borrReturnBookTestOther() {
		Database db = new Database();
		int cardNo = -1;
		int numBranches = 0;
		int branchId = -1;
		int numBooks = 0;
		try {
			db.connectToDB();
			cardNo = db.getBorrowers().get(0).getCardNo();
			List <Branch> branches = db.getBranches();
			numBranches = branches.size();
			List <Book> books = db.getBooks();
			for(Branch br : branches) {
				numBooks = 0;
				for(Book b : books) {
					if(db.getBookLoan(br.getBranchId(), b.getBookId(), cardNo) != null) {
						numBooks++;
					}
				}
				if(numBooks > 0) {
					branchId = br.getBranchId();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(branchId != -1) {
			LibraryConsole.borrReturnBook(new Scanner((numBooks+2) + "\n" + (numBooks+1) + "\n" + (numBranches+1) +"\n3\n4"), cardNo, branchId);
			String lines[] = systemOutput.toString().split("\r\n");
			
			assertEquals("Pick the Book you want to return:", lines[0]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 2]);
			assertEquals("\'" + (numBooks+2) + "\' is not a valid book.", lines[numBooks + 4]);
			assertEquals("Pick the Book you want to return:", lines[numBooks + 5]);
			assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + numBooks + 7]);
		}
		
		db.close();
	}
	
	/**
	 * Test that admin prints out the correct output when the user inputs the correct password
	 */
	@Test
	public void adminTestCorrect() {
		LibraryConsole.admin(new Scanner("Java_Basics_Pa$$word\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");
		
		assertEquals("Please enter the admin password (enter \'quit\' to quit):", lines[0]);
		assertEquals("What would you like to do:", lines[1]);
		assertEquals("\t1. Add Data", lines[3]);
		assertEquals("\t2. Update Data", lines[4]);
		assertEquals("\t3. Delete Data", lines[5]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[6]);
		assertEquals("\t5. Quit to Previous", lines[7]);
	}
	
	/**
	 * Test that admin prints out the correct output when the user inputs the wrong password
	 */
	@Test
	public void adminTestIncorrect() {
		LibraryConsole.admin(new Scanner("Java_Basics_Password\nquit\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Please enter the admin password (enter \'quit\' to quit):", lines[0]);
		assertEquals("Sorry, that is incorrect. Please try again.", lines[1]);
		assertEquals("Please enter the admin password (enter \'quit\' to quit):", lines[2]);
	}
	
	/**
	 * Test that admin1 prints out the correct output when the user inputs 1
	 */
	@Test
	public void admin1Test1() {
		LibraryConsole.admin1(new Scanner("1\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What would you like to do:", lines[0]);
		assertEquals("\t1. Add Data", lines[2]);
		assertEquals("\t2. Update Data", lines[3]);
		assertEquals("\t3. Delete Data", lines[4]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[5]);
		assertEquals("\t5. Quit to Previous", lines[6]);
		assertEquals("Which table would you like to add to:", lines[8]);
		assertEquals("\t1. Books", lines[10]);
		assertEquals("\t2. Authors", lines[11]);
		assertEquals("\t3. Publishers", lines[12]);
		assertEquals("\t4. Library Branches", lines[13]);
		assertEquals("\t5. Borrowers", lines[14]);
		assertEquals("\t6. Quit to Previous", lines[15]);
	}
	
	/**
	 * Test that admin1 prints out the correct output when the user inputs 2
	 */
	@Test
	public void admin1Test2() {
		LibraryConsole.admin1(new Scanner("2\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What would you like to do:", lines[0]);
		assertEquals("\t1. Add Data", lines[2]);
		assertEquals("\t2. Update Data", lines[3]);
		assertEquals("\t3. Delete Data", lines[4]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[5]);
		assertEquals("\t5. Quit to Previous", lines[6]);
		assertEquals("Which table would you like to update:", lines[8]);
		assertEquals("\t1. Books", lines[10]);
		assertEquals("\t2. Authors", lines[11]);
		assertEquals("\t3. Publishers", lines[12]);
		assertEquals("\t4. Library Branches", lines[13]);
		assertEquals("\t5. Borrowers", lines[14]);
		assertEquals("\t6. Quit to Previous", lines[15]);
	}
	
	/**
	 * Test that admin1 prints out the correct output when the user inputs 3
	 */
	@Test
	public void admin1Test3() {

		LibraryConsole.admin1(new Scanner("3\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What would you like to do:", lines[0]);
		assertEquals("\t1. Add Data", lines[2]);
		assertEquals("\t2. Update Data", lines[3]);
		assertEquals("\t3. Delete Data", lines[4]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[5]);
		assertEquals("\t5. Quit to Previous", lines[6]);
		assertEquals("Which table would you like to delete from:", lines[8]);
		assertEquals("\t1. Books", lines[10]);
		assertEquals("\t2. Authors", lines[11]);
		assertEquals("\t3. Publishers", lines[12]);
		assertEquals("\t4. Library Branches", lines[13]);
		assertEquals("\t5. Borrowers", lines[14]);
		assertEquals("\t6. Quit to Previous", lines[15]);
	}
	
	/**
	 * Test that admin1 prints out the correct output when the user inputs 4
	 */
	@Test
	public void admin1Test4() {
		Database db = new Database();
		int numLoans = 0;
		try {
			db.connectToDB();
			numLoans = db.getLoans().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LibraryConsole.admin1(new Scanner("4\n" + (numLoans+1) + "\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What would you like to do:", lines[0]);
		assertEquals("\t1. Add Data", lines[2]);
		assertEquals("\t2. Update Data", lines[3]);
		assertEquals("\t3. Delete Data", lines[4]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[5]);
		assertEquals("\t5. Quit to Previous", lines[6]);
		assertEquals("Which rental would you like to override the due date for:", lines[8]);
		assertEquals("\t" + (numLoans+1) + ". Quit to previous", lines[numLoans + 10]);
		
		db.close();
	}
	
	/**
	 * Test that admin1 prints out the correct output when the user inputs 5
	 */
	@Test
	public void admin1Test5() {
		LibraryConsole.admin1(new Scanner("5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What would you like to do:", lines[0]);
		assertEquals("\t1. Add Data", lines[2]);
		assertEquals("\t2. Update Data", lines[3]);
		assertEquals("\t3. Delete Data", lines[4]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[5]);
		assertEquals("\t5. Quit to Previous", lines[6]);
		assertEquals("Which category of user are you:", lines[8]);
		assertEquals("\t1. Librarian", lines[10]);
		assertEquals("\t2. Administrator", lines[11]);
		assertEquals("\t3. Borrower", lines[12]);
		assertEquals("\t4. Quit", lines[13]);
	}
	
	/**
	 * Test that admin1 prints out the correct output when the user inputs a non-integer
	 */
	@Test
	public void admin1TestNotInt() {
		LibraryConsole.admin1(new Scanner("Not an Integer\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What would you like to do:", lines[0]);
		assertEquals("\t1. Add Data", lines[2]);
		assertEquals("\t2. Update Data", lines[3]);
		assertEquals("\t3. Delete Data", lines[4]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[5]);
		assertEquals("\t5. Quit to Previous", lines[6]);
		assertEquals("That is not a valid integer.", lines[8]);
		assertEquals("What would you like to do:", lines[9]);
		assertEquals("\t1. Add Data", lines[11]);
		assertEquals("\t2. Update Data", lines[12]);
		assertEquals("\t3. Delete Data", lines[13]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[14]);
		assertEquals("\t5. Quit to Previous", lines[15]);
	}
	
	/**
	 * Test that admin1 prints out the correct output when the user inputs an invalid integer
	 */
	@Test
	public void admin1TestOther() {
		LibraryConsole.admin1(new Scanner("6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What would you like to do:", lines[0]);
		assertEquals("\t1. Add Data", lines[2]);
		assertEquals("\t2. Update Data", lines[3]);
		assertEquals("\t3. Delete Data", lines[4]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[5]);
		assertEquals("\t5. Quit to Previous", lines[6]);
		assertEquals("\'6\' is not a valid selection.", lines[8]);
		assertEquals("What would you like to do:", lines[9]);
		assertEquals("\t1. Add Data", lines[11]);
		assertEquals("\t2. Update Data", lines[12]);
		assertEquals("\t3. Delete Data", lines[13]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[14]);
		assertEquals("\t5. Quit to Previous", lines[15]);
	}
	
	/**
	 * Test that adminAdd prints out the correct output when the user inputs 1
	 */
	@Test
	public void adminAddTest1() {
		LibraryConsole.adminAdd(new Scanner("1\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to add to:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("What is the title of the book (enter \'quit\' to quit):", lines[9]);
	}
	
	/**
	 * Test that adminAdd prints out the correct output when the user inputs 2
	 */
	@Test
	public void adminAddTest2() {
		LibraryConsole.adminAdd(new Scanner("2\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to add to:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("What is the name of the author (enter \'quit\' to quit):", lines[9]);
	}
	
	/**
	 * Test that adminAdd prints out the correct output when the user inputs 3
	 */
	@Test
	public void adminAddTest3() {
		LibraryConsole.adminAdd(new Scanner("3\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to add to:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("What is the name of the publisher (enter \'quit\' to quit):", lines[9]);		
	}
	
	/**
	 * Test that adminAdd prints out the correct output when the user inputs 4
	 */
	@Test
	public void adminAddTest4() {
		LibraryConsole.adminAdd(new Scanner("4\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to add to:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("What is the name of the branch (enter \'quit\' to quit):", lines[9]);		
	}
	
	/**
	 * Test that adminAdd prints out the correct output when the user inputs 5
	 */
	@Test
	public void adminAddTest5() {
		LibraryConsole.adminAdd(new Scanner("5\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to add to:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("What is the name of the borrower (enter \'quit\' to quit):", lines[9]);		
	}
	
	/**
	 * Test that adminAdd prints out the correct output when the user inputs 6
	 */
	@Test
	public void adminAddTest6() {
		LibraryConsole.adminAdd(new Scanner("6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to add to:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("What would you like to do:", lines[9]);
		assertEquals("\t1. Add Data", lines[11]);
		assertEquals("\t2. Update Data", lines[12]);
		assertEquals("\t3. Delete Data", lines[13]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[14]);
		assertEquals("\t5. Quit to Previous", lines[15]);
	}
	
	/**
	 * Test that adminAdd prints out the correct output when the user inputs a non-integer
	 */
	@Test
	public void adminAddTestNotInt() {
		LibraryConsole.adminAdd(new Scanner("Not an Integer\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to add to:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("That is not a valid integer.", lines[9]);
		assertEquals("Which table would you like to add to:", lines[10]);
		assertEquals("\t1. Books", lines[12]);
		assertEquals("\t2. Authors", lines[13]);
		assertEquals("\t3. Publishers", lines[14]);
		assertEquals("\t4. Library Branches", lines[15]);
		assertEquals("\t5. Borrowers", lines[16]);
		assertEquals("\t6. Quit to Previous", lines[17]);
	}
	
	/**
	 * Test that adminAdd prints out the correct output when the user inputs an invalid integer
	 */
	@Test
	public void adminAddTestOther() {
		LibraryConsole.adminAdd(new Scanner("7\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to add to:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("7 is not a valid table choice.", lines[9]);
		assertEquals("Which table would you like to add to:", lines[10]);
		assertEquals("\t1. Books", lines[12]);
		assertEquals("\t2. Authors", lines[13]);
		assertEquals("\t3. Publishers", lines[14]);
		assertEquals("\t4. Library Branches", lines[15]);
		assertEquals("\t5. Borrowers", lines[16]);
		assertEquals("\t6. Quit to Previous", lines[17]);
	}
	
	@Test
	public void adminAddBookTitleTest() {
		LibraryConsole.adminAddBookTitle(new Scanner("New Title\nquit\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What is the title of the book (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the name of the book's author (enter \'quit\' to quit):", lines[1]);
	}
	
	@Test
	public void adminAddBookTitleTestQuit() {
		LibraryConsole.adminAddBookTitle(new Scanner("quit\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What is the title of the book (enter \'quit\' to quit):", lines[0]);
		assertEquals("Which table would you like to add to:", lines[1]);
	}
	
	@Test
	public void adminAddBookAuthorTestValid() {
		Database db = new Database();
		String name = "";
		try {
			db.connectToDB();
			name = db.getAuthors().get(0).getAuthorName();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminAddBookAuthor(new Scanner(name + "\nquit\n6\n5\n4\n"), "New Title");
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the book's author (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the name of the book's publisher (enter \'quit\' to quit):", lines[1]);
		
		db.close();
	}
	
	@Test
	public void adminAddBookAuthorTestInvalid() {
		LibraryConsole.adminAddBookAuthor(new Scanner(" \nquit\n6\n5\n4\n"), "New Title");
		String lines[] = systemOutput.toString().split("\r\n");	
			
		assertEquals("What is the name of the book's author (enter \'quit\' to quit):", lines[0]);
		assertEquals("Could not find an author with the name:  ", lines[1]);
		assertEquals("Please input a different name or quit and add them to the author table first.", lines[2]);
		assertEquals("What is the name of the book's author (enter \'quit\' to quit):", lines[3]);
	}
	
	@Test
	public void adminAddBookAuthorTestQuit() {
		LibraryConsole.adminAddBookAuthor(new Scanner("quit\n6\n5\n4\n"), "New Title");
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What is the name of the book's author (enter \'quit\' to quit):", lines[0]);
		assertEquals("Which table would you like to add to:", lines[1]);
	}
	
	@Test
	public void adminAddBookPublisherTestValid() {
		Database db = new Database();
		String name = "";
		int authId = -1;
		int pubId = -1;
		try {
			db.connectToDB();
			Publisher p = db.getPublishers().get(0);
			name = p.getPublisherName();
			pubId = p.getPublisherId();
			authId = db.getAuthors().get(0).getAuthorId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminAddBookPublisher(new Scanner(name + "\nno\n6\n5\n4\n"), "New Title", authId);
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the book's publisher (enter \'quit\' to quit):", lines[0]);
		assertEquals("Would you like to add another book (y):", lines[1]);
		assertEquals("Which table would you like to add to:", lines[2]);
		
		try {
			int bookId = -1;
			List<Book> books = db.getBooks();
			for(Book b : books) {
				if("New Title".equals(b.getTitle()) && b.getAuthId() == authId && b.getPubId() == pubId) {
					bookId = b.getBookId();
				}
			}
			assertNotEquals(-1, bookId);
			db.deleteBook(bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminAddBookPublisherTestInvalid() {
		LibraryConsole.adminAddBookPublisher(new Scanner(" \nquit\n6\n5\n4\n"), "New Title", -1);
		String lines[] = systemOutput.toString().split("\r\n");	
			
		assertEquals("What is the name of the book's publisher (enter \'quit\' to quit):", lines[0]);
		assertEquals("Could not find a publisher with the name:  ", lines[1]);
		assertEquals("Please input a different name or quit and add them to the publisher table first.", lines[2]);
		assertEquals("What is the name of the book's publisher (enter \'quit\' to quit):", lines[3]);
	}
	
	@Test
	public void adminAddBookPublisherTestQuit() {
		LibraryConsole.adminAddBookPublisher(new Scanner("quit\n6\n5\n4\n"), "New Title", -1);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What is the name of the book's publisher (enter \'quit\' to quit):", lines[0]);
		assertEquals("Which table would you like to add to:", lines[1]);
	}
	
	@Test
	public void adminAddBookPublisherTestYes() {
		Database db = new Database();
		String name = "";
		int authId = -1;
		int pubId = -1;
		try {
			db.connectToDB();
			Publisher p = db.getPublishers().get(0);
			name = p.getPublisherName();
			pubId = p.getPublisherId();
			authId = db.getAuthors().get(0).getAuthorId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminAddBookPublisher(new Scanner(name + "\ny\nquit\n6\n5\n4\n"), "New Title", authId);
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the book's publisher (enter \'quit\' to quit):", lines[0]);
		assertEquals("Would you like to add another book (y):", lines[1]);
		assertEquals("What is the title of the book (enter \'quit\' to quit):", lines[2]);
		
		try {
			int bookId = -1;
			List<Book> books = db.getBooks();
			for(Book b : books) {
				if("New Title".equals(b.getTitle()) && b.getAuthId() == authId && b.getPubId() == pubId) {
					bookId = b.getBookId();
				}
			}
			assertNotEquals(-1, bookId);
			db.deleteBook(bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminAddAuthorTest() {
		LibraryConsole.adminAddAuthor(new Scanner("New Author\nno\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the author (enter \'quit\' to quit):", lines[0]);
		assertEquals("Would you like to add another author (y):", lines[1]);
		assertEquals("Which table would you like to add to:", lines[2]);
		
		Database db = new Database();
		try {
			db.connectToDB();
			int authId = -1;
			List<Author> auths = db.getAuthors();
			for(Author a : auths) {
				if("New Author".equals(a.getAuthorName())) {
					authId = a.getAuthorId();
				}
			}
			assertNotEquals(-1, authId);
			db.deleteAuthor(authId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminAddAuthorTestQuit() {
		LibraryConsole.adminAddAuthor(new Scanner("quit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("What is the name of the author (enter \'quit\' to quit):", lines[0]);
		assertEquals("Which table would you like to add to:", lines[1]);		
	}
	
	@Test
	public void adminAddAuthorTestYes() {
		LibraryConsole.adminAddAuthor(new Scanner("New Author\ny\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the author (enter \'quit\' to quit):", lines[0]);
		assertEquals("Would you like to add another author (y):", lines[1]);
		assertEquals("What is the name of the author (enter \'quit\' to quit):", lines[2]);
		
		Database db = new Database();
		try {
			db.connectToDB();
			int authId = -1;
			List<Author> auths = db.getAuthors();
			for(Author a : auths) {
				if("New Author".equals(a.getAuthorName())) {
					authId = a.getAuthorId();
				}
			}
			assertNotEquals(-1, authId);
			db.deleteAuthor(authId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminAddPublisherTest() {
		LibraryConsole.adminAddPublisher(new Scanner("New Publisher\nNew Address\nNew Phone\nno\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the publisher (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the publisher (enter \'quit\' to quit):", lines[1]);
		assertEquals("What is the phone number of the publisher (enter \'quit\' to quit):", lines[2]);
		assertEquals("Would you like to add another publisher (y):", lines[3]);
		assertEquals("Which table would you like to add to:", lines[4]);
		
		Database db = new Database();
		try {
			db.connectToDB();
			int pubId = -1;
			List<Publisher> pubs = db.getPublishers();
			for(Publisher p : pubs) {
				if("New Publisher".equals(p.getPublisherName()) && "New Address".equals(p.getPublisherAddress()) && "New Phone".equals(p.getPublisherPhone())) {
					pubId = p.getPublisherId();
				}
			}
			assertNotEquals(-1, pubId);
			db.deletePublisher(pubId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminAddPublisherTestQuit1() {
		LibraryConsole.adminAddPublisher(new Scanner("quit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the publisher (enter \'quit\' to quit):", lines[0]);
		assertEquals("Which table would you like to add to:", lines[1]);
	}
	
	@Test
	public void adminAddPublisherTestQuit2() {
		LibraryConsole.adminAddPublisher(new Scanner("New Publisher\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the publisher (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the publisher (enter \'quit\' to quit):", lines[1]);
		assertEquals("Which table would you like to add to:", lines[2]);
	}
	
	@Test
	public void adminAddPublisherTestQuit3() {
		LibraryConsole.adminAddPublisher(new Scanner("New Publisher\nNew Address\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the publisher (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the publisher (enter \'quit\' to quit):", lines[1]);
		assertEquals("What is the phone number of the publisher (enter \'quit\' to quit):", lines[2]);
		assertEquals("Which table would you like to add to:", lines[3]);
	}
	
	@Test
	public void adminAddPublisherTestYes() {
		LibraryConsole.adminAddPublisher(new Scanner("New Publisher\nNew Address\nNew Phone\nyes\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the publisher (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the publisher (enter \'quit\' to quit):", lines[1]);
		assertEquals("What is the phone number of the publisher (enter \'quit\' to quit):", lines[2]);
		assertEquals("Would you like to add another publisher (y):", lines[3]);
		assertEquals("What is the name of the publisher (enter \'quit\' to quit):", lines[4]);
		
		Database db = new Database();
		try {
			db.connectToDB();
			int pubId = -1;
			List<Publisher> pubs = db.getPublishers();
			for(Publisher p : pubs) {
				if("New Publisher".equals(p.getPublisherName()) && "New Address".equals(p.getPublisherAddress()) && "New Phone".equals(p.getPublisherPhone())) {
					pubId = p.getPublisherId();
				}
			}
			assertNotEquals(-1, pubId);
			db.deletePublisher(pubId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminAddBranchTest() {
		LibraryConsole.adminAddBranch(new Scanner("New Branch\nNew Address\nno\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the branch (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the branch (enter \'quit\' to quit):", lines[1]);
		assertEquals("Would you like to add another branch (y):", lines[2]);
		assertEquals("Which table would you like to add to:", lines[3]);
		
		Database db = new Database();
		try {
			db.connectToDB();
			int branchId = -1;
			List<Branch> branches = db.getBranches();
			for(Branch b : branches) {
				if("New Branch".equals(b.getBranchName()) && "New Address".equals(b.getBranchAddress())) {
					branchId = b.getBranchId();
				}
			}
			assertNotEquals(-1, branchId);
			db.deleteBranch(branchId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminAddBranchTestQuit1() {
		LibraryConsole.adminAddBranch(new Scanner("quit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the branch (enter \'quit\' to quit):", lines[0]);
		assertEquals("Which table would you like to add to:", lines[1]);
		}
	
	@Test
	public void adminAddBranchTestQuit2() {
		LibraryConsole.adminAddBranch(new Scanner("New Branch\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the branch (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the branch (enter \'quit\' to quit):", lines[1]);
		assertEquals("Which table would you like to add to:", lines[2]);
	}
	
	@Test
	public void adminAddBranchTestYes() {
		LibraryConsole.adminAddBranch(new Scanner("New Branch\nNew Address\ny\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the branch (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the branch (enter \'quit\' to quit):", lines[1]);
		assertEquals("Would you like to add another branch (y):", lines[2]);
		assertEquals("What is the name of the branch (enter \'quit\' to quit):", lines[3]);
		
		Database db = new Database();
		try {
			db.connectToDB();
			int branchId = -1;
			List<Branch> branches = db.getBranches();
			for(Branch b : branches) {
				if("New Branch".equals(b.getBranchName()) && "New Address".equals(b.getBranchAddress())) {
					branchId = b.getBranchId();
				}
			}
			assertNotEquals(-1, branchId);
			db.deleteBranch(branchId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminAddBorrowerTest() {
		LibraryConsole.adminAddBorrower(new Scanner("New Borrower\nNew Address\nNew Phone\nno\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the borrower (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the borrower (enter \'quit\' to quit):", lines[1]);
		assertEquals("What is the phone number of the borrower (enter \'quit\' to quit):", lines[2]);
		assertEquals("Would you like to add another borrower (y):", lines[3]);
		assertEquals("Which table would you like to add to:", lines[4]);
		
		Database db = new Database();
		try {
			db.connectToDB();
			int cardNo = -1;
			List<Borrower> borrs = db.getBorrowers();
			for(Borrower b : borrs) {
				if("New Borrower".equals(b.getName()) && "New Address".equals(b.getAddress()) && "New Phone".equals(b.getPhone())) {
					cardNo = b.getCardNo();
				}
			}
			assertNotEquals(-1, cardNo);
			db.deleteBorrower(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminAddBorrowerTestQuit1() {
		LibraryConsole.adminAddBorrower(new Scanner("quit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the borrower (enter \'quit\' to quit):", lines[0]);
		assertEquals("Which table would you like to add to:", lines[1]);
	}
	
	@Test
	public void adminAddBorrowerTestQuit2() {
		LibraryConsole.adminAddBorrower(new Scanner("New Borrower\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the borrower (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the borrower (enter \'quit\' to quit):", lines[1]);
		assertEquals("Which table would you like to add to:", lines[2]);
	}
	
	@Test
	public void adminAddBorrowerTestQuit3() {
		LibraryConsole.adminAddBorrower(new Scanner("New Borrower\nNew Address\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the borrower (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the borrower (enter \'quit\' to quit):", lines[1]);
		assertEquals("What is the phone number of the borrower (enter \'quit\' to quit):", lines[2]);
		assertEquals("Which table would you like to add to:", lines[3]);
	}
	
	@Test
	public void adminAddBorrowerTestYes() {
		LibraryConsole.adminAddBorrower(new Scanner("New Borrower\nNew Address\nNew Phone\ny\nquit\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
				
		assertEquals("What is the name of the borrower (enter \'quit\' to quit):", lines[0]);
		assertEquals("What is the address of the borrower (enter \'quit\' to quit):", lines[1]);
		assertEquals("What is the phone number of the borrower (enter \'quit\' to quit):", lines[2]);
		assertEquals("Would you like to add another borrower (y):", lines[3]);
		assertEquals("What is the name of the borrower (enter \'quit\' to quit):", lines[4]);
		
		Database db = new Database();
		try {
			db.connectToDB();
			int cardNo = -1;
			List<Borrower> borrs = db.getBorrowers();
			for(Borrower b : borrs) {
				if("New Borrower".equals(b.getName()) && "New Address".equals(b.getAddress()) && "New Phone".equals(b.getPhone())) {
					cardNo = b.getCardNo();
				}
			}
			assertNotEquals(-1, cardNo);
			db.deleteBorrower(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	/**
	 * Test that adminUpdate prints out the correct output when the user inputs 1
	 */
	@Test
	public void adminUpdateTest1() {
		Database db = new Database();
		int numBooks = 0;
		try {
			db.connectToDB();
			numBooks = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminUpdate(new Scanner("1\n" + (numBooks+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to update:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Book you want to update:", lines[9]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 11]);
	}
	
	/**
	 * Test that adminUpdate prints out the correct output when the user inputs 2
	 */
	@Test
	public void adminUpdateTest2() {
		Database db = new Database();
		int numAuthors = 0;
		try {
			db.connectToDB();
			numAuthors = db.getAuthors().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminUpdate(new Scanner("2\n" + (numAuthors+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to update:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Author you want to update:", lines[9]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors + 11]);
	}
	
	/**
	 * Test that adminUpdate prints out the correct output when the user inputs 3
	 */
	@Test
	public void adminUpdateTest3() {
		Database db = new Database();
		int numPublishers = 0;
		try {
			db.connectToDB();
			numPublishers = db.getPublishers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminUpdate(new Scanner("3\n" + (numPublishers+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to update:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Publisher you want to update:", lines[9]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers + 11]);
		
		db.close();
	}
	
	/**
	 * Test that adminUpdate prints out the correct output when the user inputs 4
	 */
	@Test
	public void adminUpdateTest4() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminUpdate(new Scanner("4\n" + (numBranches+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to update:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Branch you want to update:", lines[9]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 11]);
		
		db.close();
	}
	
	/**
	 * Test that adminUpdate prints out the correct output when the user inputs 5
	 */
	@Test
	public void adminUpdateTest5() {
		Database db = new Database();
		int numBorrowers = 0;
		try {
			db.connectToDB();
			numBorrowers = db.getBorrowers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminUpdate(new Scanner("5\n" + (numBorrowers+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to update:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Borrower you want to update:", lines[9]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers + 11]);
		
		db.close();
	}
	
	/**
	 * Test that adminUpdate prints out the correct output when the user inputs 6
	 */
	@Test
	public void adminUpdateTest6() {
		LibraryConsole.adminUpdate(new Scanner("6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to update:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("What would you like to do:", lines[9]);
		assertEquals("\t1. Add Data", lines[11]);
		assertEquals("\t2. Update Data", lines[12]);
		assertEquals("\t3. Delete Data", lines[13]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[14]);
		assertEquals("\t5. Quit to Previous", lines[15]);
	}
	
	/**
	 * Test that adminUpdate prints out the correct output when the user inputs a non-integer
	 */
	@Test
	public void adminUpdateTestNotInt() {
		LibraryConsole.adminUpdate(new Scanner("Not an Integer\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to update:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("That is not a valid integer.", lines[9]);
		assertEquals("Which table would you like to update:", lines[10]);
		assertEquals("\t1. Books", lines[12]);
		assertEquals("\t2. Authors", lines[13]);
		assertEquals("\t3. Publishers", lines[14]);
		assertEquals("\t4. Library Branches", lines[15]);
		assertEquals("\t5. Borrowers", lines[16]);
		assertEquals("\t6. Quit to Previous", lines[17]);
	}
	
	/**
	 * Test that adminUpdate prints out the correct output when the user inputs an invalid integer
	 */
	@Test
	public void adminUpdateTestOther() {

		LibraryConsole.adminUpdate(new Scanner("7\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to update:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("7 is not a valid table choice.", lines[9]);
		assertEquals("Which table would you like to update:", lines[10]);
		assertEquals("\t1. Books", lines[12]);
		assertEquals("\t2. Authors", lines[13]);
		assertEquals("\t3. Publishers", lines[14]);
		assertEquals("\t4. Library Branches", lines[15]);
		assertEquals("\t5. Borrowers", lines[16]);
		assertEquals("\t6. Quit to Previous", lines[17]);
	}
	
	@Test
	public void adminUpdateBookTestBook() {
		Database db = new Database();
		int numBooks = 0;
		String bookName = "";
		String bookAuthor = "";
		String bookPublisher = "";
		try {
			db.connectToDB();
			List<Book> books = db.getBooks();
			Book b = books.get(0);
			numBooks = books.size();
			bookName = b.getTitle();
			bookAuthor = db.getAuthor(b.getAuthId()).getAuthorName();
			bookPublisher = db.getPublisher(b.getPubId()).getPublisherName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBook(new Scanner("1\nn/a\nn/a\nn/a\n" + (numBooks+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Book you want to update:", lines[0]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks+2]);
		assertEquals("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".", lines[numBooks+4]);
		assertEquals("Please enter a new title or enter N/A for no change:", lines[numBooks+6]);
		assertEquals("Please enter a new author or enter N/A for no change:", lines[numBooks+7]);
		assertEquals("Please enter a new publisher or enter N/A for no change:", lines[numBooks+8]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBookTestQuit() {
		Database db = new Database();
		int numBooks = 0;
		try {
			db.connectToDB();
			numBooks = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBook(new Scanner((numBooks+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Book you want to update:", lines[0]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks+2]);
		assertEquals("Which table would you like to update:", lines[numBooks+4]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBookTestNotInt() {
		Database db = new Database();
		int numBooks = 0;
		try {
			db.connectToDB();
			numBooks = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBook(new Scanner("Not an Integer\n" + (numBooks+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Book you want to update:", lines[0]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks+2]);
		assertEquals("That is not a valid integer.", lines[numBooks+4]);
		assertEquals("Pick the Book you want to update:", lines[numBooks+5]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks+numBooks+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBookTestOther() {
		Database db = new Database();
		int numBooks = 0;
		try {
			db.connectToDB();
			numBooks = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBook(new Scanner((numBooks+2) + "\n" + (numBooks+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Book you want to update:", lines[0]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks+2]);
		assertEquals("\'" + (numBooks+2) + "\' is not a valid book.", lines[numBooks+4]);
		assertEquals("Pick the Book you want to update:", lines[numBooks+5]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks+numBooks+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBookInputTestNoTitle() {
		Database db = new Database();
		int numBooks = 0;
		int bookId = -1;
		int authId = -1;
		int pubId = -1;
		String bookName = "";
		String bookAuthor = "";
		String bookPublisher = "";
		try {
			db.connectToDB();
			List<Book> books = db.getBooks();
			Book b = books.get(0);
			numBooks = books.size();
			bookName = b.getTitle();
			bookId = b.getBookId();
			Author a = db.getAuthor(b.getAuthId());
			bookAuthor = a.getAuthorName();
			authId = a.getAuthorId();
			Publisher p = db.getPublisher(b.getPubId());
			bookPublisher = p.getPublisherName();
			pubId = p.getPublisherId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBookInput(new Scanner("n/a\nAyn Rand\nViking Press\n" + (numBooks+1) + "\n6\n5\n4\n"), bookId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".", lines[0]);
		assertEquals("Please enter a new title or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new author or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new publisher or enter N/A for no change:", lines[4]);
		assertEquals("Pick the Book you want to update:", lines[5]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 7]);
		
		try {
			Book b = db.getBook(bookId);
			assertEquals(bookName, b.getTitle());
			assertEquals("Ayn Rand", db.getAuthor(b.getAuthId()).getAuthorName());
			assertEquals("Viking Press", db.getPublisher(b.getPubId()).getPublisherName());
			db.updateBook(bookId, bookName, authId, pubId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();		
	}
	
	@Test
	public void adminUpdateBookInputTestNoAuthor() {
		Database db = new Database();
		int numBooks = 0;
		int bookId = -1;
		int authId = -1;
		int pubId = -1;
		String bookName = "";
		String bookAuthor = "";
		String bookPublisher = "";
		try {
			db.connectToDB();
			List<Book> books = db.getBooks();
			Book b = books.get(0);
			numBooks = books.size();
			bookName = b.getTitle();
			bookId = b.getBookId();
			Author a = db.getAuthor(b.getAuthId());
			bookAuthor = a.getAuthorName();
			authId = a.getAuthorId();
			Publisher p = db.getPublisher(b.getPubId());
			bookPublisher = p.getPublisherName();
			pubId = p.getPublisherId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBookInput(new Scanner("New Title\nn/a\nViking Press\n" + (numBooks+1) + "\n6\n5\n4\n"), bookId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".", lines[0]);
		assertEquals("Please enter a new title or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new author or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new publisher or enter N/A for no change:", lines[4]);
		assertEquals("Pick the Book you want to update:", lines[5]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 7]);
		
		try {
			Book b = db.getBook(bookId);
			assertEquals("New Title", b.getTitle());
			assertEquals(bookAuthor, db.getAuthor(b.getAuthId()).getAuthorName());
			assertEquals("Viking Press", db.getPublisher(b.getPubId()).getPublisherName());
			db.updateBook(bookId, bookName, authId, pubId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdateBookInputTestNoPublisher() {
		Database db = new Database();
		int numBooks = 0;
		int bookId = -1;
		int authId = -1;
		int pubId = -1;
		String bookName = "";
		String bookAuthor = "";
		String bookPublisher = "";
		try {
			db.connectToDB();
			List<Book> books = db.getBooks();
			Book b = books.get(0);
			numBooks = books.size();
			bookName = b.getTitle();
			bookId = b.getBookId();
			Author a = db.getAuthor(b.getAuthId());
			bookAuthor = a.getAuthorName();
			authId = a.getAuthorId();
			Publisher p = db.getPublisher(b.getPubId());
			bookPublisher = p.getPublisherName();
			pubId = p.getPublisherId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBookInput(new Scanner("New Title\nAyn Rand\nn/a\n" + (numBooks+1) + "\n6\n5\n4\n"), bookId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".", lines[0]);
		assertEquals("Please enter a new title or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new author or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new publisher or enter N/A for no change:", lines[4]);
		assertEquals("Pick the Book you want to update:", lines[5]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 7]);
		
		try {
			Book b = db.getBook(bookId);
			assertEquals("New Title", b.getTitle());
			assertEquals("Ayn Rand", db.getAuthor(b.getAuthId()).getAuthorName());
			assertEquals(bookPublisher, db.getPublisher(b.getPubId()).getPublisherName());
			db.updateBook(bookId, bookName, authId, pubId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdateBookInputTestInvalidAuthor() {
		Database db = new Database();
		int numBooks = 0;
		int bookId = -1;
		String bookName = "";
		String bookAuthor = "";
		String bookPublisher = "";
		try {
			db.connectToDB();
			List<Book> books = db.getBooks();
			Book b = books.get(0);
			numBooks = books.size();
			bookName = b.getTitle();
			bookId = b.getBookId();
			Author a = db.getAuthor(b.getAuthId());
			bookAuthor = a.getAuthorName();
			Publisher p = db.getPublisher(b.getPubId());
			bookPublisher = p.getPublisherName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBookInput(new Scanner("n/a\n \nn/a\nn/a\nn/a\nn/a\n" + (numBooks+1) + "\n6\n5\n4\n"), bookId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".", lines[0]);
		assertEquals("Please enter a new title or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new author or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new publisher or enter N/A for no change:", lines[4]);
		assertEquals("Could not find an author with the name  ", lines[5]);
		assertEquals("Please input a different name or quit and add them to the author table first.", lines[6]);
		assertEquals("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".", lines[7]);
		assertEquals("Please enter a new title or enter N/A for no change:", lines[9]);
		assertEquals("Please enter a new author or enter N/A for no change:", lines[10]);
		assertEquals("Please enter a new publisher or enter N/A for no change:", lines[11]);
		
				
		db.close();	
	}
	
	@Test
	public void adminUpdateBookInputTestInvalidPublisher() {
		Database db = new Database();
		int numBooks = 0;
		int bookId = -1;
		String bookName = "";
		String bookAuthor = "";
		String bookPublisher = "";
		try {
			db.connectToDB();
			List<Book> books = db.getBooks();
			Book b = books.get(0);
			numBooks = books.size();
			bookName = b.getTitle();
			bookId = b.getBookId();
			Author a = db.getAuthor(b.getAuthId());
			bookAuthor = a.getAuthorName();
			Publisher p = db.getPublisher(b.getPubId());
			bookPublisher = p.getPublisherName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBookInput(new Scanner("n/a\nn/a\n \nn/a\nn/a\nn/a\n" + (numBooks+1) + "\n6\n5\n4\n"), bookId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".", lines[0]);
		assertEquals("Please enter a new title or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new author or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new publisher or enter N/A for no change:", lines[4]);
		assertEquals("Could not find a publisher with the name  ", lines[5]);
		assertEquals("Please input a different name or quit and add them to the publisher table first.", lines[6]);
		assertEquals("You have chosen to update the Book: " + bookName + " by " + bookAuthor + ", published by " + bookPublisher + ".", lines[7]);
		assertEquals("Please enter a new title or enter N/A for no change:", lines[9]);
		assertEquals("Please enter a new author or enter N/A for no change:", lines[10]);
		assertEquals("Please enter a new publisher or enter N/A for no change:", lines[11]);
		
		db.close();	
	}
	
	@Test
	public void adminUpdateAuthorTestAuthor() {
		Database db = new Database();
		int numAuthors = 0;
		String authorName = "";
		try {
			db.connectToDB();
			List<Author> authors = db.getAuthors();
			Author a = authors.get(0);
			numAuthors = authors.size();
			authorName = a.getAuthorName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateAuthor(new Scanner("1\nn/a\n" + (numAuthors+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Author you want to update:", lines[0]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors+2]);
		assertEquals("You have chosen to update the Author: " + authorName + ".", lines[numAuthors+4]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[numAuthors+6]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateAuthorTestQuit() {
		Database db = new Database();
		int numAuthors = 0;
		try {
			db.connectToDB();
			numAuthors = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateAuthor(new Scanner((numAuthors+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Author you want to update:", lines[0]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors+2]);
		assertEquals("Which table would you like to update:", lines[numAuthors+4]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateAuthorTestNotInt() {
		Database db = new Database();
		int numAuthors = 0;
		try {
			db.connectToDB();
			numAuthors = db.getAuthors().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateAuthor(new Scanner("Not an Integer\n" + (numAuthors+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Author you want to update:", lines[0]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors+2]);
		assertEquals("That is not a valid integer.", lines[numAuthors+4]);
		assertEquals("Pick the Author you want to update:", lines[numAuthors+5]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors+numAuthors+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateAuthorTestOther() {
		Database db = new Database();
		int numAuthors = 0;
		try {
			db.connectToDB();
			numAuthors = db.getAuthors().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateAuthor(new Scanner((numAuthors+2) + "\n" + (numAuthors+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Author you want to update:", lines[0]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors+2]);
		assertEquals("\'" + (numAuthors+2) + "\' is not a valid author.", lines[numAuthors+4]);
		assertEquals("Pick the Author you want to update:", lines[numAuthors+5]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors+numAuthors+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateAuthorInputTest() {
		Database db = new Database();
		int numAuthors = 0;
		int authId = -1;
		String authorName = "";
		try {
			db.connectToDB();
			List<Author> authors = db.getAuthors();
			Author a = authors.get(0);
			numAuthors = authors.size();
			authorName = a.getAuthorName();
			authId = a.getAuthorId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateAuthorInput(new Scanner("New Author\n" + (numAuthors+1) + "\n6\n5\n4\n"), authId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Author: " + authorName + ".", lines[0]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[2]);
		assertEquals("Pick the Author you want to update:", lines[3]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors + 5]);
		
		try {
			Author a = db.getAuthor(authId);
			assertEquals("New Author", a.getAuthorName());
			db.updateAuthor(authId, authorName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdateAuthorInputTestNo() {
		Database db = new Database();
		int numAuthors = 0;
		int authId = -1;
		String authorName = "";
		try {
			db.connectToDB();
			List<Author> authors = db.getAuthors();
			Author a = authors.get(0);
			numAuthors = authors.size();
			authorName = a.getAuthorName();
			authId = a.getAuthorId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateAuthorInput(new Scanner("n/a\n" + (numAuthors+1) + "\n6\n5\n4\n"), authId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Author: " + authorName + ".", lines[0]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[2]);
		assertEquals("Pick the Author you want to update:", lines[3]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors + 5]);
		
		try {
			Author a = db.getAuthor(authId);
			assertEquals(authorName, a.getAuthorName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdatePublisherTestPublisher() {
		Database db = new Database();
		int numPublishers = 0;
		String PublisherName = "";
		String PublisherAddress = "";
		String PublisherPhone = "";
		try {
			db.connectToDB();
			List<Publisher> pubs = db.getPublishers();
			Publisher p = pubs.get(0);
			numPublishers = pubs.size();
			PublisherName = p.getPublisherName();
			PublisherAddress = p.getPublisherAddress();
			PublisherPhone = p.getPublisherPhone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdatePublisher(new Scanner("1\nn/a\nn/a\nn/a\n" + (numPublishers+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Publisher you want to update:", lines[0]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers+2]);
		assertEquals("You have chosen to update the Publisher: " + PublisherName + ", " + PublisherAddress + " - " + PublisherPhone + ".", lines[numPublishers+4]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[numPublishers+6]);
		assertEquals("Please enter a new address or enter N/A for no change:", lines[numPublishers+7]);
		assertEquals("Please enter a new phone number or enter N/A for no change:", lines[numPublishers+8]);
		
		db.close();
	}
	
	@Test
	public void adminUpdatePublisherTestQuit() {
		Database db = new Database();
		int numPubs = 0;
		try {
			db.connectToDB();
			numPubs = db.getPublishers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdatePublisher(new Scanner((numPubs+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Publisher you want to update:", lines[0]);
		assertEquals("\t" + (numPubs+1) + ". Quit to previous", lines[numPubs+2]);
		assertEquals("Which table would you like to update:", lines[numPubs+4]);
		
		db.close();
	}
	
	@Test
	public void adminUpdatePublisherTestNotInt() {
		Database db = new Database();
		int numPublishers = 0;
		try {
			db.connectToDB();
			numPublishers = db.getPublishers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdatePublisher(new Scanner("Not an Integer\n" + (numPublishers+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Publisher you want to update:", lines[0]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers+2]);
		assertEquals("That is not a valid integer.", lines[numPublishers+4]);
		assertEquals("Pick the Publisher you want to update:", lines[numPublishers+5]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers+numPublishers+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdatePublisherTestOther() {
		Database db = new Database();
		int numPublishers = 0;
		try {
			db.connectToDB();
			numPublishers = db.getPublishers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdatePublisher(new Scanner((numPublishers+2) + "\n" + (numPublishers+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Publisher you want to update:", lines[0]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers+2]);
		assertEquals("\'" + (numPublishers+2) + "\' is not a valid publisher.", lines[numPublishers+4]);
		assertEquals("Pick the Publisher you want to update:", lines[numPublishers+5]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers+numPublishers+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdatePublisherInputTestNoName() {
		Database db = new Database();
		int numPublishers = 0;
		int pubId = -1;
		String publisherName = "";
		String publisherAddress = "";
		String publisherPhone = "";
		try {
			db.connectToDB();
			List<Publisher> pubs = db.getPublishers();
			Publisher p = pubs.get(0);
			numPublishers = pubs.size();
			publisherName = p.getPublisherName();
			publisherAddress = p.getPublisherAddress();
			publisherPhone = p.getPublisherPhone();
			pubId = p.getPublisherId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdatePublisherInput(new Scanner("n/a\nNew Address\nNew Phone\n" + (numPublishers+1) + "\n6\n5\n4\n"), pubId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Publisher: " + publisherName + ", " + publisherAddress + " - " + publisherPhone + ".", lines[0]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new address or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new phone number or enter N/A for no change:", lines[4]);
		assertEquals("Pick the Publisher you want to update:", lines[5]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers + 7]);
		
		try {
			Publisher p = db.getPublisher(pubId);
			assertEquals(publisherName, p.getPublisherName());
			assertEquals("New Address", p.getPublisherAddress());
			assertEquals("New Phone", p.getPublisherPhone());
			db.updatePublisher(pubId, publisherName, publisherAddress, publisherPhone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdatePublisherInputTestNoAddress() {
		Database db = new Database();
		int numPublishers = 0;
		int pubId = -1;
		String publisherName = "";
		String publisherAddress = "";
		String publisherPhone = "";
		try {
			db.connectToDB();
			List<Publisher> pubs = db.getPublishers();
			Publisher p = pubs.get(0);
			numPublishers = pubs.size();
			publisherName = p.getPublisherName();
			publisherAddress = p.getPublisherAddress();
			publisherPhone = p.getPublisherPhone();
			pubId = p.getPublisherId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdatePublisherInput(new Scanner("New Publisher\nn/a\nNew Phone\n" + (numPublishers+1) + "\n6\n5\n4\n"), pubId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Publisher: " + publisherName + ", " + publisherAddress + " - " + publisherPhone + ".", lines[0]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new address or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new phone number or enter N/A for no change:", lines[4]);
		assertEquals("Pick the Publisher you want to update:", lines[5]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers + 7]);
		
		try {
			Publisher p = db.getPublisher(pubId);
			assertEquals("New Publisher", p.getPublisherName());
			assertEquals(publisherAddress, p.getPublisherAddress());
			assertEquals("New Phone", p.getPublisherPhone());
			db.updatePublisher(pubId, publisherName, publisherAddress, publisherPhone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdatePublisherInputTestNoPhone() {
		Database db = new Database();
		int numPublishers = 0;
		int pubId = -1;
		String publisherName = "";
		String publisherAddress = "";
		String publisherPhone = "";
		try {
			db.connectToDB();
			List<Publisher> pubs = db.getPublishers();
			Publisher p = pubs.get(0);
			numPublishers = pubs.size();
			publisherName = p.getPublisherName();
			publisherAddress = p.getPublisherAddress();
			publisherPhone = p.getPublisherPhone();
			pubId = p.getPublisherId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdatePublisherInput(new Scanner("New Publisher\nNew Address\nn/a\n" + (numPublishers+1) + "\n6\n5\n4\n"), pubId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Publisher: " + publisherName + ", " + publisherAddress + " - " + publisherPhone + ".", lines[0]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new address or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new phone number or enter N/A for no change:", lines[4]);
		assertEquals("Pick the Publisher you want to update:", lines[5]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers + 7]);
		
		try {
			Publisher p = db.getPublisher(pubId);
			assertEquals("New Publisher", p.getPublisherName());
			assertEquals("New Address", p.getPublisherAddress());
			assertEquals(publisherPhone, p.getPublisherPhone());
			db.updatePublisher(pubId, publisherName, publisherAddress, publisherPhone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdateBranchTestBranch() {
		Database db = new Database();
		int numBranches = 0;
		String BranchName = "";
		String BranchAddress = "";
		try {
			db.connectToDB();
			List<Branch> branches = db.getBranches();
			Branch b = branches.get(0);
			numBranches = branches.size();
			BranchName = b.getBranchName();
			BranchAddress = b.getBranchAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBranch(new Scanner("1\nn/a\nn/a\n" + (numBranches+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Branch you want to update:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches+2]);
		assertEquals("You have chosen to update the Branch: " + BranchName + ", " + BranchAddress + ".", lines[numBranches+4]);
		assertEquals("Please enter a new branch name or enter N/A for no change:", lines[numBranches+6]);
		assertEquals("Please enter a new branch address or enter N/A for no change:", lines[numBranches+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBranchTestQuit() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBranch(new Scanner((numBranches+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Branch you want to update:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches+2]);
		assertEquals("Which table would you like to update:", lines[numBranches+4]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBranchTestNotInt() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBranch(new Scanner("Not an Integer\n" + (numBranches+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Branch you want to update:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches+2]);
		assertEquals("That is not a valid integer.", lines[numBranches+4]);
		assertEquals("Pick the Branch you want to update:", lines[numBranches+5]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches+numBranches+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBranchTestOther() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBranch(new Scanner((numBranches+2) + "\n" + (numBranches+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Branch you want to update:", lines[0]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches+2]);
		assertEquals("\'" + (numBranches+2) + "\' is not a valid branch.", lines[numBranches+4]);
		assertEquals("Pick the Branch you want to update:", lines[numBranches+5]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches+numBranches+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBranchInputTestNoName() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		String branchName = "";
		String branchAddress = "";
		try {
			db.connectToDB();
			List<Branch> branches = db.getBranches();
			Branch b = branches.get(0);
			numBranches = branches.size();
			branchName = b.getBranchName();
			branchAddress = b.getBranchAddress();
			branchId = b.getBranchId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBranchInput(new Scanner("n/a\nNew Address\n" + (numBranches+1) + "\n6\n5\n4\n"), branchId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Branch: " + branchName + ", " + branchAddress + ".", lines[0]);
		assertEquals("Please enter a new branch name or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new branch address or enter N/A for no change:", lines[3]);
		assertEquals("Pick the Branch you want to update:", lines[4]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 6]);
		
		try {
			Branch p = db.getBranch(branchId);
			assertEquals(branchName, p.getBranchName());
			assertEquals("New Address", p.getBranchAddress());
			db.updateBranch(branchId, branchName, branchAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdateBranchInputTestNoAddress() {
		Database db = new Database();
		int numBranches = 0;
		int branchId = -1;
		String branchName = "";
		String branchAddress = "";
		try {
			db.connectToDB();
			List<Branch> branches = db.getBranches();
			Branch b = branches.get(0);
			numBranches = branches.size();
			branchName = b.getBranchName();
			branchAddress = b.getBranchAddress();
			branchId = b.getBranchId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBranchInput(new Scanner("New Branch\nn/a\n" + (numBranches+1) + "\n6\n5\n4\n"), branchId);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Branch: " + branchName + ", " + branchAddress + ".", lines[0]);
		assertEquals("Please enter a new branch name or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new branch address or enter N/A for no change:", lines[3]);
		assertEquals("Pick the Branch you want to update:", lines[4]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 6]);
		
		try {
			Branch p = db.getBranch(branchId);
			assertEquals("New Branch", p.getBranchName());
			assertEquals(branchAddress, p.getBranchAddress());
			db.updateBranch(branchId, branchName, branchAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdateBorrowerTestBorrower() {
		Database db = new Database();
		int numBorrowers = 0;
		String BorrowerName = "";
		String BorrowerAddress = "";
		String BorrowerPhone = "";
		try {
			db.connectToDB();
			List<Borrower> borrs = db.getBorrowers();
			Borrower b = borrs.get(0);
			numBorrowers = borrs.size();
			BorrowerName = b.getName();
			BorrowerAddress = b.getAddress();
			BorrowerPhone = b.getPhone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBorrower(new Scanner("1\nn/a\nn/a\nn/a\n" + (numBorrowers+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Borrower you want to update:", lines[0]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers+2]);
		assertEquals("You have chosen to update the Borrower: " + BorrowerName + ", " + BorrowerAddress + " - " + BorrowerPhone + ".", lines[numBorrowers+4]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[numBorrowers+6]);
		assertEquals("Please enter a new address or enter N/A for no change:", lines[numBorrowers+7]);
		assertEquals("Please enter a new phone number or enter N/A for no change:", lines[numBorrowers+8]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBorrowerTestQuit() {
		Database db = new Database();
		int numBorrs = 0;
		try {
			db.connectToDB();
			numBorrs = db.getBorrowers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBorrower(new Scanner((numBorrs+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Borrower you want to update:", lines[0]);
		assertEquals("\t" + (numBorrs+1) + ". Quit to previous", lines[numBorrs+2]);
		assertEquals("Which table would you like to update:", lines[numBorrs+4]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBorrowerTestNotInt() {
		Database db = new Database();
		int numBorrowers = 0;
		try {
			db.connectToDB();
			numBorrowers = db.getBorrowers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBorrower(new Scanner("Not an Integer\n" + (numBorrowers+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Borrower you want to update:", lines[0]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers+2]);
		assertEquals("That is not a valid integer.", lines[numBorrowers+4]);
		assertEquals("Pick the Borrower you want to update:", lines[numBorrowers+5]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers+numBorrowers+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBorrowerTestOther() {
		Database db = new Database();
		int numBorrowers = 0;
		try {
			db.connectToDB();
			numBorrowers = db.getBorrowers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBorrower(new Scanner((numBorrowers+2) + "\n" + (numBorrowers+1) + "\n6\n5\n4\n"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Borrower you want to update:", lines[0]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers+2]);
		assertEquals("\'" + (numBorrowers+2) + "\' is not a valid borrower.", lines[numBorrowers+4]);
		assertEquals("Pick the Borrower you want to update:", lines[numBorrowers+5]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers+numBorrowers+7]);
		
		db.close();
	}
	
	@Test
	public void adminUpdateBorrowerInputTestNoName() {
		Database db = new Database();
		int numBorrowers = 0;
		int cardNo = -1;
		String borrowerName = "";
		String borrowerAddress = "";
		String borrowerPhone = "";
		try {
			db.connectToDB();
			List<Borrower> borrs = db.getBorrowers();
			Borrower b = borrs.get(0);
			numBorrowers = borrs.size();
			borrowerName = b.getName();
			borrowerAddress = b.getAddress();
			borrowerPhone = b.getPhone();
			cardNo = b.getCardNo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBorrowerInput(new Scanner("n/a\nNew Address\nNew Phone\n" + (numBorrowers+1) + "\n6\n5\n4\n"), cardNo);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Borrower: " + borrowerName + ", " + borrowerAddress + " - " + borrowerPhone + ".", lines[0]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new address or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new phone number or enter N/A for no change:", lines[4]);
		assertEquals("Pick the Borrower you want to update:", lines[5]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers + 7]);
		
		try {
			Borrower b = db.getBorrower(cardNo);
			assertEquals(borrowerName, b.getName());
			assertEquals("New Address", b.getAddress());
			assertEquals("New Phone", b.getPhone());
			db.updateBorrower(cardNo, borrowerName, borrowerAddress, borrowerPhone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdateBorrowerInputTestNoAddress() {
		Database db = new Database();
		int numBorrowers = 0;
		int cardNo = -1;
		String borrowerName = "";
		String borrowerAddress = "";
		String borrowerPhone = "";
		try {
			db.connectToDB();
			List<Borrower> borrs = db.getBorrowers();
			Borrower b = borrs.get(0);
			numBorrowers = borrs.size();
			borrowerName = b.getName();
			borrowerAddress = b.getAddress();
			borrowerPhone = b.getPhone();
			cardNo = b.getCardNo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBorrowerInput(new Scanner("New Borrower\nn/a\nNew Phone\n" + (numBorrowers+1) + "\n6\n5\n4\n"), cardNo);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Borrower: " + borrowerName + ", " + borrowerAddress + " - " + borrowerPhone + ".", lines[0]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new address or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new phone number or enter N/A for no change:", lines[4]);
		assertEquals("Pick the Borrower you want to update:", lines[5]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers + 7]);
		
		try {
			Borrower b = db.getBorrower(cardNo);
			assertEquals(borrowerAddress, b.getAddress());
			assertEquals("New Phone", b.getPhone());
			db.updateBorrower(cardNo, borrowerName, borrowerAddress, borrowerPhone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	@Test
	public void adminUpdateBorroewrInputTestNoPhone() {
		Database db = new Database();
		int numBorrowers = 0;
		int cardNo = -1;
		String borrowerName = "";
		String borrowerAddress = "";
		String borrowerPhone = "";
		try {
			db.connectToDB();
			List<Borrower> borrs = db.getBorrowers();
			Borrower b = borrs.get(0);
			numBorrowers = borrs.size();
			borrowerName = b.getName();
			borrowerAddress = b.getAddress();
			borrowerPhone = b.getPhone();
			cardNo = b.getCardNo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminUpdateBorrowerInput(new Scanner("New Borrower\nNew Address\nn/a\n" + (numBorrowers+1) + "\n6\n5\n4\n"), cardNo);
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("You have chosen to update the Borrower: " + borrowerName + ", " + borrowerAddress + " - " + borrowerPhone + ".", lines[0]);
		assertEquals("Please enter a new name or enter N/A for no change:", lines[2]);
		assertEquals("Please enter a new address or enter N/A for no change:", lines[3]);
		assertEquals("Please enter a new phone number or enter N/A for no change:", lines[4]);
		assertEquals("Pick the Borrower you want to update:", lines[5]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers + 7]);
		
		try {
			Borrower b = db.getBorrower(cardNo);
			assertEquals("New Borrower", b.getName());
			assertEquals("New Address", b.getAddress());
			assertEquals(borrowerPhone, b.getPhone());
			db.updateBorrower(cardNo, borrowerName, borrowerAddress, borrowerPhone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	/**
	 * Test that adminDelete prints out the correct output when the user inputs 1
	 */
	@Test
	public void adminDeleteTest1() {
		Database db = new Database();
		int numBooks = 0;
		try {
			db.connectToDB();
			numBooks = db.getBooks().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminDelete(new Scanner("1\n" + (numBooks+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to delete from:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Book you want to delete:", lines[9]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks + 11]);
		
		db.close();
	}
	
	/**
	 * Test that adminDelete prints out the correct output when the user inputs 2
	 */
	@Test
	public void adminDeleteTest2() {
		Database db = new Database();
		int numAuthors = 0;
		try {
			db.connectToDB();
			numAuthors = db.getAuthors().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminDelete(new Scanner("2\n" + (numAuthors+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to delete from:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Author you want to delete:", lines[9]);
		assertEquals("\t" + (numAuthors+1) + ". Quit to previous", lines[numAuthors + 11]);
		
		db.close();
	}
	
	/**
	 * Test that adminDelete prints out the correct output when the user inputs 3
	 */
	@Test
	public void adminDeleteTest3() {
		Database db = new Database();
		int numPublishers = 0;
		try {
			db.connectToDB();
			numPublishers = db.getPublishers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminDelete(new Scanner("3\n" + (numPublishers+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to delete from:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Publisher you want to delete:", lines[9]);
		assertEquals("\t" + (numPublishers+1) + ". Quit to previous", lines[numPublishers + 11]);
		
		db.close();
	}
	
	/**
	 * Test that adminDelete prints out the correct output when the user inputs 4
	 */
	@Test
	public void adminDeleteTest4() {
		Database db = new Database();
		int numBranches = 0;
		try {
			db.connectToDB();
			numBranches = db.getBranches().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminDelete(new Scanner("4\n" + (numBranches+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to delete from:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Branch you want to delete:", lines[9]);
		assertEquals("\t" + (numBranches+1) + ". Quit to previous", lines[numBranches + 11]);
		
		db.close();
	}
	
	/**
	 * Test that adminDelete prints out the correct output when the user inputs 5
	 */
	@Test
	public void adminDeleteTest5() {
		Database db = new Database();
		int numBorrowers = 0;
		try {
			db.connectToDB();
			numBorrowers = db.getBorrowers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LibraryConsole.adminDelete(new Scanner("5\n" + (numBorrowers+1) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to delete from:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("Pick the Borrower you want to delete:", lines[9]);
		assertEquals("\t" + (numBorrowers+1) + ". Quit to previous", lines[numBorrowers + 11]);
		
		db.close();
	}
	
	/**
	 * Test that adminDelete prints out the correct output when the user inputs 6
	 */
	@Test
	public void adminDeleteTest6() {
		LibraryConsole.adminDelete(new Scanner("6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to delete from:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("What would you like to do:", lines[9]);
		assertEquals("\t1. Add Data", lines[11]);
		assertEquals("\t2. Update Data", lines[12]);
		assertEquals("\t3. Delete Data", lines[13]);
		assertEquals("\t4. Override the Due Date for a Book Loan", lines[14]);
		assertEquals("\t5. Quit to Previous", lines[15]);
	}
	
	/**
	 * Test that adminDelete prints out the correct output when the user inputs a non-integer
	 */
	@Test
	public void adminDeleteTestNotInt() {
		LibraryConsole.adminDelete(new Scanner("Not an Integer\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to delete from:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("That is not a valid integer.", lines[9]);
		assertEquals("Which table would you like to delete from:", lines[10]);
		assertEquals("\t1. Books", lines[12]);
		assertEquals("\t2. Authors", lines[13]);
		assertEquals("\t3. Publishers", lines[14]);
		assertEquals("\t4. Library Branches", lines[15]);
		assertEquals("\t5. Borrowers", lines[16]);
		assertEquals("\t6. Quit to Previous", lines[17]);
	}
	
	/**
	 * Test that adminDelete prints out the correct output when the user inputs an invalid integer
	 */
	@Test
	public void adminDeleteTestOther() {
		LibraryConsole.adminDelete(new Scanner("7\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Which table would you like to delete from:", lines[0]);
		assertEquals("\t1. Books", lines[2]);
		assertEquals("\t2. Authors", lines[3]);
		assertEquals("\t3. Publishers", lines[4]);
		assertEquals("\t4. Library Branches", lines[5]);
		assertEquals("\t5. Borrowers", lines[6]);
		assertEquals("\t6. Quit to Previous", lines[7]);
		assertEquals("7 is not a valid table choice.", lines[9]);
		assertEquals("Which table would you like to delete from:", lines[10]);
		assertEquals("\t1. Books", lines[12]);
		assertEquals("\t2. Authors", lines[13]);
		assertEquals("\t3. Publishers", lines[14]);
		assertEquals("\t4. Library Branches", lines[15]);
		assertEquals("\t5. Borrowers", lines[16]);
		assertEquals("\t6. Quit to Previous", lines[17]);
	}
 
	/* Ran into issues implementing the delete tests since they would temporarily delete the books before I re-added them, 
	 * which would also remove entries from bookCopies and bookLoans - Can fix but didn't have enough time
	@Test        
	public void adminDeleteBookTestBook() {
		Database db = new Database();
		int numBooks = 0;
		int bookId = -1;
		int authId = -1;
		int pubId = -1;
		String bookName = "";
		try {
			db.connectToDB();
			List<Book> books = db.getBooks();
			Book b = books.get(0);
			numBooks = books.size();
			bookId = b.getBookId();
			bookName = b.getTitle();
			authId = b.getAuthId();
			pubId = b.getPubId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LibraryConsole.adminDeleteBook(new Scanner("1\n" + (numBooks) + "\n6\n5\n4"));
		String lines[] = systemOutput.toString().split("\r\n");	
		
		assertEquals("Pick the Book you want to delete:", lines[0]);
		assertEquals("\t" + (numBooks+1) + ". Quit to previous", lines[numBooks+2]);
		assertEquals("Pick the Book you want to delete:", lines[numBooks+4]);
		assertEquals("\t" + (numBooks) + ". Quit to previous", lines[numBooks+numBooks+5]);
		
		try {
			assertEquals(null, db.getBook(bookId));
			db.addBook(bookName, authId, pubId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		db.close();
	}
	
	@Test
	public void adminDeleteBookTestQuit() {
		
	}
	
	@Test
	public void adminDeleteBookTestNotInt() {
		
	}
	
	@Test
	public void adminDeleteBookTestOther() {
		
	}
	
	@Test
	public void adminDeleteAuthorTestAuthor() {
		
	}
	
	@Test
	public void adminDeleteAuthorTestQuit() {
		
	}
	
	@Test
	public void adminDeleteAuthorTestNotInt() {
		
	}
	
	@Test
	public void adminDeleteAuthorTestOther() {
		
	}
	
	@Test
	public void adminDeletePublisherTestPublisher() {
		
	}
	
	@Test
	public void adminDeletePublisherTestQuit() {
		
	}
	
	@Test
	public void adminDeletePublisherTestNotInt() {
		
	}
	
	@Test
	public void adminDeletePublisherTestOther() {
		
	}
	
	@Test
	public void adminDeleteBranchTestBranch() {
		
	}
	
	@Test
	public void adminDeleteBranchTestQuit() {
		
	}
	
	@Test
	public void adminDeleteBranchTestNotInt() {
		
	}
	
	@Test
	public void adminDeleteBranchTestOther() {
		
	}
	
	@Test
	public void adminDeleteBorrowerTestBorrower() {
		
	}
	
	@Test
	public void adminDeleteBorrowerTestQuit() {
		
	}
	
	@Test
	public void adminDeleteBorrowerTestNotInt() {
		
	}
	
	@Test
	public void adminDeleteBorrowerTestOther() {
		
	}*/	
}
