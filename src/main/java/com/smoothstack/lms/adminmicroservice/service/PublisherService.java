package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Publisher;
import com.smoothstack.lms.common.service.PublisherCommonService;

@Service
public class PublisherService extends PublisherCommonService {
	
	@Transactional
	public Publisher save(Publisher publisher) {
		return super.save(publisher);
	}
	
	@Transactional
	public void delete(Publisher publisher) {
		super.delete(publisher);
	}
	
	@Transactional
	public Publisher update(Publisher publisher) {
		findByIdOrThrow(publisher.getPublisherId());
		return super.save(publisher);
	}
}
