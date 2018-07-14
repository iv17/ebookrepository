import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public user = {
    firstName: "Ivana",
    lastName: "Savin",
    email: "ivana@gmail.com",
    password: "ivana"
  };
  
  constructor(private service: UserService,
    private router: Router) { }

  ngOnInit() {
  }

  register() {
    this.service.register(this.user)
    .subscribe((data) => {
      this.router.navigateByUrl('/login');
    });
  }

}
