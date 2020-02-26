package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.service.BookCommonService;

@Service
public class BookService extends BookCommonService {
	
	@Autowired
	private GenreService genreService;
	
	@Transactional
	public Book save(Book book) {
		if (!beforeSave(book))
			return book;
		getJpaRepository().save(book);
		for (Genre genre: book.getBookGenreSet()) {
			genre.getGenreBookSet().add(book);
			genreService.save(genre);
		}
		afterSave(book);
		return (book);
	}
	
	@Transactional
	public void deleteById(Long bookId) {
		if (!beforeDeleteById(bookId))
			return;
		getJpaRepository().deleteById(bookId);
		afterDeleteById(bookId);
	}
	
	@Transactional
	public int update(Book book) {
		int createdOrUpdated = 0;
		if (findById(book.getBookId()).isPresent())
			createdOrUpdated = 204;
		else
			createdOrUpdated = 201;
		save(book);
		return createdOrUpdated;
	}
}
