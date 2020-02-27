package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Borrower;
import com.smoothstack.lms.common.service.BorrowerCommonService;

@Service
public class BorrowerService extends BorrowerCommonService {
	
	@Transactional
	public Borrower save(Borrower borrower) {
		return super.save(borrower);
	}
	
	@Transactional
	public void delete(Borrower borrower) {
		super.delete(borrower);
	}
	
	@Transactional
	public Borrower update(Borrower borrower) {
		findByIdOrThrow(borrower.getBorrowerId());
		return super.save(borrower);
	}
}
