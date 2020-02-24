package com.smoothstack.lms.adminmicroservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "tbl_library_branch")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = LibraryBranch.class)
public class LibraryBranch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branchId")
	private Long id;

	@Column(name = "branchName")
	@NotNull
	@NotBlank
	private String name;

	@Column(name = "branchAddress")
	@NotNull
	@NotBlank
	private String address;

	@ManyToMany
	@JoinTable(name = "tbl_book_loans", joinColumns = { @JoinColumn(name = "branchId") }, inverseJoinColumns = {
			@JoinColumn(name = "bookId") })
	private List<Book> books;

	@ManyToMany
	@JoinTable(name = "tbl_book_loans", joinColumns = { @JoinColumn(name = "branchId") }, inverseJoinColumns = {
			@JoinColumn(name = "cardNo") })
	private List<Borrower> borrowers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Borrower> getBorrowers() {
		return borrowers;
	}

	public void setBorrowers(List<Borrower> borrowers) {
		this.borrowers = borrowers;
	}

}
