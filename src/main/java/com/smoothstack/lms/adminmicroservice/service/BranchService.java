package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.service.BranchCommonService;

@Service
public class BranchService extends BranchCommonService {
	
	@Transactional
	public Branch save(Branch branch) {
		if (!beforeSave(branch))
			return branch;
		getJpaRepository().save(branch);
		afterSave(branch);
		return (branch);
	}

	@Transactional
	public void deleteById(Long branchId) {
		if (!beforeDeleteById(branchId))
			return;
		getJpaRepository().deleteById(branchId);
		afterDeleteById(branchId);
	}
	
	@Transactional
	public int update(Branch branch) {
		int createdOrUpdated = 0;
		if (findById(branch.getBranchId()).isPresent())
			createdOrUpdated = 204;
		else
			createdOrUpdated = 201;
		save(branch);
		return createdOrUpdated;
	}
}
