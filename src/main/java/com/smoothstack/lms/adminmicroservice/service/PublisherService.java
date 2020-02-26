package com.smoothstack.lms.adminmicroservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Publisher;
import com.smoothstack.lms.common.service.PublisherCommonService;

@Service
public class PublisherService extends PublisherCommonService {
	
	@Transactional
	public Publisher save(Publisher publisher) {
		if (!beforeSave(publisher))
			return publisher;
		getJpaRepository().save(publisher);
		afterSave(publisher);
		return (publisher);
	}
	
	@Transactional
	public void deleteById(Long publisherId) {
		if (!beforeDeleteById(publisherId))
			return;
		getJpaRepository().deleteById(publisherId);
		afterDeleteById(publisherId);
	}
	
	@Transactional
	public int update(Publisher publisher) {
		int createdOrUpdated = 0;
		if (findById(publisher.getPublisherId()).isPresent())
			createdOrUpdated = 204;
		else
			createdOrUpdated = 201;
		save(publisher);
		return createdOrUpdated;
	}
}
