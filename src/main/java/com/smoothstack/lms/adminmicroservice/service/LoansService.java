package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Loans;
import com.smoothstack.lms.common.service.LoansCommonService;

@Service
public class LoansService extends LoansCommonService {
	
	@Transactional
	public Loans save(Loans loans) {
		if (!beforeSave(loans))
			return loans;
		getJpaRepository().save(loans);
		afterSave(loans);
		return (loans);
	}
	
	@Transactional
	public void deleteById(Long loansId) {
		if (!beforeDeleteById(loansId))
			return;
		getJpaRepository().deleteById(loansId);
		afterDeleteById(loansId);
	}
	
	@Transactional
	public int update(Loans loans) {
		int createdOrUpdated = 0;
		if (findById(loans.getTransactionId()).isPresent())
			createdOrUpdated = 204;
		else
			createdOrUpdated = 201;
		save(loans);
		return createdOrUpdated;
	}
}
