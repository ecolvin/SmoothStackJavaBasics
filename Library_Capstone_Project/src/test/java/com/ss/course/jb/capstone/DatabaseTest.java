/**
 * 
 */
package com.ss.course.jb.capstone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Eric Colvin
 *
 */
public class DatabaseTest {
	Database db = new Database();
	
	@Before
	public void connectToDBTest() {
		try {
			assertEquals(0, db.connectToDB());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getBooksTest() {
		try {
			List<Book> books = db.getBooks();
			assertNotEquals(0, books.size());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getBookTest() {
		try {
			List<Book> books = db.getBooks();
			Book b1 = books.get(0);
			Book b2 = db.getBook(b1.getBookId());
			assertNotEquals(null, b2);
			assertEquals(b1.getBookId(), b2.getBookId());
			assertEquals(b1.getTitle(), b2.getTitle());
			assertEquals(b1.getAuthId(), b2.getAuthId());
			assertEquals(b1.getPubId(), b2.getPubId());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void addBookTest() {
		try {
			List<Book> books1 = db.getBooks();
			int id = db.addBook("Test Book", 1, 2);
			assertNotEquals(-1, id);
			Book b = db.getBook(id);
			assertNotEquals(null, b);
			assertEquals(id, b.getBookId());
			assertEquals("Test Book", b.getTitle());
			assertEquals(1, b.getAuthId());
			assertEquals(2, b.getPubId());
			List<Book> books2 = db.getBooks();
			assertEquals(books1.size() + 1, books2.size());
			db.deleteBook(id);			
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void updateBookTest() {
		try {
			int id = db.addBook("Book Test", 2, 1);
			db.updateBook(id, "Test Book", 1, 2);
			Book b = db.getBook(id);
			assertEquals(id, b.getBookId());
			assertEquals("Test Book", b.getTitle());
			assertEquals(1, b.getAuthId());
			assertEquals(2, b.getPubId());
			db.deleteBook(id);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void deleteBookTest() {
		try {
			int id = db.addBook("Test Book", 1, 2);
			List<Book> booksBefore = db.getBooks();
			db.deleteBook(id);
			List<Book> booksAfter = db.getBooks();
			assertEquals(null, db.getBook(id));
			assertEquals(booksBefore.size() - 1, booksAfter.size());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getAuthorsTest() {
		try {
			List<Author> authors = db.getAuthors();
			assertNotEquals(0, authors.size());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getAuthorTestId() {
		try {
			List<Author> authors = db.getAuthors();
			Author a1 = authors.get(0);
			Author a2 = db.getAuthor(a1.getAuthorId());
			assertNotEquals(null, a2);
			assertEquals(a1.getAuthorId(), a2.getAuthorId());
			assertEquals(a1.getAuthorName(), a2.getAuthorName());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getAuthorTestName() {
		try {
			List<Author> authors = db.getAuthors();
			Author a1 = authors.get(0);
			Author a2 = db.getAuthor(a1.getAuthorName());
			assertNotEquals(null, a2);
			assertEquals(a1.getAuthorId(), a2.getAuthorId());
			assertEquals(a1.getAuthorName(), a2.getAuthorName());			
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void addAuthorTest() {
		try {
			List<Author> auths1 = db.getAuthors();
			int id = db.addAuthor("Test Name");
			assertNotEquals(-1, id);
			Author a = db.getAuthor(id);
			assertNotEquals(null, a);
			assertEquals(id, a.getAuthorId());
			assertEquals("Test Name", a.getAuthorName());
			List<Author> auths2 = db.getAuthors();
			assertEquals(auths1.size() + 1, auths2.size());
			db.deleteAuthor(id);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void updateAuthorTest() {
		try {
			int id = db.addAuthor("Name Test");
			db.updateAuthor(id, "Test Name");
			Author a = db.getAuthor(id);
			assertEquals(id, a.getAuthorId());
			assertEquals("Test Name", a.getAuthorName());
			db.deleteAuthor(id);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void deleteAuthorTest() {
		try {
			int id = db.addAuthor("Test Name");
			List<Author> authorsBefore = db.getAuthors();
			db.deleteAuthor(id);
			List<Author> authorsAfter = db.getAuthors();
			assertEquals(null, db.getAuthor(id));
			assertEquals(authorsBefore.size() - 1, authorsAfter.size());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getPublishersTest() {
		try {
			List<Publisher> publishers = db.getPublishers();
			assertNotEquals(0, publishers.size());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getPublisherTestId() {
		try {
			List<Publisher> pubs = db.getPublishers();
			Publisher p1 = pubs.get(0);
			Publisher p2 = db.getPublisher(p1.getPublisherId());
			assertNotEquals(null, p2);
			assertEquals(p1.getPublisherId(), p2.getPublisherId());
			assertEquals(p1.getPublisherName(), p2.getPublisherName());
			assertEquals(p1.getPublisherAddress(), p2.getPublisherAddress());
			assertEquals(p1.getPublisherPhone(), p2.getPublisherPhone());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getPublisherTestName() {
		try {
			List<Publisher> pubs = db.getPublishers();
			Publisher p1 = pubs.get(0);
			Publisher p2 = db.getPublisher(p1.getPublisherName());
			assertNotEquals(null, p2);
			assertEquals(p1.getPublisherId(), p2.getPublisherId());
			assertEquals(p1.getPublisherName(), p2.getPublisherName());
			assertEquals(p1.getPublisherAddress(), p2.getPublisherAddress());
			assertEquals(p1.getPublisherPhone(), p2.getPublisherPhone());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void addPublisherTest() {
		try {
			List<Publisher> pubs1 = db.getPublishers();
			int id = db.addPublisher("Test Name", "Test Address", "Test Phone");
			assertNotEquals(-1, id);
			Publisher p = db.getPublisher(id);
			assertNotEquals(null, p);
			assertEquals(id, p.getPublisherId());
			assertEquals("Test Name", p.getPublisherName());
			assertEquals("Test Address", p.getPublisherAddress());
			assertEquals("Test Phone", p.getPublisherPhone());
			List<Publisher> pubs2 = db.getPublishers();
			assertEquals(pubs1.size() + 1, pubs2.size());
			db.deletePublisher(id);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void updatePublisherTest() {
		try {
			int id = db.addPublisher("Name Test", "Address Test", "Phone Test");
			db.updatePublisher(id, "Test Name", "Test Address", "Test Phone");
			Publisher p = db.getPublisher(id);
			assertEquals(id, p.getPublisherId());
			assertEquals("Test Name", p.getPublisherName());
			assertEquals("Test Address", p.getPublisherAddress());
			assertEquals("Test Phone", p.getPublisherPhone());
			db.deletePublisher(id);
		} catch (Exception e) {  
			fail(e.toString());
		}
	}
	
	@Test
	public void deletePublisherTest() {
		try {
			int id = db.addPublisher("Test Name", "Test Address", "Test Phone");
			List<Publisher> pubsBefore = db.getPublishers();
			db.deletePublisher(id);
			List<Publisher> pubsAfter = db.getPublishers();
			assertEquals(null, db.getPublisher(id));
			assertEquals(pubsBefore.size() - 1, pubsAfter.size());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getBranchesTest() {
		try {
			List<Branch> branches = db.getBranches();
			assertNotEquals(0, branches.size());			
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getBranchTest() {
		try {
			List<Branch> branches = db.getBranches();
			Branch b1 = branches.get(0);
			Branch b2 = db.getBranch(b1.getBranchId());
			assertNotEquals(null, b2);
			assertEquals(b1.getBranchId(), b2.getBranchId());
			assertEquals(b1.getBranchName(), b2.getBranchName());
			assertEquals(b1.getBranchAddress(), b2.getBranchAddress());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void addBranchTest() {
		try {
			List<Branch> branches1 = db.getBranches();
			int id = db.addBranch("Test Name", "Test Address");
			assertNotEquals(-1, id);
			Branch b = db.getBranch(id);
			assertNotEquals(null, b);
			assertEquals(id, b.getBranchId());
			assertEquals("Test Name", b.getBranchName());
			assertEquals("Test Address", b.getBranchAddress());
			List<Branch> branches2 = db.getBranches();
			assertEquals(branches1.size() + 1, branches2.size());
			db.deleteBranch(id);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void updateBranchTest() {
		try {
			int id = db.addBranch("Name Test", "Address Test");
			db.updateBranch(id, "Test Name", "Test Address");
			Branch b = db.getBranch(id);
			assertEquals(id, b.getBranchId());
			assertEquals("Test Name", b.getBranchName());
			assertEquals("Test Address", b.getBranchAddress());
			db.deleteBranch(id);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void deleteBranchTest() {
		try {
			int id = db.addBranch("Test Name", "Test Address");
			List<Branch> branchesBefore = db.getBranches();
			db.deleteBranch(id);
			List<Branch> branchesAfter = db.getBranches();
			assertEquals(null, db.getBranch(id));
			assertEquals(branchesBefore.size() - 1, branchesAfter.size());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getBorrowersTest() {
		try {
			List<Borrower> borrowers = db.getBorrowers();
			assertNotEquals(0, borrowers.size());			
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getBorrowerTest() {
		try {
			List<Borrower> borrs = db.getBorrowers();
			Borrower b1 = borrs.get(0);
			Borrower b2 = db.getBorrower(b1.getCardNo());
			assertNotEquals(null, b2);
			assertEquals(b1.getCardNo(), b2.getCardNo());
			assertEquals(b1.getName(), b2.getName());
			assertEquals(b1.getAddress(), b2.getAddress());
			assertEquals(b1.getPhone(), b2.getPhone());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void addBorrowerTest() {
		try {
			List<Borrower> borrs1 = db.getBorrowers();
			int card = db.addBorrower("Test Name", "Test Address", "Test Phone");
			assertNotEquals(-1, card);
			Borrower b = db.getBorrower(card);
			assertNotEquals(null, b);
			assertEquals(card, b.getCardNo());
			assertEquals("Test Name", b.getName());
			assertEquals("Test Address", b.getAddress());
			assertEquals("Test Phone", b.getPhone());
			List<Borrower> borrs2 = db.getBorrowers();
			assertEquals(borrs1.size() + 1, borrs2.size());
			db.deleteBorrower(card);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void updateBorrowerTest() {
		try {
			int card = db.addBorrower("Name Test", "Address Test", "Phone Test");
			db.updateBorrower(card, "Test Name", "Test Address", "Test Phone");
			Borrower b = db.getBorrower(card);
			assertEquals(card, b.getCardNo());
			assertEquals("Test Name", b.getName());
			assertEquals("Test Address", b.getAddress());
			assertEquals("Test Phone", b.getPhone());
			db.deleteBorrower(card);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void deleteBorrowerTest() {
		try {
			int card = db.addBorrower("Test Name", "Test Address", "Test Phone");
			List<Borrower> borrsBefore = db.getBorrowers();
			db.deleteBorrower(card);
			List<Borrower> borrsAfter = db.getBorrowers();
			assertEquals(null, db.getBorrower(card));
			assertEquals(borrsBefore.size() - 1, borrsAfter.size());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void getBookLoanTest() {
		try {
			List<Loan> loans = db.getLoans();
			if(loans.size() == 0) {
				String dueDate = db.addBookLoan(3, 2, 1);
				Loan l = db.getBookLoan(3, 2, 1);
				assertNotEquals(null, l);
				assertEquals(3, l.getBranchId());
				assertEquals(2, l.getBookId());
				assertEquals(1, l.getCardNo());
				assertEquals(dueDate, l.getDueDate());
				db.deleteBookLoan(3, 2, 1);
			} else {
				Loan l1 = loans.get(0);
				Loan l2 = db.getBookLoan(l1.getBranchId(), l1.getBookId(), l1.getCardNo());
				assertNotEquals(null, l2);
				assertEquals(l1.getBranchId(), l2.getBranchId());
				assertEquals(l1.getBookId(), l2.getBookId());
				assertEquals(l1.getCardNo(), l2.getCardNo());
				assertEquals(l1.getDateOut(), l2.getDateOut());
				assertEquals(l1.getDueDate(), l2.getDueDate());
			}
		} catch (Exception e) {
			fail(e.toString());
		}
	}
			
	@Test
	public void getBookCopyTest() {
		try {
			List<BookCopy> copies = db.getBookCopies();
			if(copies.size() == 0) {
				db.addBookCopy(1, 2, 7);
				BookCopy bc = db.getBookCopy(1, 2);
				assertNotEquals(null, bc);
				assertEquals(1, bc.getBranchId());
				assertEquals(2, bc.getBookId());
				assertEquals(7, bc.getNoOfCopies());
				db.updateBookCopies(1, 2, 0);
			} else {
				BookCopy bc1 = copies.get(0);
				BookCopy bc2 = db.getBookCopy(bc1.getBranchId(), bc1.getBookId());
				assertNotEquals(null, bc2);
				assertEquals(bc1.getBranchId(), bc2.getBranchId());
				assertEquals(bc1.getBookId(), bc2.getBookId());
				assertEquals(bc1.getNoOfCopies(), bc2.getNoOfCopies());
			}
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void updateBookCopiesTest() {
		try {
			List<BookCopy> copies = db.getBookCopies();
			BookCopy bc = copies.get(0);
			int oldBranch = bc.getBranchId();
			int oldBook = bc.getBookId();
			int oldNoOfCopies = bc.getNoOfCopies();
			db.updateBookCopies(oldBranch, oldBook, oldNoOfCopies + 3);
			bc = db.getBookCopy(oldBranch, oldBook);
			assertEquals(oldBranch, bc.getBranchId());
			assertEquals(oldBook, bc.getBookId());
			assertEquals(oldNoOfCopies + 3, bc.getNoOfCopies());
			db.updateBookCopies(oldBranch, oldBook, oldNoOfCopies);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@After
	public void closeTest() {
		db.close();
	}
}
