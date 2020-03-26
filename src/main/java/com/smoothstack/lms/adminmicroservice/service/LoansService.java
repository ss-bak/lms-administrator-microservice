package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Loans;
import com.smoothstack.lms.common.service.LoansCommonService;

@Service
public class LoansService extends LoansCommonService {
	
	@Transactional
	public Loans save(Loans loans) {
		return super.save(loans);
	}
	
	@Transactional
	public void delete(Loans loans) {
		super.delete(loans);
	}
	
	@Transactional
	public Loans update(Loans loans) {
		findByIdOrThrow(loans.getTransactionId());
		return super.save(loans);
	}
}
