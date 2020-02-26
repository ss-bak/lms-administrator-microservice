package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Borrower;
import com.smoothstack.lms.common.service.BorrowerCommonService;

@Service
public class BorrowerService extends BorrowerCommonService {
	
	@Transactional
	public Borrower save(Borrower borrower) {
		if (!beforeSave(borrower))
			return borrower;
		getJpaRepository().save(borrower);
		afterSave(borrower);
		return (borrower);
	}
	
	@Transactional
	public void deleteById(Long borrowerId) {
		if (!beforeDeleteById(borrowerId))
			return;
		getJpaRepository().deleteById(borrowerId);
		afterDeleteById(borrowerId);
	}
	
	@Transactional
	public int update(Borrower borrower) {
		int createdOrUpdated = 0;
		if (findById(borrower.getBorrowerId()).isPresent())
			createdOrUpdated = 204;
		else
			createdOrUpdated = 201;
		save(borrower);
		return createdOrUpdated;
	}
}
