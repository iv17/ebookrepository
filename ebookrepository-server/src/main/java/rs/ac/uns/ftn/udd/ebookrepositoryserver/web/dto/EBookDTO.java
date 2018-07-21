package rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto;

public class EBookDTO {

	private int id;
	private String filename;
	
	private String title;
	private String author;
	private String keywords;
	private int publicationYear;
	private String mime;
	private String categoryName;
	private String languageName;
	private String cataloguerName;
	
	private String highlight;
	private String text;
	
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getCataloguerName() {
		return cataloguerName;
	}

	public void setCataloguerName(String cataloguerName) {
		this.cataloguerName = cataloguerName;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "EBookDTO [id=" + id + ", filename=" + filename + ", title=" + title + ", author=" + author
				+ ", keywords=" + keywords + ", publicationYear=" + publicationYear + ", mime=" + mime
				+ ", categoryName=" + categoryName + ", languageName=" + languageName + ", cataloguerName="
				+ cataloguerName + ", highlight=" + highlight + ", text=" + text + "]";
	}
	
}
