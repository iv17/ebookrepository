package rs.ac.uns.ftn.udd.ebookrepositoryserver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ebook")
public class EBook implements Serializable {

	private static final long serialVersionUID = 1794404948542221514L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false , unique = true)
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "keywords")
	private String keywords;
	
	@Column(name = "publication_year")
	private int publicationYear;
	
	@Column(name = "filename")
	private String filename;
	
	@Column(name = "mime")
	private String MIME;
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id", nullable = true)
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "language_id", referencedColumnName = "id", nullable = true)
	private Language language;
	
	@ManyToOne
	@JoinColumn(name = "cataloguer_id", referencedColumnName = "id", nullable = true)
	private User cataloguer;

	
	public EBook() {}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMIME() {
		return MIME;
	}

	public void setMIME(String mIME) {
		MIME = mIME;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public User getCataloguer() {
		return cataloguer;
	}

	public void setCataloguer(User cataloguer) {
		this.cataloguer = cataloguer;
	}
	
}
