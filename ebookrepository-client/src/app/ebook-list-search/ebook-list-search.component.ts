import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../service/category.service';
import { LanguageService } from '../service/language.service';
import { EbookrepositoryService } from '../service/ebookrepository.service';
import { SimpleQuery } from '../model/simpleQuery';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-ebook-list-search',
  templateUrl: './ebook-list-search.component.html',
  styleUrls: ['./ebook-list-search.component.css']
})
export class EbookListSearchComponent implements OnInit {

  public books = [];
  public languages = [];
  public categories = [];

  public row = { title: "", author: "", publicationYear: "", languageName: "" };
  public book = { title: "", author: "", keywords: "", publicationYear: "", languageName: "", filename: "" };

  public title = "";
  public author = "";
  public keywords = "";
  public languageName = "";

  public simpleQuery = new SimpleQuery;

  constructor(private categoryService: CategoryService,
    private languageService: LanguageService,
    private eBookRepositoryService: EbookrepositoryService,
    private sanitizer: DomSanitizer) { }


  ngOnInit() {
    this.populate();
    this.populateCategories();
    this.populateLanguages();

  }
  public populateCategories() {
    this.categoryService.getAll()
      .subscribe(
        data => {
          this.categories = data;
        },
        error => {
          console.log(error);
        }
      );
  }
  public populateLanguages() {
    this.languageService.getAll()
      .subscribe(
        data => {
          this.languages = data;
        },
        error => {
          console.log(error);
        }
      );
  }
  trustedUrl;

  public populate() {
    this.eBookRepositoryService.getAll()
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }

  public searchByTitleTerm() {
    this.simpleQuery.field = "title";
    this.simpleQuery.value = this.title;
    this.eBookRepositoryService.searchTerm(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByTitleFuzzy() {
    this.simpleQuery.field = "title";
    this.simpleQuery.value = this.title;
    this.eBookRepositoryService.searchFuzzy(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByTitlePhrase() {
    this.simpleQuery.field = "title";
    this.simpleQuery.value = this.title;
    this.eBookRepositoryService.searchPhrase(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }

  public searchByAuthorTerm() {
    this.simpleQuery.field = "author";
    this.simpleQuery.value = this.author;
    this.eBookRepositoryService.searchTerm(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByAuthorFuzzy() {
    this.simpleQuery.field = "author";
    this.simpleQuery.value = this.author;
    this.eBookRepositoryService.searchFuzzy(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByAuthorPhrase() {
    this.simpleQuery.field = "author";
    this.simpleQuery.value = this.author;
    this.eBookRepositoryService.searchPhrase(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByKeywordsTerm() {
    this.simpleQuery.field = "keywords";
    this.simpleQuery.value = this.keywords;
    this.eBookRepositoryService.searchTerm(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByKeywordsFuzzy() {
    this.simpleQuery.field = "keywords";
    this.simpleQuery.value = this.keywords;
    this.eBookRepositoryService.searchFuzzy(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByKeywordsPhrase() {
    this.simpleQuery.field = "keywords";
    this.simpleQuery.value = this.keywords;
    this.eBookRepositoryService.searchPhrase(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByLanguageTerm() {
    this.simpleQuery.field = "languageName";
    this.simpleQuery.value = this.languageName;
    this.eBookRepositoryService.searchTerm(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
}
