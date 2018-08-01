import { Component, OnInit } from '@angular/core';
import { EbookrepositoryService } from '../service/ebookrepository.service';
import { ActivatedRoute, Router } from '@angular/router';
import { saveAs } from "file-saver";

@Component({
  selector: 'app-ebook',
  templateUrl: './ebook.component.html',
  styleUrls: ['./ebook.component.css']
})
export class EbookComponent implements OnInit {
  
  public type: string = "";
  public categoryName: string = "";
  public bookCategoryName: string = "";
  public eBookId;
  public eBook;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private eBookRepositoryService: EbookrepositoryService) {
    this.eBookId = route.snapshot.params['eBookId'];
    if(JSON.parse(localStorage.getItem('user')) != null){
      this.type = JSON.parse(localStorage.getItem('user')).type;
      this.categoryName = JSON.parse(localStorage.getItem('user')).categoryName;
      console.log(this.categoryName);
     
    }
  }

  ngOnInit() {
    this.eBookRepositoryService.getById(this.eBookId)
      .subscribe(
        data => {
          this.eBook = data;
          this.bookCategoryName = this.eBook.categoryName;
          console.log(this.eBook);
        },
        error => {
          console.log(error);
        }
      );
  }
  delete() {
   
    this.eBookRepositoryService.deleteIndex(this.eBookId)
      .subscribe(
        data => {
          console.log("Obrisan!");
          this.router.navigateByUrl('/home/ebooks');
        }
      );
  }
  download() {
    this.eBookRepositoryService.download(this.eBookId)
      .subscribe(
        data => {
          this.downloadFile(data, this.eBook.title + ".pdf");
        },
        error => {
          console.log(error);
        }
      );
  }

  downloadFile(data, fileName) {
    var blob = new Blob([data], { type: 'application/pdf' });
    saveAs(blob, fileName);
  }
}
