import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { CategoryService } from '../service/category.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent implements OnInit {

  public newUser = { firstName: "", lastName: "", email: "", password: "", categoryName: ""};
  public categories = [];

  constructor(private router: Router,
    private userService: UserService,
    private categoryService: CategoryService) { }

   ngOnInit() {
    this.populateCategories();
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

  public create() {
    this.userService.register(this.newUser)
      .subscribe(
        data => {
          this.router.navigateByUrl('/home/users');
        },
        error => {
          console.log(error);
        });
  }

}
