import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { Category } from '../model/category';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  public users = [];

  public row = { firstName: "", lastName: "", email: "" };
  public newUser = { firstName: "", lastName: "", email: "", password: ""};
  public user = { id: -1, firstName: "", lastName: "", email: "", category: Category};

  constructor(private router: Router,
    private route: ActivatedRoute,
    private userService: UserService) { }

  ngOnInit() {
    this.populate();
  }

  public populate() {
    this.userService.getAll()
      .subscribe(
        data => {
          this.users = data;
          console.log(this.users);
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
    //this.user.category.name = row.category.name;
  }

  public create() {
    this.userService.register(this.newUser)
      .subscribe(
        data => {
          this.populate();
        },
        error => {
          console.log(error);
        });
  }

  public update() {
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
