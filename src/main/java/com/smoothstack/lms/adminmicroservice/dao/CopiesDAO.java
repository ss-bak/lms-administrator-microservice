package com.smoothstack.lms.adminmicroservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smoothstack.lms.adminmicroservice.model.BookCopy;
import com.smoothstack.lms.adminmicroservice.model.BookCopyId;

public interface CopiesDAO extends JpaRepository<BookCopy, BookCopyId> {

}
