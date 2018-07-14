import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CategoryService } from '../service/category.service';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  public categories = [];

  public row = { name: "" };
  public newCategory = { name: "" };
  public category = { id: -1, name: "" };


  constructor(private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService) {
      
  }

  ngOnInit() {
    this.populate();
  }

  public populate() {
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
  public selectCategory(row) {
    this.category.id = row.id;
    this.category.name = row.name;
  }

  public addNewCategory() {
    this.categoryService.addNewCategory(this.newCategory)
      .subscribe(
        data => {
          this.populate();
        },
        error => {
          console.log(error);
        });
  }

  public updateCategory() {
    this.categoryService.updateCategory(this.category.id, this.category)
      .subscribe(
        data => {
          this.populate();
        },
        error => {
          console.log(error);
        }
      )
  }

}
