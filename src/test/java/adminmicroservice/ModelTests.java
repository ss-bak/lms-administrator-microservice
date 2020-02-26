package com.smoothstack.lms.adminmicroservice;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.smoothstack.lms.common.model.Author;
import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Copies;
import com.smoothstack.lms.common.model.Borrower;
import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.model.Loans;
import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.model.Publisher;

@SpringBootTest
class ModelTests {

	@Test
	void authorTest() {
		Author author = new Author();
		author.setId(0L);
		author.setName("");
		author.setBooks(new ArrayList<Book>());
		author.getId();
		author.getName();
		author.getBooks();
		author.toString();
	}

	@Test
	void bookTest() {
		Book book = new Book();
		book.setAuthors(new ArrayList<Author>());
		book.setId(0L);
		book.setGenres(new ArrayList<Genre>());
		book.setPublisher(new Publisher());
		book.setTitle("");
		book.getAuthors();
		book.getId();
		book.getGenres();
		book.getPublisher();
		book.getTitle();
		book.toString();
	}

	@Test
	void borrowerTest() {
		Borrower borrower = new Borrower();
		borrower.setAddress("");
		borrower.setCardNumber(0L);
		borrower.setName("");
		borrower.setPhone("");
		borrower.getAddress();
		borrower.getCardNumber();
		borrower.getName();
		borrower.getPhone();
		borrower.toString();
	}

	@Test
	void branchTest() {
		LibraryBranch branch = new LibraryBranch();
		branch.setAddress("");
		branch.setId(0L);
		branch.setName("");
		branch.getAddress();
		branch.getId();
		branch.getName();
		branch.toString();
	}

	@Test
	void copiesTest() {
		BookCopy copy = new BookCopy();
		copy.setId(new BookCopyId(0L, 0L));
		copy.setAmount(0);
		copy.getId();
		copy.getId();
		copy.getAmount();
		copy.toString();
	}

	@Test
	void genreTest() {
		Genre genre = new Genre();
		genre.setBooks(new ArrayList<Book>());
		genre.setId(0L);
		genre.setName("");
		genre.getBooks();
		genre.getId();
		genre.getName();
		genre.toString();
	}

	@Test
	void publisherTest() {
		Publisher publisher = new Publisher();
		publisher.setAddress("");
		publisher.setId(0L);
		publisher.setName("");
		publisher.setPhone("");
		publisher.getAddress();
		publisher.getId();
		publisher.getName();
		publisher.getPhone();
		publisher.toString();
	}
}
