import { Component, OnInit } from '@angular/core';
import { LanguageService } from '../service/language.service';
import { CategoryService } from '../service/category.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-ebook-create',
  templateUrl: './ebook-create.component.html',
  styleUrls: ['./ebook-create.component.css']
})
export class EbookCreateComponent implements OnInit {

  public languages = [];
  public categories = [];

  public file: any;

  public categoryId;
  public newBook = { title: "", author: "", keywords: "", publicationYear: "", languageName: "", categoryName: "" };

  constructor(private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private languageService: LanguageService) { 
    
    }

  ngOnInit() {
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

  public upload() {
    console.log(this.file);
  }
  public addNewBook() {
    this.router.navigateByUrl('/home/categories/ebooks/1');
  }
  
}
