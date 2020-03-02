package com.smoothstack.lms.adminmicroservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.service.BookCommonService;

@Service
public class BookService extends BookCommonService {
	
	@Transactional
	public Book save(Book book) {
		return super.save(book);
	}
	
	@Transactional
	public void delete(Book book) {
		super.delete(book);
	}
	
	@Transactional
	public Book update(Book book) {
		findByIdOrThrow(book.getBookId());
		return super.save(book);
	}
}
