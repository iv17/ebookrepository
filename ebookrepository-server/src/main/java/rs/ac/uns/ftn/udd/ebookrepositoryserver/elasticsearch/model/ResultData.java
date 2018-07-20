package rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model;

public final class ResultData {
	
	private String filename;
	private String title;
	private String author;
	private String keywords;
	private int publicationYear;
	private String mime;
	private String languageName;
	private String categoryName;
	private String highlight;
	private String text;
	
	public ResultData() {
		super();
	}

	public ResultData(String filename, String title, String author, String keywords, int publicationYear, String mime,
			String languageName, String categoryName, String highlight, String text) {
		super();
		this.filename = filename;
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.publicationYear = publicationYear;
		this.mime = mime;
		this.languageName = languageName;
		this.categoryName = categoryName;
		this.highlight = highlight;
		this.text = text;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	
}
