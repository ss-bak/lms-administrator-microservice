package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.service.GenreCommonService;

@Service
public class GenreService extends GenreCommonService {
	
	@Transactional
	public Genre save(Genre genre) {
		return super.save(genre);
	}
	
	@Transactional
	public void delete(Genre genre) {
		super.delete(genre);
	}
	
	@Transactional
	public Genre update(Genre genre) {
		findByIdOrThrow(genre.getGenreId());
		return super.save(genre);
	}
}
