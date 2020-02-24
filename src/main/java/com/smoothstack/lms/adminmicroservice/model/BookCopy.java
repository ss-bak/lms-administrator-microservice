package com.smoothstack.lms.adminmicroservice.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "tbl_book_copies")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = BookCopy.class)
public class BookCopy {

	@EmbeddedId
	@Valid
	@NotNull
	private BookCopyId id;

	@Column(name = "noOfCopies")
	@NotNull
	@Min(0)
	private Integer amount;

	public BookCopyId getId() {
		return id;
	}

	public void setId(BookCopyId id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
