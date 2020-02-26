package com.smoothstack.lms.adminmicroservice;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.smoothstack.lms.adminmicroservice.controller.AdminController;
import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.BookCopy;
import com.smoothstack.lms.adminmicroservice.model.BookCopyId;
import com.smoothstack.lms.adminmicroservice.model.Borrower;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.LibraryBranch;
import com.smoothstack.lms.adminmicroservice.model.Publisher;

@SpringBootTest
class AdminConrollerTest {

	@Autowired
	AdminController adminController;

	@Test
	void testAuthor() throws ClassNotFoundException, SQLException {
		Author author = new Author();
		author.setName("Test");
		author.setBooks(new ArrayList<Book>());
		Assertions.assertEquals(HttpStatus.CREATED, adminController.saveAuthor(author).getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, adminController.getAuthor(author.getId()).getStatusCode());
		author.setName("Test2");
		Assertions.assertEquals(HttpStatus.OK,
				adminController.updateAuthor(author, author.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NO_CONTENT,
				adminController.deleteAuthor(author.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, adminController.getAuthor(author.getId()).getStatusCode());
	}

	@Test
	void testReadAuthor() throws ClassNotFoundException, SQLException {
		Assertions.assertEquals(HttpStatus.OK, adminController.getAuthors().getStatusCode());
	}

	@Test
	void testBook() throws ClassNotFoundException, SQLException {
		Book book = new Book();
		book.setAuthors(new ArrayList<Author>());
		Author author = new Author();
		author.setId(1L);
		book.getAuthors().add(author);
		book.setGenres(new ArrayList<Genre>());
		book.setPublisher(new Publisher());
		book.getPublisher().setId(1L);
		book.setTitle("Test");
		Assertions.assertEquals(HttpStatus.CREATED, adminController.saveBook(book).getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, adminController.getBook(book.getId()).getStatusCode());
		book.setTitle("Test2");
		Assertions.assertEquals(HttpStatus.OK, adminController.updateBook(book, book.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NO_CONTENT, adminController.deleteBook(book.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, adminController.getBook(book.getId()).getStatusCode());
	}

	@Test
	void testReadBook() throws ClassNotFoundException, SQLException {
		Assertions.assertEquals(HttpStatus.OK, adminController.getBooks().getStatusCode());
	}

	@Test
	void testGenre() throws ClassNotFoundException, SQLException {
		Genre genre = new Genre();
		genre.setName("Test");
		genre.setBooks(new ArrayList<Book>());
		Assertions.assertEquals(HttpStatus.CREATED, adminController.saveGenre(genre).getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, adminController.getGenre(genre.getId()).getStatusCode());
		genre.setName("Test2");
		Assertions.assertEquals(HttpStatus.OK, adminController.updateGenre(genre, genre.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NO_CONTENT, adminController.deleteGenre(genre.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, adminController.getGenre(genre.getId()).getStatusCode());
	}

	@Test
	void testReadGenre() throws ClassNotFoundException, SQLException {
		Assertions.assertEquals(HttpStatus.OK, adminController.getGenres().getStatusCode());
	}
	
	@Test
	void testBorrower() throws ClassNotFoundException, SQLException {
		Borrower borrower = new Borrower();
		borrower.setName("Test");
		borrower.setAddress("123");
		borrower.setPhone("123");
		Assertions.assertEquals(HttpStatus.CREATED, adminController.saveBorrower(borrower).getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, adminController.getBorrower(borrower.getCardNumber()).getStatusCode());
		borrower.setName("Test2");
		Assertions.assertEquals(HttpStatus.OK,
				adminController.updateBorrower(borrower, borrower.getCardNumber()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NO_CONTENT,
				adminController.deleteBorrower(borrower.getCardNumber()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, adminController.getBorrower(borrower.getCardNumber()).getStatusCode());
	}

	@Test
	void testReadBorrower() throws ClassNotFoundException, SQLException {
		Assertions.assertEquals(HttpStatus.OK, adminController.getBorrowers().getStatusCode());
	}
	
	@Test
	void testBranch() throws ClassNotFoundException, SQLException {
		LibraryBranch branch = new LibraryBranch();
		branch.setName("Test");
		branch.setAddress("123");
		Assertions.assertEquals(HttpStatus.CREATED, adminController.saveBranch(branch).getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, adminController.getBranch(branch.getId()).getStatusCode());
		branch.setName("Test2");
		Assertions.assertEquals(HttpStatus.OK,
				adminController.updateLibraryBranch(branch, branch.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NO_CONTENT,
				adminController.deleteLibraryBranch(branch.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, adminController.getBranch(branch.getId()).getStatusCode());
	}

	@Test
	void testReadBranch() throws ClassNotFoundException, SQLException {
		Assertions.assertEquals(HttpStatus.OK, adminController.getBranchs().getStatusCode());
	}
	
	@Test
	void testCopies() throws ClassNotFoundException, SQLException {
		BookCopy copy = new BookCopy();
		copy.setId(new BookCopyId(5L, 3L));
		copy.setAmount(66);
		Assertions.assertEquals(HttpStatus.CREATED, adminController.saveBookCopy(copy).getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, adminController.getCopy(
				copy.getId().getLibraryBranchId(), copy.getId().getBookId()).getStatusCode());
		copy.setAmount(67);
		Assertions.assertEquals(HttpStatus.OK,
				adminController.updateBookCopy(copy, copy.getId().getLibraryBranchId(), copy.getId().getBookId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NO_CONTENT,
				adminController.deleteBookCopy(copy.getId().getLibraryBranchId(), copy.getId().getBookId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, adminController.getCopy(
				copy.getId().getLibraryBranchId(), copy.getId().getBookId()).getStatusCode());
	}

	@Test
	void testReadCopies() throws ClassNotFoundException, SQLException {
		Assertions.assertEquals(HttpStatus.OK, adminController.getBookCopy().getStatusCode());
	}
	
	@Test
	void testPublisher() throws ClassNotFoundException, SQLException {
		Publisher publisher = new Publisher();
		publisher.setName("Test");
		publisher.setAddress("123");
		publisher.setPhone("123");
		Assertions.assertEquals(HttpStatus.CREATED, adminController.savePublisher(publisher).getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, adminController.getPublisher(publisher.getId()).getStatusCode());
		publisher.setName("Test2");
		Assertions.assertEquals(HttpStatus.OK,
				adminController.updatePublisher(publisher, publisher.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NO_CONTENT,
				adminController.deletePublisher(publisher.getId()).getStatusCode());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, adminController.getPublisher(publisher.getId()).getStatusCode());
	}

	@Test
	void testReadPublisher() throws ClassNotFoundException, SQLException {
		Assertions.assertEquals(HttpStatus.OK, adminController.getPublishers().getStatusCode());
	}

}
