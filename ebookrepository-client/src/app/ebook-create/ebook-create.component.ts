import { Component, OnInit } from '@angular/core';
import { LanguageService } from '../service/language.service';
import { CategoryService } from '../service/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EbookrepositoryService } from '../service/ebookrepository.service';
import { IndexUnit } from '../model/indexUnit';

@Component({
  selector: 'app-ebook-create',
  templateUrl: './ebook-create.component.html',
  styleUrls: ['./ebook-create.component.css']
})
export class EbookCreateComponent implements OnInit {

  public languages = [];
  public categories = [];

  public categoryId;
  public book = { title: "", author: "", keywords: "", fileName: "", publicationYear: "", languageName: "", categoryName: "" };

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

  public addNewBook() {
    this.eBookService.create(this.book)
    .subscribe(
      data => {
        console.log(data)
        this.book.title = data.title.toString();
        this.book.author = data.author.toString();
        this.book.fileName = data.filename.toString();
        this.book.keywords = data.keywords.toString();
        this.book.publicationYear = data.publicationYear.toString();
        this.book.categoryName = data.categoryName.toString();
        this.book.languageName = data.languageName.toString();
      },
      error => {
        console.log(error);
      }
    );
  }
  
  uploadFile(event) {
    let fileList: FileList = event.target.files;
    if(fileList.length > 0) {
        let file: File = fileList[0];
        console.log(file);
        this.eBookService.upload(file)
        .subscribe(data => {
            console.log(data)
            this.book.title = data.title.toString();
            this.book.author = data.author.toString();
            this.book.fileName = data.filename.toString();
            this.book.keywords = data.keywords.toString();
            this.book.publicationYear = data.publicationYear.toString();
        });
    }
}
public index() {
  this.eBookService.index()
    .subscribe(
      data => {
        
      },
      error => {
        console.log(error);
      }
    );
}

}
