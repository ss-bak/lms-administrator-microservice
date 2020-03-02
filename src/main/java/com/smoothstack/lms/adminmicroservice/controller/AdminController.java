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
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.smoothstack.lms.common.exception.DependencyException;
import com.smoothstack.lms.common.exception.RecordNotFoundException;
import com.smoothstack.lms.common.model.Author;
import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Borrower;
import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.model.Copies;
import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.model.Publisher;

@RequestMapping("/administrator")
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

	@GetMapping(path = "/books")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {
		return new ResponseEntity<List<Book>>(bookService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/books/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Book> getBook(@PathVariable Long id) {
		try {
			return new ResponseEntity<Book>(bookService.findByIdOrThrow(id), HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/books")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBook(@RequestBody Book book) {

		Book newBook = new Book();
		newBook.setBookTitle(book.getBookTitle());
		try {
			if (book.getPublisher().getPublisherId() != 0)
				newBook.setPublisher(publisherService.findByIdOrThrow(book.getPublisher().getPublisherId()));
			else
				newBook.setPublisher(book.getPublisher());
			book.getBookAuthorSet().forEach((Author author) -> {
				if (author.getAuthorId() != 0)
					newBook.getBookAuthorSet().add(authorService.findByIdOrThrow(author.getAuthorId()));
				else
					newBook.getBookAuthorSet().add(author);
			});
			book.getBookGenreSet().forEach((Genre genre) -> {
				if (genre.getGenreId() != 0)
					newBook.getBookGenreSet().add(genreService.findByIdOrThrow(genre.getGenreId()));
				else
					newBook.getBookGenreSet().add(genre);
			});
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (!bookService.isValid(newBook))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		bookService.save(newBook);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newBook.getBookId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/books/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBook(@RequestBody Book book, @PathVariable Long id) {
		Book newBook = new Book();
		newBook.setBookId(book.getBookId());
		newBook.setBookTitle(book.getBookTitle());
		try {
			if (book.getPublisher().getPublisherId() != 0)
				newBook.setPublisher(publisherService.findByIdOrThrow(book.getPublisher().getPublisherId()));
			else
				newBook.setPublisher(book.getPublisher());
			book.getBookAuthorSet().forEach((Author author) -> {
				if (author.getAuthorId() != 0)
					newBook.getBookAuthorSet().add(authorService.findByIdOrThrow(author.getAuthorId()));
				else
					newBook.getBookAuthorSet().add(author);
			});
			book.getBookGenreSet().forEach((Genre genre) -> {
				if (genre.getGenreId() != 0)
					newBook.getBookGenreSet().add(genreService.findByIdOrThrow(genre.getGenreId()));
				else
					newBook.getBookGenreSet().add(genre);
			});
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		if (!bookService.isValid(newBook))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		try {
			bookService.update(newBook);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBook.getBookId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(path = "/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		try {
			bookService.delete(bookService.findByIdOrThrow(id));
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DependencyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/authors")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Author>> getAuthors() {
		return new ResponseEntity<List<Author>>(authorService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/authors/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
		try {
			return new ResponseEntity<Author>(authorService.findByIdOrThrow(id), HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/authors")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveAuthor(@RequestBody Author author) {
		Author newAuthor = new Author();
		newAuthor.setAuthorName(author.getAuthorName());
		try {
			author.getAuthorBookSet().forEach((Book book) -> {
				if (book.getBookId() != 0)
					newAuthor.getAuthorBookSet().add(bookService.findByIdOrThrow(book.getBookId()));
				else
					newAuthor.getAuthorBookSet().add(book);
			});
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		if (!authorService.isValid(author))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		authorService.save(author);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(author.getAuthorId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/authors/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateAuthor(@RequestBody Author author, @PathVariable Long id) {
		Author newAuthor = new Author();
		newAuthor.setAuthorName(author.getAuthorName());
		newAuthor.setAuthorId(author.getAuthorId());
		try {
			author.getAuthorBookSet().forEach((Book book) -> {
				if (book.getBookId() != 0)
					newAuthor.getAuthorBookSet().add(bookService.findByIdOrThrow(book.getBookId()));
				else
					newAuthor.getAuthorBookSet().add(book);
			});
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		if (!authorService.isValid(author))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		try {
			authorService.update(author);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(author.getAuthorId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(path = "/authors/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		try {
			authorService.delete(authorService.findByIdOrThrow(id));
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DependencyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/borrowers")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Borrower>> getBorrowers() {
		return new ResponseEntity<List<Borrower>>(borrowerService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/borrowers/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Borrower> getBorrower(@PathVariable Long id) {
		try {
			return new ResponseEntity<Borrower>(borrowerService.findByIdOrThrow(id), HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/borrowers")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBorrower(@RequestBody Borrower borrower) {
		if (!borrowerService.isValid(borrower))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		borrowerService.save(borrower);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(borrower.getBorrowerId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/borrowers/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBorrower(@RequestBody Borrower borrower, @PathVariable Long id) {
		if (!borrowerService.isValid(borrower))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		try {
			borrowerService.update(borrower);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(borrower.getBorrowerId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(path = "/borrowers/{id}")
	public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
		try {
			borrowerService.delete(borrowerService.findByIdOrThrow(id));
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DependencyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/branches")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Branch>> getBranchs() {
		return new ResponseEntity<List<Branch>>(branchService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/branches/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Branch> getBranch(@PathVariable Long id) {
		try {
			return new ResponseEntity<Branch>(branchService.findByIdOrThrow(id), HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/branches")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBranch(@RequestBody Branch branch) {
		if (!branchService.isValid(branch))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		branchService.save(branch);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(branch.getBranchId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/branches/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBranch(@RequestBody Branch branch, @PathVariable Long id) {
		if (!branchService.isValid(branch))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		try {
			branchService.update(branch);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(branch.getBranchId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(path = "/branches/{id}")
	public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
		try {
			branchService.delete(branchService.findByIdOrThrow(id));
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DependencyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/copies")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Copies>> getCopiess() {
		return new ResponseEntity<List<Copies>>(copiesService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/copies/BranchID/{branchId}/BookID/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Copies> getCopies(@PathVariable Long branchId, @PathVariable Long bookId) {
		try {
			return new ResponseEntity<Copies>(copiesService.findById(branchId, bookId).get(), HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/copies")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveCopies(@RequestBody Copies copies) {
			System.out.println(copies);
		if (!copiesService.isValid(copies))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		copiesService.save(copies);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/BranchID/{branchId}/BookID/{bookId}")
				.buildAndExpand(copies.getBranch().getBranchId(), copies.getBook().getBookId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/copies/BranchID/{branchId}/BookID/{bookId}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateCopies(@RequestBody Copies copies, @PathVariable Long id) {
		if (!copiesService.isValid(copies))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		try {
			copiesService.update(copies);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/BranchID/{branchId}/BookID/{bookId}")
				.buildAndExpand(copies.getBranch().getBranchId(), copies.getBook().getBookId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(path = "/copies/BranchID/{branchId}/BookID/{bookId}")
	public ResponseEntity<Void> deleteCopies(@PathVariable Long branchId, @PathVariable Long bookId) {
		try {
			copiesService.delete(copiesService.findByIdOrThrow(bookId, branchId));
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DependencyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/genres")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Genre>> getGenres() {
		return new ResponseEntity<List<Genre>>(genreService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/genres/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Genre> getGenre(@PathVariable Long id) {
		try {
			return new ResponseEntity<Genre>(genreService.findByIdOrThrow(id), HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/genres")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveGenre(@RequestBody Genre genre) {
		Genre newGenre = new Genre();
		newGenre.setGenreName(genre.getGenreName());
		try {
			genre.getGenreBookSet().forEach((Book book) -> {
				if (book.getBookId() != 0)
					newGenre.getGenreBookSet().add(bookService.findByIdOrThrow(book.getBookId()));
				else
					newGenre.getGenreBookSet().add(book);
			});
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		if (!genreService.isValid(genre))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		if (!genreService.isValid(genre))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		genreService.save(genre);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(genre.getGenreId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/genres/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateGenre(@RequestBody Genre genre, @PathVariable Long id) {
		Genre newGenre = new Genre();
		newGenre.setGenreName(genre.getGenreName());
		newGenre.setGenreId(genre.getGenreId());
		try {
			genre.getGenreBookSet().forEach((Book book) -> {
				if (book.getBookId() != 0)
					newGenre.getGenreBookSet().add(bookService.findByIdOrThrow(book.getBookId()));
				else
					newGenre.getGenreBookSet().add(book);
			});
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		if (!genreService.isValid(genre))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		if (!genreService.isValid(genre))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		try {
			genreService.update(genre);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(genre.getGenreId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(path = "/genres/{id}")
	public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
		try {
			genreService.delete(genreService.findByIdOrThrow(id));
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DependencyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/publishers")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Publisher>> getPublishers() {
		return new ResponseEntity<List<Publisher>>(publisherService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/publishers/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Publisher> getPublisher(@PathVariable Long id) {
		try {
			return new ResponseEntity<Publisher>(publisherService.findByIdOrThrow(id), HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/publishers")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> savePublisher(@RequestBody Publisher publisher) {
		Publisher newPublisher = new Publisher();
		newPublisher.setPublisherName(publisher.getPublisherName());
		newPublisher.setPublisherAddress(publisher.getPublisherAddress());
		newPublisher.setPublisherPhone(publisher.getPublisherPhone());
		try {
			publisher.getPublisherBookSet().forEach((Book book) -> {
				if (book.getBookId() != 0)
					newPublisher.getPublisherBookSet().add(bookService.findByIdOrThrow(book.getBookId()));
				else
					newPublisher.getPublisherBookSet().add(book);
			});
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		if (!publisherService.isValid(publisher))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		publisherService.save(publisher);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(publisher.getPublisherId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/publishers/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updatePublisher(@RequestBody Publisher publisher, @PathVariable Long id) {
		Publisher newPublisher = new Publisher();
		newPublisher.setPublisherName(publisher.getPublisherName());
		newPublisher.setPublisherId(publisher.getPublisherId());
		newPublisher.setPublisherAddress(publisher.getPublisherAddress());
		newPublisher.setPublisherPhone(publisher.getPublisherPhone());
		try {
			publisher.getPublisherBookSet().forEach((Book book) -> {
				if (book.getBookId() != 0)
					newPublisher.getPublisherBookSet().add(bookService.findByIdOrThrow(book.getBookId()));
				else
					newPublisher.getPublisherBookSet().add(book);
			});
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		if (!publisherService.isValid(publisher))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		try {
			publisherService.update(publisher);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(publisher.getPublisherId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(path = "/publishers/{id}")
	public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
		try {
			publisherService.delete(publisherService.findByIdOrThrow(id));
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DependencyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}