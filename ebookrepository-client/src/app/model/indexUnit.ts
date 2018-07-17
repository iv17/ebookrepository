
export class IndexUnit {
    title: String;
    author: String;
    keywords: String;
    publicationYear: String;
    filename: String;
    MIME: String;
    text: String;
    categoryName: String;
    languageName: String;
    cataloguerName: String;
    
    constructor(inter: IndexUnitInterface = {}) {
        this.title = inter.title;
        this.author = inter.author;
        this.keywords = inter.keywords;
        this.publicationYear = inter.publicationYear;
        this.filename = inter.filename;
        this.MIME = inter.MIME;
        this.categoryName = inter.categoryName;
        this.languageName = inter.languageName;
        this.cataloguerName = inter.cataloguerName;
    }
}
interface IndexUnitInterface {
    title?: String;
    author?: String;
    keywords?: String;
    publicationYear?: String;
    filename?: String;
    MIME?: String;
    text?: String;
    categoryName?: String;
    languageName?: String;
    cataloguerName?: String;
}