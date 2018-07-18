package rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model;

public final class ResultData {
	
	private String title;
	private String keywords;
	private String highlight;
	private String author;
	private String languageName;
	private String categoryName;
	private int publicationYear;
	private String location;
	
	public ResultData() {
		super();
	}

	public ResultData(String title, String keywords, String highlight, String author, String languageName,
			String categoryName, int publicationYear,  String location) {
		super();
		this.title = title;
		this.keywords = keywords;
		this.highlight = highlight;
		this.author = author;
		this.languageName = languageName;
		this.categoryName = categoryName;
		this.publicationYear = publicationYear;
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
