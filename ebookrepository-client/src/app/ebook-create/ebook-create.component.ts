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
  public book = { title: "", author: "", keywords: "", publicationYear: "", languageName: "", categoryName: "" };

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
    this.router.navigateByUrl('/home/categories/ebooks/1');
  }
  
  uploadFile(event) {
    let fileList: FileList = event.target.files;
    if(fileList.length > 0) {
        let file: File = fileList[0];
        console.log(file);
        this.eBookService.upload(file)
        .subscribe(x => {
            console.log(x)
        });
    }
}

}
