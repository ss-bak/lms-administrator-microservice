package com.smoothstack.lms.adminmicroservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smoothstack.lms.adminmicroservice.model.Book;

public interface BookDAO extends JpaRepository<Book, Long> {

}
