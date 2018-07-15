
export class Ebook {
    title: String;
    author: String;
    keywords: String;
    publicationYear: number;
    fileName: String;
    MIME: String;
    categoryName: String;
    languageName: String;
    cataloguerName: String;
    
    constructor(inter: EBookInterface = {}) {
        this.title = inter.title;
        this.author = inter.author;
        this.keywords = inter.keywords;
        this.publicationYear = inter.publicationYear;
        this.fileName = inter.fileName;
        this.MIME = inter.MIME;
        this.categoryName = inter.categoryName;
        this.languageName = inter.languageName;
        this.cataloguerName = inter.cataloguerName;
    }
}
interface EBookInterface {
    title?: String;
    author?: String;
    keywords?: String;
    publicationYear?: number;
    fileName?: String;
    MIME?: String;
    categoryName?: String;
    languageName?: String;
    cataloguerName?: String;
}