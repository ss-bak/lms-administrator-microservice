package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.service.GenreCommonService;

@Service
public class GenreService extends GenreCommonService {
	
	@Transactional
	public Genre save(Genre genre) {
		if (!beforeSave(genre))
			return genre;
		getJpaRepository().save(genre);
		afterSave(genre);
		return (genre);
	}
	
	@Transactional
	public void deleteById(Long genreId) {
		if (!beforeDeleteById(genreId))
			return;
		getJpaRepository().deleteById(genreId);
		afterDeleteById(genreId);
	}
	
	@Transactional
	public int update(Genre genre) {
		int createdOrUpdated = 0;
		if (findById(genre.getGenreId()).isPresent())
			createdOrUpdated = 204;
		else
			createdOrUpdated = 201;
		save(genre);
		return createdOrUpdated;
	}
}
