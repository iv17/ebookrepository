import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EbookrepositoryService } from '../service/ebookrepository.service';
import { CategoryService } from '../service/category.service';
import { LanguageService } from '../service/language.service';

@Component({
  selector: 'app-ebook-edit',
  templateUrl: './ebook-edit.component.html',
  styleUrls: ['./ebook-edit.component.css']
})
export class EbookEditComponent implements OnInit {

  public languages = [];
  public categories = [];

  public eBookId;
  
  public book = { title: "", author: "", keywords: "", filename: "", publicationYear: "", mime: "", languageName: "", categoryName: "", highlight: "", text: "" };

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private languageService: LanguageService,
    private eBookRepositoryService: EbookrepositoryService
  ) { 
    this.eBookId = route.snapshot.params['eBookId'];
  }

  ngOnInit() {
    this.populateCategories();
    this.populateLanguages();
    this.eBookRepositoryService.getById(this.eBookId)
    .subscribe(
      data => {
        this.book = data;
        console.log(this.book);
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
  originalData:any;
  public edit() {
    this.originalData = this.book;
    this.eBookRepositoryService.createIndex(this.book)
    .subscribe(
      data => {
       console.log("Successfully indexed!");
      },
      error => {
        console.log(error);
      }
    );
    this.eBookRepositoryService.update(this.eBookId, this.originalData)
      .subscribe(
        data => {
          this.router.navigateByUrl('/home/ebooks/' + data.id);
        },
        error => {
          console.log(error);
        }
      );
    
  }

}
