
export class Ebook {
    id: number;
    filename: String;

    title: String;
    author: String;
    keywords: String;
    publicationYear: number;
    MIME: String;
    categoryName: String;
    languageName: String;
    cataloguerName: String;
    highlight: String;
    text: String;

    constructor(inter: EBookInterface = {}) {
        this.id = inter.id;
        this.filename = inter.filename;
        this.title = inter.title;
        this.author = inter.author;
        this.keywords = inter.keywords;
        this.publicationYear = inter.publicationYear;
        this.MIME = inter.MIME;
        this.categoryName = inter.categoryName;
        this.languageName = inter.languageName;
        this.cataloguerName = inter.cataloguerName;
        this.highlight = inter.highlight;
        this.text = inter.text;
    }
}
interface EBookInterface {
    id?: number;
    filename?: String;
    title?: String;
    author?: String;
    keywords?: String;
    publicationYear?: number;
    MIME?: String;
    categoryName?: String;
    languageName?: String;
    cataloguerName?: String;
    highlight?: String;
    text?: String;
}