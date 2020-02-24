package com.smoothstack.lms.adminmicroservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smoothstack.lms.adminmicroservice.model.Publisher;

public interface PublisherDAO extends JpaRepository<Publisher, Long> {

}
