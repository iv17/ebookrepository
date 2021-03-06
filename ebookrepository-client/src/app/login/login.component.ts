import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public user = {
    email: "ivana@gmail.com",
    password: "ivana"
  };

  constructor(private service: UserService,
    private router: Router) { }

  ngOnInit() {
  }

  login() {
    this.service.login(this.user).subscribe((data) => {
      localStorage.setItem('token', JSON.stringify(data.token));
      localStorage.setItem('user', JSON.stringify(data.user));
      this.router.navigateByUrl('/home');
    });
  }
}
