import { Component, OnInit } from '@angular/core';
import { LanguageService } from '../service/language.service';
import { CategoryService } from '../service/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EbookrepositoryService } from '../service/ebookrepository.service';

@Component({
  selector: 'app-ebook-create',
  templateUrl: './ebook-create.component.html',
  styleUrls: ['./ebook-create.component.css']
})
export class EbookCreateComponent implements OnInit {

  public languages = [];
  public categories = [];

  public categoryId;
  public book = { title: "", author: "", keywords: "", filename: "", publicationYear: "", mime: "", languageName: "", categoryName: "", highlight: "", text: "" };

  constructor(private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private languageService: LanguageService,
    private eBookService: EbookrepositoryService) {

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

  uploadFile(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      this.eBookService.upload(file)
        .subscribe(data => {
          console.log(data)
          this.book.title = data.title.toString();
          this.book.author = data.author.toString();
          this.book.filename = data.filename.toString();
          this.book.keywords = data.keywords.toString();
          this.book.publicationYear = data.publicationYear.toString();
        });
    }
  }
  originalData:any;
  public addNewBook() {
    this.originalData = this.book;
    this.eBookService.createIndex(this.book)
    .subscribe(
      data => {
       console.log("Successfully indexed!");
      },
      error => {
        console.log(error);
      }
    );
    this.eBookService.create(this.originalData)
      .subscribe(
        data => {
          this.router.navigateByUrl('/home/ebooks/' + data.id);
        },
        error => {
          console.log(error);
        }
      );
    
  }
  public index() {
    this.eBookService.index()
      .subscribe(
        data => { },
        error => {
          console.log(error);
        }
      );
  }

}
