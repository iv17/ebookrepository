import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../service/category.service';
import { LanguageService } from '../service/language.service';
import { EbookrepositoryService } from '../service/ebookrepository.service';

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
  public book = { title: "", author: "", keywords: "", publicationYear: "", languageName: "" };
  
  constructor(private categoryService: CategoryService,
    private languageService: LanguageService,
    private eBookRepositoryService: EbookrepositoryService) { }

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

}
