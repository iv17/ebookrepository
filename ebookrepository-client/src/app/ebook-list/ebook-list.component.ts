import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EbookrepositoryService } from '../service/ebookrepository.service';
import { CategoryService } from '../service/category.service';
import { LanguageService } from '../service/language.service';

@Component({
  selector: 'app-ebook-list',
  templateUrl: './ebook-list.component.html',
  styleUrls: ['./ebook-list.component.css']
})
export class EbookListComponent implements OnInit {

  public categoryId;
  public category;

  public books = [];
  public languages = [];
  public categories = [];

  public row = { title: "", author: "", publicationYear: "", languageName: "" };
  public book = { title: "", author: "", keywords: "", publicationYear: "", languageName: "" };
  
  constructor(private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private languageService: LanguageService,
    private eBookRepositoryService: EbookrepositoryService) { 
      this.categoryId = route.snapshot.params['categoryId'];
    }

  ngOnInit() {
    this.findCategory();
    this.populate();
    this.populateCategories();
    this.populateLanguages();
  }

  public findCategory() {
    this.categoryService.getById(this.categoryId)
    .subscribe(
      data => {
        this.category = data;
      },
      error => {
        console.log(error);
      }
    );
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
    this.eBookRepositoryService.getAllByCategory(this.categoryId)
    .subscribe(
      data => {
        this.books = data;
      },
      error => {
        console.log(error);
      }
    );
  }
}
