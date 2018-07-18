import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../service/category.service';
import { LanguageService } from '../service/language.service';
import { EbookrepositoryService } from '../service/ebookrepository.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-ebook',
  templateUrl: './ebook.component.html',
  styleUrls: ['./ebook.component.css']
})
export class EbookComponent implements OnInit {

  public eBookId;
  public eBook;
  
  constructor(private router: Router,
    private route: ActivatedRoute,
    private eBookRepositoryService: EbookrepositoryService) { 
    this.eBookId = route.snapshot.params['eBookId'];
  }

  ngOnInit() {
    this.eBookRepositoryService.getById(this.eBookId)
    .subscribe(
      data => {
        this.eBook = data;
        console.log(this.eBook);
      },
      error => {
        console.log(error);
      }
    );
  }

  download() {
    
  }
}
