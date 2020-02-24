package com.smoothstack.lms.adminmicroservice.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smoothstack.lms.adminmicroservice.dao.AuthorDAO;
import com.smoothstack.lms.adminmicroservice.dao.BookDAO;
import com.smoothstack.lms.adminmicroservice.dao.BorrowerDAO;
import com.smoothstack.lms.adminmicroservice.dao.BranchDAO;
import com.smoothstack.lms.adminmicroservice.dao.CopiesDAO;
import com.smoothstack.lms.adminmicroservice.dao.GenreDAO;
import com.smoothstack.lms.adminmicroservice.dao.PublisherDAO;
import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.BookCopy;
import com.smoothstack.lms.adminmicroservice.model.BookCopyId;
import com.smoothstack.lms.adminmicroservice.model.Borrower;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.LibraryBranch;
import com.smoothstack.lms.adminmicroservice.model.Publisher;

@Service
public class AdminService {

	@Autowired
	AuthorDAO authorDao;

	@Autowired
	BookDAO bookDao;

	@Autowired
	GenreDAO genreDao;

	@Autowired
	BorrowerDAO borrowerDao;

	@Autowired
	BranchDAO branchDao;

	@Autowired
	CopiesDAO copiesDao;

	@Autowired
	PublisherDAO publisherDao;

	@Transactional
	public void saveAuthor(Author auth) throws SQLException {
		authorDao.save(auth);
	}

	@Transactional
	public void updateAuthor(Author auth) throws SQLException {
		if (authorDao.existsById(auth.getId()))
			authorDao.save(auth);
	}

	@Transactional
	public void deleteAuthor(Author auth) throws SQLException {
		if (authorDao.existsById(auth.getId()))
			authorDao.delete(auth);
	}

	public List<Author> readAuthor() throws SQLException {
		return authorDao.findAll();
	}

	public Optional<Author> readAuthorById(Long authorId) throws SQLException {
		return authorDao.findById(authorId);
	}

	@Transactional
	public void saveBook(Book book) throws SQLException {
		bookDao.save(book);
	}

	@Transactional
	public void updateBook(Book book) throws SQLException {
		if (bookDao.existsById(book.getId()))
			bookDao.save(book);
	}

	@Transactional
	public void deleteBook(Book book) throws SQLException {
		if (bookDao.existsById(book.getId()))
			bookDao.delete(book);
	}

	public List<Book> readBook() throws SQLException {
		return bookDao.findAll();
	}

	public Optional<Book> readBookById(Long bookId) throws SQLException {
		return bookDao.findById(bookId);
	}

	@Transactional
	public void saveGenre(Genre gen) throws SQLException {
		genreDao.save(gen);
	}

	@Transactional
	public void updateGenre(Genre gen) throws SQLException {
		if (genreDao.existsById(gen.getId()))
			genreDao.save(gen);
	}

	@Transactional
	public void deleteGenre(Genre gen) throws SQLException {
		if (genreDao.existsById(gen.getId()))
			genreDao.delete(gen);
	}

	public List<Genre> readGenre() throws SQLException {
		return genreDao.findAll();
	}

	public Optional<Genre> readGenreById(Long genreId) throws SQLException {
		return genreDao.findById(genreId);
	}

	@Transactional
	public void saveBorrower(Borrower bor) throws SQLException {
		borrowerDao.save(bor);
	}

	@Transactional
	public void updateBorrower(Borrower bor) throws SQLException {
		if (borrowerDao.existsById(bor.getCardNumber()))
			borrowerDao.save(bor);
	}

	@Transactional
	public void deleteBorrower(Borrower bor) throws SQLException {
		if (borrowerDao.existsById(bor.getCardNumber()))
			borrowerDao.delete(bor);
	}

	public List<Borrower> readBorrower() throws SQLException {
		return borrowerDao.findAll();
	}

	public Optional<Borrower> readBorrowerById(Long cardNo) throws SQLException {
		return borrowerDao.findById(cardNo);
	}

	@Transactional
	public void saveBranch(LibraryBranch brch) throws SQLException {
		branchDao.save(brch);
	}

	@Transactional
	public void updateBranch(LibraryBranch brch) throws SQLException {
		if (branchDao.existsById(brch.getId()))
			branchDao.save(brch);
	}

	@Transactional
	public void deleteBranch(LibraryBranch brch) throws SQLException {
		if (branchDao.existsById(brch.getId()))
			branchDao.delete(brch);
	}

	public List<LibraryBranch> readBranch() throws SQLException {
		return branchDao.findAll();
	}

	public Optional<LibraryBranch> readBranchById(Long branchId) throws SQLException {
		return branchDao.findById(branchId);
	}

	@Transactional
	public void saveCopies(BookCopy copy) throws SQLException {
		copiesDao.save(copy);
	}

	@Transactional
	public void updateCopies(BookCopy copy) throws SQLException {
		if (copiesDao.existsById(copy.getId()))
			copiesDao.save(copy);
	}

	@Transactional
	public void deleteCopies(BookCopy copy) throws SQLException {
		if (copiesDao.existsById(copy.getId()))
			copiesDao.delete(copy);
	}

	public List<BookCopy> readCopies() throws SQLException {
		return copiesDao.findAll();
	}

	public Optional<BookCopy> readCopyById(Long branchId, Long bookId) throws SQLException {
		BookCopyId bookCopyId = new BookCopyId(bookId, branchId);
		return copiesDao.findById(bookCopyId);
	}

	@Transactional
	public void savePublisher(Publisher pub) throws SQLException {
		publisherDao.save(pub);
	}

	@Transactional
	public void updatePublisher(Publisher pub) throws SQLException {
		if (publisherDao.existsById(pub.getId()))
			publisherDao.save(pub);
	}

	@Transactional
	public void deletePublisher(Publisher pub) throws SQLException {
		if (publisherDao.existsById(pub.getId()))
			publisherDao.delete(pub);
	}

	public List<Publisher> readPublisher() throws SQLException {
		return publisherDao.findAll();
	}

	public Optional<Publisher> readPublisherById(Long publisherId) throws SQLException {
		return publisherDao.findById(publisherId);
	}

}
