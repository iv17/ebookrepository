import { Category } from "./category";
import { Language } from "./language";
import { User } from "./user";

export class Ebook {
    title: String;
    author: String;
    keywords: String;
    publicationYear: number;
    fileName: String;
    MIME: String;
    category: Category;
    language: Language;
    cataloguer: User;
    
    constructor(inter: EBookInterface = {}) {
        this.title = inter.title;
        this.author = inter.author;
        this.keywords = inter.keywords;
        this.publicationYear = inter.publicationYear;
        this.fileName = inter.fileName;
        this.MIME = inter.MIME;
        this.category = inter.category;
        this.language = inter.language;
        this.cataloguer = inter.cataloguer;
    }
}
interface EBookInterface {
    title?: String;
    author?: String;
    keywords?: String;
    publicationYear?: number;
    fileName?: String;
    MIME?: String;
    category?: Category;
    language?: Language;
    cataloguer?: User;
}