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

import java.util.Scanner;

/**
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
	
	public void connectToDB() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?" + "user=" + user + "&password=" + passwd);
		} catch (Exception e) {
			throw e;
		}	
		

		/*Scanner input = new Scanner(System.in);
		while(true) {
			String test = input.nextLine();
			test = escapeBadChars(test);
			System.out.println(test);
		}*/
		
	}
	
	public List<Book> getBooks() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_book");
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
	
	public void addBook(String title, int authId, int pubId) throws Exception {
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
		} catch (Exception e) {
			throw e;
		}
	}
	
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
	
	public List<Author> getAuthors() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_author");
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
	
	public void addAuthor(String name) throws Exception {
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
		} catch (Exception e) {
			throw e;
		}
	}
	
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
	
	public List<Publisher> getPublishers() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_publisher");
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
	
	public void addPublisher(String name, String address, String phone) throws Exception {
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
		} catch (Exception e) {
			throw e;
		}
	}
	
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
	
	public List<Branch> getBranches() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_library_branch");
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
	
	public void addBranch(String name, String address) throws Exception {
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
		} catch (Exception e) {
			throw e;
		}
	}
	
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
	
	public List<Borrower> getBorrowers() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from " + database + ".tbl_borrower");
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
	
	public void addBookLoan(int branchId, int bookId, int cardNo) throws Exception {
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
		} catch (Exception e) {
			throw e;
		}
	}
	
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
	
	public String escapeBadChars(String s) {
		s = s.replace("\\", "\\\\");
		s = s.replace("\'", "\\\'");
		s = s.replace("\"", "\\\"");
		return s;
	}
	
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
