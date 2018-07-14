package rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto;

public class EBookDTO {

	private int id;
	private String title;
	private String author;
	private String keywords;
	private int publicationYear;
	private String filename;
	private String MIME;
	private CategoryDTO category;
	private LanguageDTO language;
	private UserDTO cataloguer;
	
	
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
	
	public CategoryDTO getCategory() {
		return category;
	}
	
	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
	
	public LanguageDTO getLanguage() {
		return language;
	}
	
	public void setLanguage(LanguageDTO language) {
		this.language = language;
	}
	
	public UserDTO getCataloguer() {
		return cataloguer;
	}
	
	public void setCataloguer(UserDTO cataloguer) {
		this.cataloguer = cataloguer;
	}

}
