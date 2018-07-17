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
//@Document(indexName = "iv", type = "books")
public class EBook implements Serializable {

	private static final long serialVersionUID = 1794404948542221514L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "author", nullable = true)
	private String author;
	
	@Column(name = "keywords", nullable = true)
	private String keywords;
	
	@Column(name = "publication_year", nullable = true)
	private int publicationYear;
	
	@Column(name = "filename", nullable = false, unique = true)
	private String filename;
	
	@Column(name = "mime", nullable = true)
	private String mime;
	
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
	
	public EBook(int id, String title, String author, String keywords, int publicationYear, String filename, String mime,
			Category category, Language language, User cataloguer) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.publicationYear = publicationYear;
		this.filename = filename;
		this.mime = mime;
		this.category = category;
		this.language = language;
		this.cataloguer = cataloguer;
	}
	
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

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
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


	@Override
	public String toString() {
		return "EBook [id=" + id + ", title=" + title + ", author=" + author + ", keywords=" + keywords
				+ ", publicationYear=" + publicationYear + ", filename=" + filename + ", MIME=" + mime + ", category="
				+ category + ", language=" + language + ", cataloguer=" + cataloguer + "]";
	}
	
}
