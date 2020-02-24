package com.smoothstack.lms.adminmicroservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smoothstack.lms.adminmicroservice.model.Borrower;

public interface BorrowerDAO extends JpaRepository<Borrower, Long> {

}
