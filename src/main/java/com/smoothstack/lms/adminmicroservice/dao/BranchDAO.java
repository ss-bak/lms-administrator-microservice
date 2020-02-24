package com.smoothstack.lms.adminmicroservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smoothstack.lms.adminmicroservice.model.LibraryBranch;

public interface BranchDAO extends JpaRepository<LibraryBranch, Long> {

}
