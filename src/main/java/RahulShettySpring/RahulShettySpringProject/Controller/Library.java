package RahulShettySpring.RahulShettySpringProject.Controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="storage2")
public class Library {

	public String getBookName() {
		return book_name;
	}

	public void setBookName(String book_name) {
		this.book_name = book_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getAisle() {
		return aisle;
	}

	public void setAisle(int aisle) {
		this.aisle = aisle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name="book_name")
	private String book_name;
	@Column(name="id")
	@Id
	private String id;
	@Column(name="isbn")
	private String isbn;
	@Column(name="aisle")
	private int aisle;
	@Column(name="author")
	private String author;
}
