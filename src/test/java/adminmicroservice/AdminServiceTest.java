package com.smoothstack.lms.adminmicroservice;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.BookCopy;
import com.smoothstack.lms.adminmicroservice.model.BookCopyId;
import com.smoothstack.lms.adminmicroservice.model.Borrower;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.LibraryBranch;
import com.smoothstack.lms.adminmicroservice.model.Publisher;
import com.smoothstack.lms.adminmicroservice.service.AdminService;

@SpringBootTest
class AdminServiceTest {

	@Autowired
	AdminService adminService;

	@Test
	void testAuthor() throws ClassNotFoundException, SQLException {
		Author author = new Author();
		author.setName("Test");
		author.setBooks(new ArrayList<Book>());
		adminService.saveAuthor(author);
		Assertions.assertNotNull(adminService.readAuthorById(author.getId()));
		author.setName("Test2");
		adminService.updateAuthor(author);
		Assertions.assertEquals("Test2", adminService.readAuthorById(author.getId()).get().getName());
		adminService.deleteAuthor(author);
		Assertions.assertTrue(adminService.readAuthorById(author.getId()).isEmpty());
	}

	@Test
	void testReadAuthor() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readAuthor());
	}

	@Test
	void testReadAuthorById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readAuthorById(1L));
		Assertions.assertTrue(adminService.readAuthorById(0L).isEmpty());
	}

	@Test
	void testBook() throws ClassNotFoundException, SQLException {
		Book book = new Book();
		book.setAuthors(new ArrayList<Author>());
		book.setGenres(new ArrayList<Genre>());
		book.setPublisher(new Publisher());
		book.getPublisher().setId(1L);
		book.setTitle("Test");
		adminService.saveBook(book);
		Assertions.assertNotNull(adminService.readBookById(book.getId()));
		book.setTitle("Test2");
		adminService.updateBook(book);
		Assertions.assertEquals("Test2", adminService.readBookById(book.getId()).get().getTitle());
		adminService.deleteBook(book);
		Assertions.assertTrue(adminService.readBookById(book.getId()).isEmpty());
	}

	@Test
	void testReadBook() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBook());
	}

	@Test
	void testReadBookById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBookById(1L));
		Assertions.assertTrue(adminService.readBookById(0L).isEmpty());
	}

	@Test
	void testGenre() throws ClassNotFoundException, SQLException {
		Genre genre = new Genre();
		genre.setName("Test");
		genre.setBooks(new ArrayList<Book>());
		adminService.saveGenre(genre);
		Assertions.assertNotNull(adminService.readGenreById(genre.getId()));
		genre.setName("Test2");
		adminService.updateGenre(genre);
		Assertions.assertEquals("Test2", adminService.readGenreById(genre.getId()).get().getName());
		adminService.deleteGenre(genre);
		Assertions.assertTrue(adminService.readGenreById(genre.getId()).isEmpty());
	}

	@Test
	void testReadGenre() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readGenre());
	}

	@Test
	void testReadGenreById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readGenreById(1L));
		Assertions.assertTrue(adminService.readGenreById(0L).isEmpty());
	}

	@Test
	void testBorrower() throws ClassNotFoundException, SQLException {
		Borrower borrower = new Borrower();
		borrower.setName("Test");
		borrower.setAddress("123");
		borrower.setPhone("123");
		adminService.saveBorrower(borrower);
		Assertions.assertNotNull(adminService.readBorrowerById(borrower.getCardNumber()));
		borrower.setName("Test2");
		adminService.updateBorrower(borrower);
		Assertions.assertEquals("Test2", adminService.readBorrowerById(borrower.getCardNumber()).get().getName());
		adminService.deleteBorrower(borrower);
		Assertions.assertTrue(adminService.readBorrowerById(borrower.getCardNumber()).isEmpty());
	}

	@Test
	void testReadBorrower() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBorrower());
	}

	@Test
	void testReadBorrowerById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBorrowerById(1L));
		Assertions.assertTrue(adminService.readBorrowerById(0L).isEmpty());
	}

	@Test
	void testBranch() throws ClassNotFoundException, SQLException {
		LibraryBranch branch = new LibraryBranch();
		branch.setName("Test");
		branch.setAddress("123");
		adminService.saveBranch(branch);
		Assertions.assertNotNull(adminService.readBranchById(branch.getId()));
		branch.setName("Test2");
		adminService.updateBranch(branch);
		Assertions.assertEquals("Test2", adminService.readBranchById(branch.getId()).get().getName());
		adminService.deleteBranch(branch);
		Assertions.assertTrue(adminService.readBranchById(branch.getId()).isEmpty());
	}

	@Test
	void testReadBranch() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBranch());
	}

	@Test
	void testReadBranchById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBranchById(1L));
		Assertions.assertTrue(adminService.readBranchById(0L).isEmpty());
	}

	@Test
	void testCopies() throws ClassNotFoundException, SQLException {
		BookCopy copy = new BookCopy();
		BookCopyId id = new BookCopyId(5L, 3L);
		copy.setId(id);
		copy.setAmount(66);
		adminService.saveCopies(copy);
		Assertions.assertNotNull(adminService.readCopyById(copy.getId().getLibraryBranchId(), copy.getId().getBookId()));
		copy.setAmount(67);
		adminService.updateCopies(copy);
		Assertions.assertEquals(67, adminService.readCopyById
				(copy.getId().getLibraryBranchId(), copy.getId().getBookId()).get().getAmount());
		adminService.deleteCopies(copy);
		Assertions.assertTrue(adminService.readCopyById(copy.getId().getLibraryBranchId(), copy.getId().getBookId()).isEmpty());
	}

	@Test
	void testReadCopies() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readCopies());
	}

	@Test
	void testPublisher() throws ClassNotFoundException, SQLException {
		Publisher publisher = new Publisher();
		publisher.setName("Test");
		publisher.setAddress("123");
		publisher.setPhone("123");
		adminService.savePublisher(publisher);
		Assertions.assertNotNull(adminService.readPublisherById(publisher.getId()));
		publisher.setName("Test2");
		adminService.updatePublisher(publisher);
		Assertions.assertEquals("Test2", adminService.readPublisherById(publisher.getId()).get().getName());
		adminService.deletePublisher(publisher);
		Assertions.assertTrue(adminService.readPublisherById(publisher.getId()).isEmpty());
	}

	@Test
	void testReadPublisher() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readPublisher());
	}

	@Test
	void testReadPublisherById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readPublisherById(1L));
		Assertions.assertTrue(adminService.readPublisherById(0L).isEmpty());
	}

}
