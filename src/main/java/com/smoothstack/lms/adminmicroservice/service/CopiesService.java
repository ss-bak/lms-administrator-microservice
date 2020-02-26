package com.smoothstack.lms.adminmicroservice.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.BookLibraryBranch;
import com.smoothstack.lms.common.model.Copies;
import com.smoothstack.lms.common.service.CopiesCommonService;

@Service
public class CopiesService extends CopiesCommonService {
	
	@Transactional
	public Copies save(Copies copies) {
		if (!beforeSave(copies))
			return copies;
		getJpaRepository().save(copies);
		afterSave(copies);
		return (copies);
	}
	
	@Transactional
	public int update(Copies copies) {
		int createdOrUpdated = 0;
		if (findById(new BookLibraryBranch(copies.getBook().getBookId(), copies.getBranch().getBranchId())).isPresent())
			createdOrUpdated = 204;
		else
			createdOrUpdated = 201;
		save(copies);
		return createdOrUpdated;
	}
	
	@Transactional
	public Optional<Copies> findById(Long branchId, Long bookId) {
		return findById(new BookLibraryBranch(bookId, branchId));
	}
	
	@Transactional
	public void deleteById(Long branchId, Long bookId) {
		deleteById(new BookLibraryBranch(bookId, branchId));
	}
}
