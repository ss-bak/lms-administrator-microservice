package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Author;
import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.service.AuthorCommonService;

@Service
public class AuthorService extends AuthorCommonService {
	
	@Autowired
	private BookService bookService;
	
	@Transactional
	public Author save(Author author) {
		if (!beforeSave(author))
			return author;
		getJpaRepository().save(author);
		for (Book book: author.getAuthorBookSet()) {
			book.getBookAuthorSet().add(author);
			bookService.save(book);
		}
		afterSave(author);
		return (author);
	}
	
	@Transactional
	public void deleteById(Long authorId) {
		if (!beforeDeleteById(authorId))
			return;
		Author author = findById(authorId).get();
		for (Book book: author.getAuthorBookSet()) {
			book.getBookAuthorSet().remove(author);
			if (book.getBookAuthorSet().isEmpty())
				bookService.delete(book);
			else
				bookService.save(book);
		}
		getJpaRepository().deleteById(authorId);
		afterDeleteById(authorId);
	}
	
	@Transactional
	public int update(Author author) {
		int createdOrUpdated = 0;
		if (findById(author.getAuthorId()).isPresent())
			createdOrUpdated = 204;
		else
			createdOrUpdated = 201;
		save(author);
		return createdOrUpdated;
	}
	
	@Transactional
	public void delete(Author author) {
		for (Book book: author.getAuthorBookSet()) {
			book.getBookAuthorSet().remove(author);
			if (book.getBookAuthorSet().isEmpty())
				bookService.delete(book);
			else
				bookService.save(book);
		}
		getJpaRepository().deleteById(author.getAuthorId());
	}
}
