package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.service.BranchCommonService;

@Service
public class BranchService extends BranchCommonService {
	
	@Transactional
	public Branch save(Branch branch) {
		return super.save(branch);
	}
	
	@Transactional
	public void delete(Branch branch) {
		super.delete(branch);
	}
	
	@Transactional
	public Branch update(Branch branch) {
		findByIdOrThrow(branch.getBranchId());
		return super.save(branch);
	}
}
