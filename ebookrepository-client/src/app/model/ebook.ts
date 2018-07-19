
export class Ebook {
    id: number;
    title: String;
    author: String;
    keywords: String;
    publicationYear: number;
    filename: String;
    MIME: String;
    categoryName: String;
    languageName: String;
    cataloguerName: String;
    highlight: String;
    
    constructor(inter: EBookInterface = {}) {
        this.id = inter.id;
        this.title = inter.title;
        this.author = inter.author;
        this.keywords = inter.keywords;
        this.publicationYear = inter.publicationYear;
        this.filename = inter.filename;
        this.MIME = inter.MIME;
        this.categoryName = inter.categoryName;
        this.languageName = inter.languageName;
        this.cataloguerName = inter.cataloguerName;
        this.highlight = inter.highlight;
    }
}
interface EBookInterface {
    id?: number;
    title?: String;
    author?: String;
    keywords?: String;
    publicationYear?: number;
    filename?: String;
    MIME?: String;
    categoryName?: String;
    languageName?: String;
    cataloguerName?: String;
    highlight?: String;
}