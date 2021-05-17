/**
 * Adapted from http://www.science.smith.edu/dftwiki/index.php/Tutorial:_Accessing_a_MySql_database_in_Java_(Eclipse)
 */
package com.ss.course.jb.capstone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * An interface between our app and the mySQL database
 * @author Eric Colvin
 *
 */
public class Database {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	final private String host = "127.0.0.1:3306";
	final private String user = "javaUser";
	final private String passwd = "Java_Basics_Pa$$word";
	final private String database = "library";
	
	/**
	 * Startup function to initialize the database
	 * @throws Exception - Throws an exception if something goes wrong initializing the database
	 */
	public int connectToDB() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?" + "user=" + user + "&password=" + passwd);
			if(connect != null) {
				return 0;
			} else {
				return -1;
			}
		} catch (Exception e) {
			throw e;
		}		
	}
	
	/**
	 * Retrieves the query of all entries in tbl_book and turns it into an ArrayList of Books and returns it
	 * @return - ArrayList of Book objects retrieved from the database
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public List<Book> getBooks() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_book order by bookId");
			List<Book> books = new ArrayList<Book>();
			while(resultSet.next()) {
				int id = resultSet.getInt("bookId");
				String title = resultSet.getString("title");
				int authId = resultSet.getInt("authId");
				int pubId = resultSet.getInt("pubId");				
				books.add(new Book(id, title, authId, pubId));
			}
			return books;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Queries the database for a book with the specified bookId and returns it
	 * @param bookId - The bookId for the query
	 * @return - The queried book or null if no book was found
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public Book getBook(int bookId) throws Exception{
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_book where bookId=" + bookId);
			if(resultSet.next()) {
				int id = resultSet.getInt("bookId");
				String title = resultSet.getString("title");
				int authId = resultSet.getInt("authId");
				int pubId = resultSet.getInt("pubId");	
				
				return new Book(id, title, authId, pubId);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Adds an entry to the tbl_book table with the specified values and a bookId one larger than the current max bookId
	 * @param title - The title of the new book
	 * @param authId - The Foreign Key for the author of the new book
	 * @param pubId - The Foreign Key for the publisher of the new book
	 * @throws Exception - Throws an exception if something goes wrong with the query or insert into statement
	 * @return - Returns the bookId of the newly created entry
	 */
	public int addBook(String title, int authId, int pubId) throws Exception {
		title = escapeBadChars(title);
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(
					  "Select MAX(bookId) "
					+ "From " + database + ".tbl_book"					
			);
			int bookId = 1;
			if(resultSet.next()) {
				bookId = resultSet.getInt("MAX(bookId)") + 1;
			}
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Insert Into " + database + ".tbl_book (bookId, title, authId, pubId) "
					+ "Values (" + bookId + ", \'" + title + "\', " + authId + ", " + pubId + ")" 
			);
			return bookId;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Updates the specified entry in the tbl_book table to the specified values
	 * @param bookId - The Primary Key of the entry to be updated
	 * @param title - The new title of the book entry
	 * @param authId - The Foreign Key for the new author of the book entry
	 * @param pubId - The Foreign Key for the new publisher of the book entry
	 * @return - Returns -1 if either the author or publisher could not be found in the database or 0 otherwise
	 * @throws Exception - Throws an exception if something goes wrong with the update statement
	 */
	public int updateBook(int bookId, String title, int authId, int pubId) throws Exception {
		title = escapeBadChars(title);
		try {
			if(getAuthor(authId) == null || getPublisher(pubId) == null) {
				return -1;
			}	
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Update " + database + ".tbl_book "
					+ "Set title=\'" + title + "\', authId=" + authId + ", pubId=" + pubId + " "
					+ "Where bookId=" + bookId
			);
			return 0;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Deletes the specified entry from the tbl_book table
	 * @param bookId - The Primary Key of the entry to be deleted
	 * @throws Exception - Throws an exception if something goes wrong with the delete statement
	 */
	public void deleteBook(int bookId) throws Exception {
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Delete From "+ database + ".tbl_book "
					+ "Where bookId=" + bookId
			);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Retrieves the query of all entries in tbl_author and turns it into an ArrayList of Authors and returns it
	 * @return - ArrayList of Author objects retrieved from the database
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public List<Author> getAuthors() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_author order by authorId");
			List<Author> authors = new ArrayList<Author>();
			while(resultSet.next()) {
				int id = resultSet.getInt("authorId");
				String name = resultSet.getString("authorName");
				authors.add(new Author(id, name));
			}
			return authors;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Queries the database for an author with the specified authorId and returns it
	 * @param authorId - The authorId for the query
	 * @return - The queried author or null if no author was found
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public Author getAuthor(int authorId) throws Exception{
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_author where authorId=" + authorId);
			if(resultSet.next()) {
				int id = resultSet.getInt("authorId");
				String name = resultSet.getString("authorName");
				return new Author(id, name);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Queries the database for an author with the specified name and returns it
	 * @param authorName - The authorName for the query
	 * @return - The queried author or null if no author was found
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public Author getAuthor(String authorName) throws Exception{
		authorName = escapeBadChars(authorName);
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_author where authorName=\'" + authorName + "\'");
			if(resultSet.next()) {
				int id = resultSet.getInt("authorId");
				String name = resultSet.getString("authorName");
				return new Author(id, name);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Adds an entry to the tbl_author table with the specified values and an authorId one larger than the current max authorId
	 * @param name - The name of the new author
	 * @throws Exception - Throws an exception if something goes wrong with the query or insert into statement
	 * @return - Returns the authorId of the newly created entry
	 */
	public int addAuthor(String name) throws Exception {
		name = escapeBadChars(name);
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(
					  "Select MAX(authorId) "
					+ "From " + database + ".tbl_author"					
			);
			int authId = 1;
			if(resultSet.next()) {
				authId = resultSet.getInt("MAX(authorId)") + 1;
			}
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Insert Into " + database + ".tbl_author (authorId, authorName) "
					+ "Values (" + authId + ", \'" + name + "\')" 
			);
			return authId;
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Updates the specified entry in the tbl_author table to the specified values
	 * @param authorId - The Primary Key of the entry to be updated
	 * @param name - The new name of the author entry
	 * @throws Exception - Throws an exception if something goes wrong with the update statement
	 */
	public void updateAuthor(int authorId, String name) throws Exception {
		name = escapeBadChars(name);
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Update " + database + ".tbl_author "
					+ "Set authorName=\'" + name  + "\' "
					+ "Where authorId=" + authorId
			);			
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Deletes the specified entry from the tbl_author table
	 * @param authorId - The Primary Key of the entry to be deleted
	 * @throws Exception - Throws an exception if something goes wrong with the delete statement
	 */
	public void deleteAuthor(int authorId) throws Exception {
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Delete From "+ database + ".tbl_author "
					+ "Where authorId=" + authorId
			);
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Retrieves the query of all entries in tbl_publisher and turns it into an ArrayList of Publishers and returns it
	 * @return - ArrayList of Publisher objects retrieved from the database
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public List<Publisher> getPublishers() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_publisher order by publisherId");
			List<Publisher> publishers = new ArrayList<Publisher>();
			while(resultSet.next()) {
				int id = resultSet.getInt("publisherId");
				String name = resultSet.getString("publisherName");
				String address = resultSet.getString("publisherAddress");
				String phone = resultSet.getString("publisherPhone");
				publishers.add(new Publisher(id, name, address, phone));
			}
			return publishers;
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Queries the database for a publisher with the specified publisherId and returns it
	 * @param publisherId - The publisherId for the query
	 * @return - The queried publisher or null if no publisher was found
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public Publisher getPublisher(int publisherId) throws Exception{
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_publisher where publisherId=" + publisherId);
			if(resultSet.next()) {
				int id = resultSet.getInt("publisherId");
				String name = resultSet.getString("publisherName");
				String address = resultSet.getString("publisherAddress");
				String phone = resultSet.getString("publisherPhone");
				return new Publisher(id, name, address, phone);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Queries the database for a publisher with the specified name and returns it
	 * @param publisherName - The publisherName for the query
	 * @return - The queried publisher or null if no publisher was found
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public Publisher getPublisher(String publisherName) throws Exception{
		publisherName = escapeBadChars(publisherName);
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_publisher where publisherName=\'" + publisherName + "\'");
			if(resultSet.next()) {
				int id = resultSet.getInt("publisherId");
				String name = resultSet.getString("publisherName");
				String address = resultSet.getString("publisherAddress");
				String phone = resultSet.getString("publisherPhone");
				return new Publisher(id, name, address, phone);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Adds an entry to the tbl_publisher table with the specified values and a publisherId one larger than the current max publisherId
	 * @param name - The name of the new publisher
	 * @param address - The location of the new publisher
	 * @param phone - The contact info of the new publisher
	 * @throws Exception - Throws an exception if something goes wrong with the query or insert into statement
	 * @return - Returns the pubId of the newly created entry
	 */
	public int addPublisher(String name, String address, String phone) throws Exception {
		name = escapeBadChars(name);
		address = escapeBadChars(address);
		phone = escapeBadChars(phone);
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(
					  "Select MAX(publisherId) "
					+ "From " + database + ".tbl_publisher"					
			);
			int pubId = 1;
			if(resultSet.next()) {
				pubId = resultSet.getInt("MAX(publisherId)") + 1;
			}
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Insert Into " + database + ".tbl_publisher (publisherId, publisherName, publisherAddress, publisherPhone) "
					+ "Values (" + pubId + ", \'" + name + "\', \'" + address + "\', \'" + phone + "\')" 
			);
			return pubId;
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Updates the specified entry in the tbl_publisher table to the specified values
	 * @param publisherId - The Primary Key of the entry to be updated
	 * @param name - The new name of the publisher
	 * @param address - The new location of the publisher
	 * @param phone - The new contact info of the publisher
	 * @throws Exception - Throws an exception if something goes wrong with the update statement
	 */
	public void updatePublisher(int publisherId, String name, String address, String phone) throws Exception {
		name = escapeBadChars(name);
		address = escapeBadChars(address);
		phone = escapeBadChars(phone);
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Update " + database + ".tbl_publisher "
					+ "Set publisherName=\'" + name + "\', publisherAddress=\'" + address + "\', publisherPhone=\'" + phone + "\' "
					+ "Where publisherId=" + publisherId
			);			
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Deletes the specified entry from the tbl_publisher table
	 * @param publisherId - The Primary Key of the entry to be deleted
	 * @throws Exception - Throws an exception if something goes wrong with the delete statement
	 */
	public void deletePublisher(int publisherId) throws Exception {
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Delete From "+ database + ".tbl_publisher "
					+ "Where publisherId=" + publisherId
			);
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Retrieves the query of all entries in tbl_library_branch and turns it into an ArrayList of Branch objects and returns it
	 * @return - ArrayList of Branch objects retrieved from the database
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public List<Branch> getBranches() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_library_branch order by branchId");
			List<Branch> branches = new ArrayList<Branch>();
			while(resultSet.next()) {
				int id = resultSet.getInt("branchId");
				String name = resultSet.getString("branchName");
				String address = resultSet.getString("branchAddress");
				branches.add(new Branch(id, name, address));
			}
			return branches;
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Queries the database for a branch with the specified branchId and returns it
	 * @param branchId - The branchId for the query
	 * @return - The queried branch or null if no branch was found
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public Branch getBranch(int branchId) throws Exception{
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_library_branch where branchId=" + branchId);
			if(resultSet.next()) {
				int id = resultSet.getInt("branchId");
				String name = resultSet.getString("branchName");
				String address = resultSet.getString("branchAddress");
				return new Branch(id, name, address);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	

	/**
	 * Adds an entry to the tbl_library_branch table with the specified values and a branchId one larger than the current max branchId
	 * @param name - The name of the new branch
	 * @param address - The location of the new branch
	 * @throws Exception - Throws an exception if something goes wrong with the query or insert into statement
	 * @return - Returns the branchId of the newly created entry
	 */
	public int addBranch(String name, String address) throws Exception {
		name = escapeBadChars(name);
		address = escapeBadChars(address);
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(
					  "Select MAX(branchId) "
					+ "From " + database + ".tbl_library_branch"					
			);
			int branchId = 1;
			if(resultSet.next()) {
				branchId = resultSet.getInt("MAX(branchId)") + 1;
			}
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Insert Into " + database + ".tbl_library_branch (branchId, branchName, branchAddress) "
					+ "Values (" + branchId + ", \'" + name + "\', \'" + address + "\')" 
			);
			return branchId;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Updates the specified entry in the tbl_library_branch table to the specified values
	 * @param branchId - The Primary Key of the entry to be updated
	 * @param name - The new name of the branch
	 * @param address - The new location of the branch
	 * @throws Exception - Throws an exception if something goes wrong with the update statement
	 */
	public void updateBranch(int branchId, String name, String address) throws Exception {
		name = escapeBadChars(name);
		address = escapeBadChars(address);
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Update " + database + ".tbl_library_branch "
					+ "Set branchName=\'" + name + "\', branchAddress=\'" + address + "\' "
					+ "Where branchId=" + branchId
			);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Deletes the specified entry from the tbl_library_branch table
	 * @param branchId - The Primary key of the entry to be deleted
	 * @throws Exception - Throws an exception if something goes wrong with the delete statement
	 */
	public void deleteBranch(int branchId) throws Exception {
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Delete From "+ database + ".tbl_library_branch "
					+ "Where branchId=" + branchId
			);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Retrieves the query of all entries in tbl_borrower and turns it into an ArrayList of Borrower objects and returns it
	 * @return - ArrayList of Borrower objects retrieved from the database
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public List<Borrower> getBorrowers() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_borrower order by cardNo");
			List<Borrower> borrowers = new ArrayList<Borrower>();
			while(resultSet.next()) {
				int cardNo = resultSet.getInt("cardNo");
				String name = resultSet.getString("name");
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				borrowers.add(new Borrower(cardNo, name, address, phone));
			}
			return borrowers;
		} catch (Exception e) {
			throw e;
		}
 	}

	/**
	 * Queries the database for a borrower with the specified cardNo and returns it
	 * @param cardNo - The cardNo for the query
	 * @return - The queried borrower or null if no borrower was found
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public Borrower getBorrower(int cardNo) throws Exception{
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_borrower where cardNo=" + cardNo);
			if(resultSet.next()) {
				int cardNum = resultSet.getInt("cardNo");
				String name = resultSet.getString("name");
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				return new Borrower(cardNum, name, address, phone);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Adds an entry to the tbl_borrower table with the specified values and a cardNo one larger than the current max cardNo
	 * @param name - The name of the new borrower
	 * @param address - The location of the new borrower
	 * @param phone - The contact info of the new borrower
	 * @throws Exception - Throws an exception if something goes wrong with the query or insert into statement
	 * @return - Returns the cardNo of the newly created entry
	 */
	public int addBorrower(String name, String address, String phone) throws Exception {
		name = escapeBadChars(name);
		address = escapeBadChars(address);
		phone = escapeBadChars(phone);
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(
					  "Select MAX(cardNo) "
					+ "From " + database + ".tbl_borrower"					
			);
			int cardNo = 1;
			if(resultSet.next()) {
				cardNo = resultSet.getInt("MAX(cardNo)") + 1;
			}
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Insert Into " + database + ".tbl_borrower (cardNo, name, address, phone) "
					+ "Values (" + cardNo + ", \'" + name + "\', \'" + address + "\', \'" + phone + "\')" 
			);
			return cardNo;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Updates the specified entry in the tbl_borrower table to the specified values
	 * @param cardNo - The Primary Key of the entry to be updated 
	 * @param name - The new name of the borrower
	 * @param address - The new location of the borrower
	 * @param phone - The new contact info of the borrower
	 * @throws Exception - Throws an exception if something goes wrong with the update statement
	 */
	public void updateBorrower(int cardNo, String name, String address, String phone) throws Exception {
		name = escapeBadChars(name);
		address = escapeBadChars(address);
		phone = escapeBadChars(phone);
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Update " + database + ".tbl_borrower "
					+ "Set name=\'" + name + "\', address=\'" + address + "\', phone=\'" + phone + "\' "
					+ "Where cardNo=" + cardNo
			);			
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Deletes the specified entry from the tbl_borrower table
	 * @param cardNo - The Primary Key of the entry to be deleted
	 * @throws Exception - Throws an exception if something goes wrong with the delete statement
	 */
	public void deleteBorrower(int cardNo) throws Exception {
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Delete From "+ database + ".tbl_borrower "
					+ "Where cardNo=" + cardNo
			);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Retrieves the query of all entries in tbl_book_loans and turns it into an ArrayList of Loan objects and returns it
	 * @return - ArrayList of Loan objects retrieved from the database
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public List<Loan> getLoans() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_book_loans");
			List<Loan> loans = new ArrayList<Loan>();
			while(resultSet.next()) {
				int bookId = resultSet.getInt("bookId");
				int branchId = resultSet.getInt("branchId");
				int cardNo = resultSet.getInt("cardNo");
				String dateOut = resultSet.getTimestamp("dateOut").toString();
				String dueDate = resultSet.getTimestamp("dueDate").toString();
				loans.add(new Loan(bookId, branchId, cardNo, dateOut, dueDate));
			}
			return loans;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Queries the database for a book loan with the specified branchId, bookId, and cardNo and returns it
	 * @param branchId - The branchId for the query
	 * @param bookId - The bookId for the query
	 * @param cardNo - The cardNo for the query
	 * @return - The queried book loan or null if no book loan was found
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public Loan getBookLoan(int branchId, int bookId, int cardNo) throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_book_loans where bookId=" + bookId + " AND branchId=" + branchId + " AND cardNo=" + cardNo);             
			if(resultSet.next()) {
				int book = resultSet.getInt("bookId");
				int branch = resultSet.getInt("branchId");
				int cardNum = resultSet.getInt("cardNo");
				String dateOut = resultSet.getTimestamp("dateOut").toString();
				String dueDate = resultSet.getTimestamp("dueDate").toString();
				return new Loan(book, branch, cardNum, dateOut, dueDate);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Adds an entry to the tbl_book_loans table with the specified values and decrements the respective entry in the tbl_book_copies table
	 * @param branchId - The Foreign Key of the branch the book is being borrowed from
	 * @param bookId - The Foreign Key of the book that is being borrowed
	 * @param cardNo - The Foreign Key of the borrower who is borrowing the book
	 * @throws Exception - Throws an exception if something goes wrong with the update statement
	 * @return - Returns the due date of the book
	 */
	public String addBookLoan(int branchId, int bookId, int cardNo) throws Exception {
		DateTimeFormatter sqlDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime curDate = LocalDateTime.now();
	    String dateOut = curDate.format(sqlDateTime);
		LocalDateTime curDateMonth = curDate.plusMonths(1);
		String dueDate = curDateMonth.format(sqlDateTime);
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					"Insert into " + database + ".tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) " +
					"Values (" + bookId + ", " + branchId + ", " + cardNo + ", \'" + dateOut + "\', \'" + dueDate + "\')"		
			);
			updateBookCopies(branchId, bookId, getBookCopy(branchId, bookId).getNoOfCopies()-1);
			return dueDate;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Deletes the specified entry from the tbl_book_loans table
	 * @param branchId - The Branch's Foreign Key of the entry to be deleted
	 * @param bookId - The Book's Foreign Key of the entry to be deleted
	 * @param cardNo - The Borrower's Foreign Key of the entry to be deleted
	 * @throws Exception - Throws an exception if something goes wrong with the delete statement
	 */
	public void deleteBookLoan(int branchId, int bookId, int cardNo) throws Exception{
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Delete From " + database + ".tbl_book_loans "
					+ "Where branchId=" + branchId + " AND bookId=" + bookId + " AND cardNo=" + cardNo
			);
			updateBookCopies(branchId, bookId, getBookCopy(branchId, bookId).getNoOfCopies()+1);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Retrieves the query of all entries in tbl_book_copies and turns it into an ArrayList of BookCopy objects and returns it
	 * @return - ArrayList of BookCopy objects retrieved from the database
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public List<BookCopy> getBookCopies() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_book_copies");
			List<BookCopy> bookCopies = new ArrayList<BookCopy>();
			while(resultSet.next()) {
				int bookId = resultSet.getInt("bookId");
				int branchId = resultSet.getInt("branchId");
				int noOfCopies = resultSet.getInt("noOfCopies");
				bookCopies.add(new BookCopy(bookId, branchId, noOfCopies));
			}
			return bookCopies;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Queries the database for a book copy entry with the specified branchId and bookId and returns it
	 * @param branchId - The branchId for the query
	 * @param bookId - The bookId for the query
	 * @return - The queried book copy entry or null if no book copy entry was found
	 * @throws Exception - Throws an exception if something goes wrong with the query
	 */
	public BookCopy getBookCopy(int branchId, int bookId) throws Exception{
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_book_copies where bookId=" + bookId + " AND branchId=" + branchId);
			if(resultSet.next()) {
				int book = resultSet.getInt("bookId");
				int branch = resultSet.getInt("branchId");
				int numCopies = resultSet.getInt("noOfCopies");
				return new BookCopy(book, branch, numCopies);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Adds an entry to the tbl_book_copies table with the specified values
	 * @param branchId - The branch of the new entry
	 * @param bookId - The book of the new entry
	 * @param noOfCopies - The number of copies of the book in the branch
	 * @throws Exception - Throws an exception if something goes wrong with the insert into statement 
	 */
	public void addBookCopy(int branchId, int bookId, int noOfCopies) throws Exception {
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Insert Into " + database + ".tbl_book_copies (bookId, branchId, noOfCopies) "
					+ "Values (" + bookId + ", " + branchId + ", " + noOfCopies + ")" 
			);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Update the specified entry in the tbl_book_copies table to have the specified value for noOfCopies
	 * @param branchId - The Foreign Key of the specified branch
	 * @param bookId - The Foreign Key of the specified book
	 * @param noOfCopies - The new number of copies of the specified book at the specified branch
	 * @throws Exception - Throws an exception if something goes wrong with the update statement
	 */
	public void updateBookCopies(int branchId, int bookId, int noOfCopies) throws Exception {
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Update " + database + ".tbl_book_copies "
					+ "Set noOfCopies=" + noOfCopies + " "
					+ "Where branchId=" + branchId + " AND bookId=" + bookId
			);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Update the specified bookLoan entry in the database so that it has no due date (set to 12/31/9999)
	 * @param bookId - The bookId of the book that was checked out
	 * @param branchId - The branchId of the branch the book was checked out from
	 * @param cardNo - The cardNo of the borrower who checked out the book
	 * @throws Exception - Throws an exception if something goes wrong with the update statement
	 */
	public void overrideDueDate(int bookId, int branchId, int cardNo) throws Exception{
		try {
			statement = connect.createStatement();
			statement.executeUpdate(
					  "Update " + database + ".tbl_book_loans "
					+ "Set dueDate=\'9999-12-31 23:59:59\' "
					+ "Where bookId=" + bookId + " AND branchId=" + branchId + " AND cardNo=" + cardNo
			);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * A simple function to escape all relevant characters in a string before passing them into an SQL query
	 * @param s - A string to escape characters in
	 * @return - The string with all the relevant characters escaped
	 */
	private String escapeBadChars(String s) {
		s = s.replace("\\", "\\\\");
		s = s.replace("\'", "\\\'");
		s = s.replace("\"", "\\\"");
		return s;
	}

	/**
	 * Function to clean up and close the database before the project exits
	 */
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			
		}
	}
}
