package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Author;
import com.smoothstack.lms.common.service.AuthorCommonService;

@Service
public class AuthorService extends AuthorCommonService {
	
	@Transactional
	public Author save(Author author) {
		return super.save(author);
	}
	
	@Transactional
	public void delete(Author author) {
		super.delete(author);
	}
	
	@Transactional
	public Author update(Author author) {
		findByIdOrThrow(author.getAuthorId());
		return super.save(author);
	}
	
}
