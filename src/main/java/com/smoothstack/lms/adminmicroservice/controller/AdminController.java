package com.smoothstack.lms.adminmicroservice.controller;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.BookCopy;
import com.smoothstack.lms.adminmicroservice.model.BookCopyId;
import com.smoothstack.lms.adminmicroservice.model.Borrower;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.LibraryBranch;
import com.smoothstack.lms.adminmicroservice.model.Publisher;
import com.smoothstack.lms.adminmicroservice.service.AdminService;

@RestController
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

	@Autowired
	Environment environment;

	@GetMapping(path = "/administrator/port")
	public String port() {
		return environment.getProperty("local.server.port");
	}

	@GetMapping(path = "/administrator/books")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {
		try {
			List<Book> books = adminService.readBook();
			return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/books/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Book> getBook(@PathVariable Long id) {
		try {
			Optional<Book> book = adminService.readBookById(id);
			if (!book.isPresent())
				return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Book>(book.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/books")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBook(@RequestBody Book book) {
		if (book == null || book.getPublisher().getId() == null || book.getTitle() == null || book.getAuthors() == null
				|| book.getAuthors().get(0).getId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveBook(book);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/books/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBook(@RequestBody Book book, @PathVariable Long id) {
		if (book == null || book.getPublisher().getId() == null || book.getTitle() == null || book.getAuthors() == null
				|| book.getAuthors().get(0).getId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		book.setId(id);
		try {
			adminService.updateBook(book);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		try {
			Optional<Book> optBook = adminService.readBookById(id);
			if (!optBook.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			adminService.deleteBook(optBook.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/authors")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Author>> getAuthors() {
		try {
			List<Author> authors = adminService.readAuthor();
			return new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/authors/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
		try {
			Optional<Author> author = adminService.readAuthorById(id);
			if (!author.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Author>(author.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/authors")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveAuthor(@RequestBody Author author) {
		if (author == null || author.getName() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveAuthor(author);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(author.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/authors/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateAuthor(@RequestBody Author author, @PathVariable Long id) {
		if (author == null || author.getName() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		author.setId(id);
		try {
			adminService.updateAuthor(author);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/authors/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		try {
			Optional<Author> optAuthor = adminService.readAuthorById(id);
			if (!optAuthor.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			adminService.deleteAuthor(optAuthor.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/genres")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Genre>> getGenres() {
		try {
			List<Genre> genres = adminService.readGenre();
			return new ResponseEntity<List<Genre>>(genres, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/genres/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Genre> getGenre(@PathVariable Long id) {
		try {
			Optional<Genre> genre = adminService.readGenreById(id);
			if (!genre.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Genre>(genre.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/genres")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveGenre(@RequestBody Genre genre) {
		if (genre == null || genre.getName() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveGenre(genre);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(genre.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/genres/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateGenre(@RequestBody Genre genre, @PathVariable Long id) {
		if (genre == null || genre.getName() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		genre.setId(id);
		try {
			adminService.updateGenre(genre);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/genres/{id}")
	public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
		try {
			Optional<Genre> optGenre = adminService.readGenreById(id);
			if (!optGenre.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			adminService.deleteGenre(optGenre.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/borrowers")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Borrower>> getBorrowers() {
		try {
			List<Borrower> borrowers = adminService.readBorrower();
			return new ResponseEntity<List<Borrower>>(borrowers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/borrowers/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Borrower> getBorrower(@PathVariable Long id) {
		try {
			Optional<Borrower> borrower = adminService.readBorrowerById(id);
			if (!borrower.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Borrower>(borrower.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/borrowers")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBorrower(@RequestBody Borrower borrower) {
		if (borrower == null || borrower.getAddress() == null || borrower.getName() == null
				|| borrower.getPhone() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveBorrower(borrower);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(borrower.getCardNumber()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/borrowers/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBorrower(@RequestBody Borrower borrower, @PathVariable Long id) {
		if (borrower == null || borrower.getAddress() == null || borrower.getName() == null
				|| borrower.getPhone() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		borrower.setCardNumber(id);
		try {
			adminService.updateBorrower(borrower);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/borrowers/{id}")
	public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
		try {
			Optional<Borrower> optBorrower = adminService.readBorrowerById(id);
			if (!optBorrower.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			adminService.deleteBorrower(optBorrower.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/branches")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<LibraryBranch>> getBranchs() {
		try {
			List<LibraryBranch> branchs = adminService.readBranch();
			return new ResponseEntity<List<LibraryBranch>>(branchs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/branches/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<LibraryBranch> getBranch(@PathVariable Long id) {
		try {
			Optional<LibraryBranch> branch = adminService.readBranchById(id);
			if (!branch.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<LibraryBranch>(branch.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/branches")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBranch(@RequestBody LibraryBranch branch) {
		if (branch == null || branch.getName() == null || branch.getAddress() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveBranch(branch);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(branch.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/branches/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateLibraryBranch(@RequestBody LibraryBranch branch, @PathVariable Long id) {
		if (branch == null || branch.getName() == null || branch.getAddress() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		branch.setId(id);
		try {
			adminService.updateBranch(branch);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/branches/{id}")
	public ResponseEntity<Void> deleteLibraryBranch(@PathVariable Long id) {
		try {
			Optional<LibraryBranch> optBranch = adminService.readBranchById(id);
			if (!optBranch.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			adminService.deleteBranch(optBranch.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/copies")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<BookCopy>> getBookCopy() {
		try {
			List<BookCopy> copies = adminService.readCopies();
			return new ResponseEntity<List<BookCopy>>(copies, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/copies/{branchId}/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BookCopy> getCopy(@PathVariable Long branchId, @PathVariable Long bookId) {
		try {
			Optional<BookCopy> copies = adminService.readCopyById(branchId, bookId);
			if (!copies.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<BookCopy>(copies.get(), HttpStatus.OK);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/copies")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBookCopy(@RequestBody BookCopy copies) {
		if (copies.getId() == null || copies.getId() == null || copies.getAmount() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveCopies(copies);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/branchId}/{bookId}")
					.buildAndExpand(copies.getId(), copies.getId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/copies/{branchId}/{bookId}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBookCopy(@RequestBody BookCopy copies, @PathVariable Long branchId,
			@PathVariable Long bookId) {
		if (copies.getAmount() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BookCopyId bookCopyId = new BookCopyId(bookId, branchId);
		copies.setId(bookCopyId);
		try {
			adminService.updateCopies(copies);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/copies/{brancId}/{bookId}")
	public ResponseEntity<Void> deleteBookCopy(@PathVariable Long branchId, @PathVariable Long bookId) {
		try {
			Optional<BookCopy> optBookCopy = adminService.readCopyById(branchId, bookId);
			if (!optBookCopy.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			adminService.deleteCopies(optBookCopy.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/publishers")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Publisher>> getPublishers() {
		try {
			List<Publisher> publishers = adminService.readPublisher();
			return new ResponseEntity<List<Publisher>>(publishers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/publishers/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Publisher> getPublisher(@PathVariable Long id) {
		try {
			Optional<Publisher> publisher = adminService.readPublisherById(id);
			if (!publisher.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Publisher>(publisher.get(), HttpStatus.OK);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/publishers")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> savePublisher(@RequestBody Publisher publisher) {
		if (publisher == null || publisher.getName() == null || publisher.getAddress() == null
				|| publisher.getPhone() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.savePublisher(publisher);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(publisher.getId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/publishers/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updatePublisher(@RequestBody Publisher publisher, @PathVariable Long id) {
		if (publisher == null || publisher.getName() == null || publisher.getAddress() == null
				|| publisher.getPhone() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		publisher.setId(id);
		try {
			adminService.updatePublisher(publisher);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/publishers/{id}")
	public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
		try {
			Optional<Publisher> optPublisher = adminService.readPublisherById(id);
			if (!optPublisher.isPresent())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			adminService.deletePublisher(optPublisher.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}