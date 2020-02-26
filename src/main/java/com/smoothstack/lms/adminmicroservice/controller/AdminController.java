package com.smoothstack.lms.adminmicroservice.controller;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smoothstack.lms.adminmicroservice.service.AuthorService;
import com.smoothstack.lms.adminmicroservice.service.BookService;
import com.smoothstack.lms.adminmicroservice.service.BorrowerService;
import com.smoothstack.lms.adminmicroservice.service.BranchService;
import com.smoothstack.lms.adminmicroservice.service.CopiesService;
import com.smoothstack.lms.adminmicroservice.service.GenreService;
import com.smoothstack.lms.adminmicroservice.service.LoansService;
import com.smoothstack.lms.adminmicroservice.service.PublisherService;
import com.smoothstack.lms.common.model.Author;
import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Borrower;
import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.model.Copies;
import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.model.Publisher;

@RestController
public class AdminController {

	@Autowired
	AuthorService authorService;

	@Autowired
	BookService bookService;

	@Autowired
	BranchService branchService;

	@Autowired
	CopiesService copiesService;

	@Autowired
	GenreService genreService;

	@Autowired
	LoansService loansService;

	@Autowired
	PublisherService publisherService;

	@Autowired
	BorrowerService borrowerService;

//	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId())
//			.toUri();
//	return ResponseEntity.created(location).build();

