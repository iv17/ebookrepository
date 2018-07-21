package rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = IndexUnit.INDEX_NAME, type = IndexUnit.TYPE_NAME, shards = 1, replicas = 0)
public class IndexUnit {

	public static final String INDEX_NAME = "digitallibrary";
	public static final String TYPE_NAME = "book";
	
	
	@Id
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	private String filename;
	
	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	private String title;
	
	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	private String author;
	
	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	private String keywords;
	
	@Field(type = FieldType.Integer, index = FieldIndex.analyzed, store = true)
	private Integer publicationYear;
	
	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	private String mime;
	
	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	private String categoryName;
	
	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	private String languageName;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	private String text;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	private String hightlight;

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

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHightlight() {
		return hightlight;
	}

	public void setHightlight(String hightlight) {
		this.hightlight = hightlight;
	}

	@Override
	public String toString() {
		return "IndexUnit [filename=" + filename + ", title=" + title + ", author=" + author + ", keywords=" + keywords
				+ ", publicationYear=" + publicationYear + ", mime=" + mime + ", categoryName=" + categoryName
				+ ", languageName=" + languageName + ", text=" + text + ", hightlight=" + hightlight + "]";
	}
	

	
}
