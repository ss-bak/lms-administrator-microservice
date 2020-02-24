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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "tbl_book")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Book.class)
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId")
	private Long id;

	@Column(name = "title")
	private String title;

	@ManyToOne
	@JoinColumn(name = "pubId")
	@JsonIgnoreProperties("hibernateLazyInitializer")
	private Publisher publisher;

	@ManyToMany
	@JoinTable(name = "tbl_book_authors", joinColumns = { @JoinColumn(name = "bookId") }, inverseJoinColumns = {
			@JoinColumn(name = "authorId") })
	private List<Author> authors;

	@ManyToMany
	@JoinTable(name = "tbl_book_genres", joinColumns = { @JoinColumn(name = "bookId") }, inverseJoinColumns = {
			@JoinColumn(name = "genre_id") })
	private List<Genre> genres;

	@ManyToMany
	@JoinTable(name = "tbl_book_loans", joinColumns = { @JoinColumn(name = "bookId") }, inverseJoinColumns = {
			@JoinColumn(name = "branchId") })
	private List<LibraryBranch> libraryBranches;

	@ManyToMany
	@JoinTable(name = "tbl_book_loans", joinColumns = { @JoinColumn(name = "bookId") }, inverseJoinColumns = {
			@JoinColumn(name = "cardNo") })
	private List<Borrower> borrowers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public List<LibraryBranch> getLibraryBranches() {
		return libraryBranches;
	}

	public void setLibraryBranches(List<LibraryBranch> libraryBranches) {
		this.libraryBranches = libraryBranches;
	}

	public List<Borrower> getBorrowers() {
		return borrowers;
	}

	public void setBorrowers(List<Borrower> borrowers) {
		this.borrowers = borrowers;
	}

}
