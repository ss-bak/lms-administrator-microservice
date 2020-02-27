package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Copies;
import com.smoothstack.lms.common.service.CopiesCommonService;

@Service
public class CopiesService extends CopiesCommonService {
	
	@Transactional
	public Copies save(Copies copies) {
		return super.save(copies);
	}
	
	@Transactional
	public void delete(Copies copies) {
		super.delete(copies);
	}
	
	@Transactional
	public Copies update(Copies copies) {
		findByIdOrThrow(copies.getBook().getBookId(), copies.getBranch().getBranchId());
		return super.save(copies);
	}
}
