import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { Category } from '../model/category';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CategoryService } from '../service/category.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  public type: string = "";
  public users = [];

  public row = { firstName: "", lastName: "", email: "", categoryName: ""};
  public user = { id: -1, firstName: "", lastName: "", email: "", categoryName: ""};

  public categories = [];

  constructor(private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private categoryService: CategoryService) { 
      if(JSON.parse(localStorage.getItem('user')) != null){
        this.type = JSON.parse(localStorage.getItem('user')).type;
      }
    }

  ngOnInit() {
    this.populate();
    this.populateCategories();
  }

  public populate() {
    this.userService.getAll()
      .subscribe(
        data => {
          this.users = data;
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
  public selectUser(row) {
    this.user.id = row.id;
    this.user.firstName = row.firstName;
    this.user.lastName = row.lastName;
    this.user.email = row.email;
    this.user.categoryName = row.categoryName;
  }

  public update() {
    console.log(this.user)
    this.userService.update(this.user.id, this.user)
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