	@GetMapping(path = "/administrator/books")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {
		return new ResponseEntity<List<Book>>(bookService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/administrator/books/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Book> getBook(@PathVariable Long id) {
		return new ResponseEntity<Book>(bookService.findByIdOrThrow(id), HttpStatus.OK);
	}

	@PostMapping(path = "administrator/books")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBook(@RequestBody Book book) {
		book.setPublisher(publisherService.findByIdOrThrow(book.getPublisher().getPublisherId()));
		book.getBookAuthorSet().forEach((Author author) -> author
				.setAuthorName(authorService.findByIdOrThrow(author.getAuthorId()).getAuthorName()));
		book.getBookGenreSet().forEach(
				(Genre genre) -> genre.setGenreName(genreService.findByIdOrThrow(genre.getGenreId()).getGenreName()));
		if (!bookService.isValid(book))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		bookService.save(book);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getBookId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "administrator/books/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBook(@RequestBody Book book, @PathVariable Long id) {
		if (!bookService.isValid(book))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		int createdOrUpdated = bookService.update(book);
		if (createdOrUpdated == 204)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		else {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(book.getBookId()).toUri();
			return ResponseEntity.created(location).build();
		}
	}

	@DeleteMapping(path = "administrator/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/administrator/authors")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Author>> getAuthors() {
		return new ResponseEntity<List<Author>>(authorService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/administrator/authors/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
		return new ResponseEntity<Author>(authorService.findByIdOrThrow(id), HttpStatus.OK);
	}

	@PostMapping(path = "administrator/authors")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveAuthor(@RequestBody Author author) {
		author.getAuthorBookSet().forEach((Book book) -> {
			book = bookService.findByIdOrThrow(book.getBookId());
		});
		if (!authorService.isValid(author))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		authorService.save(author);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(author.getAuthorId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "administrator/authors/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateAuthor(@RequestBody Author author, @PathVariable Long id) {
		if (!authorService.isValid(author))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		int createdOrUpdated = authorService.update(author);
		if (createdOrUpdated == 204)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		else {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(author.getAuthorId()).toUri();
			return ResponseEntity.created(location).build();
		}
	}

	@DeleteMapping(path = "administrator/authors/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		authorService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/administrator/borrowers")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Borrower>> getBorrowers() {
		return new ResponseEntity<List<Borrower>>(borrowerService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/administrator/borrowers/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Borrower> getBorrower(@PathVariable Long id) {
		return new ResponseEntity<Borrower>(borrowerService.findByIdOrThrow(id), HttpStatus.OK);
	}

	@PostMapping(path = "administrator/borrowers")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBorrower(@RequestBody Borrower borrower) {
		if (!borrowerService.isValid(borrower))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		if (!borrowerService.isValid(borrower))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		borrowerService.save(borrower);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(borrower.getBorrowerId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "administrator/borrowers/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBorrower(@RequestBody Borrower borrower, @PathVariable Long id) {
		if (!borrowerService.isValid(borrower))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		int createdOrUpdated = borrowerService.update(borrower);
		if (createdOrUpdated == 204)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		else {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(borrower.getBorrowerId()).toUri();
			return ResponseEntity.created(location).build();
		}
	}

	@DeleteMapping(path = "administrator/borrowers/{id}")
	public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
		borrowerService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/administrator/branches")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Branch>> getBranchs() {
		return new ResponseEntity<List<Branch>>(branchService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/administrator/branches/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Branch> getBranch(@PathVariable Long id) {
		return new ResponseEntity<Branch>(branchService.findByIdOrThrow(id), HttpStatus.OK);
	}

	@PostMapping(path = "administrator/branches")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBranch(@RequestBody Branch branch) {
		if (!branchService.isValid(branch))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		branchService.save(branch);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(branch.getBranchId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "administrator/branches/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBranch(@RequestBody Branch branch, @PathVariable Long id) {
		if (!branchService.isValid(branch))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		int createdOrUpdated = branchService.update(branch);
		if (createdOrUpdated == 204)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		else {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(branch.getBranchId()).toUri();
			return ResponseEntity.created(location).build();
		}
	}

	@DeleteMapping(path = "administrator/branches/{id}")
	public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
		branchService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/administrator/copies")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Copies>> getCopiess() {
		return new ResponseEntity<List<Copies>>(copiesService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/administrator/copies/BranchID/{branchId}/BookID/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Copies> getCopies(@PathVariable Long branchId, @PathVariable Long bookId) {
		return new ResponseEntity<Copies>(copiesService.findById(branchId, bookId).get(), HttpStatus.OK);
	}

	@PostMapping(path = "administrator/copies")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveCopies(@RequestBody Copies copies) {
		if (!copiesService.isValid(copies))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		copiesService.save(copies);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/BranchID/{branchId}/BookID/{bookId}")
				.buildAndExpand(copies.getBranch().getBranchId(), copies.getBook().getBookId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "administrator/copies/BranchID/{branchId}/BookID/{bookId}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateCopies(@RequestBody Copies copies, @PathVariable Long id) {
		if (!copiesService.isValid(copies))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		int createdOrUpdated = copiesService.update(copies);
		if (createdOrUpdated == 204)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		else {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/BranchID/{branchId}/BookID/{bookId}")
					.buildAndExpand(copies.getBranch().getBranchId(), copies.getBook().getBookId()).toUri();
			return ResponseEntity.created(location).build();
		}
	}

	@DeleteMapping(path = "administrator/copies/BranchID/{branchId}/BookID/{bookId}")
	public ResponseEntity<Void> deleteCopies(@PathVariable Long branchId, @PathVariable Long bookId) {
		copiesService.deleteById(branchId, bookId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/administrator/genres")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Genre>> getGenres() {
		return new ResponseEntity<List<Genre>>(genreService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/administrator/genres/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Genre> getGenre(@PathVariable Long id) {
		return new ResponseEntity<Genre>(genreService.findByIdOrThrow(id), HttpStatus.OK);
	}

	@PostMapping(path = "administrator/genres")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveGenre(@RequestBody Genre genre) {
		if (!genreService.isValid(genre))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		genreService.save(genre);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(genre.getGenreId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "administrator/genres/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateGenre(@RequestBody Genre genre, @PathVariable Long id) {
		if (!genreService.isValid(genre))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		int createdOrUpdated = genreService.update(genre);
		if (createdOrUpdated == 204)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		else {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(genre.getGenreId()).toUri();
			return ResponseEntity.created(location).build();
		}
	}

	@DeleteMapping(path = "administrator/genres/{id}")
	public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
		genreService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/administrator/publishers")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Publisher>> getPublishers() {
		return new ResponseEntity<List<Publisher>>(publisherService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/administrator/publishers/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Publisher> getPublisher(@PathVariable Long id) {
		return new ResponseEntity<Publisher>(publisherService.findByIdOrThrow(id), HttpStatus.OK);
	}

	@PostMapping(path = "administrator/publishers")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> savePublisher(@RequestBody Publisher publisher) {
		if (!publisherService.isValid(publisher))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		publisherService.save(publisher);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(publisher.getPublisherId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "administrator/publishers/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updatePublisher(@RequestBody Publisher publisher, @PathVariable Long id) {
		if (!publisherService.isValid(publisher))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		int createdOrUpdated = publisherService.update(publisher);
		if (createdOrUpdated == 204)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		else {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(publisher.getPublisherId()).toUri();
			return ResponseEntity.created(location).build();
		}
	}

	@DeleteMapping(path = "administrator/publishers/{id}")
	public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
		publisherService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}