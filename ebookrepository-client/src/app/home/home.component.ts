import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

import { UserService } from '../service/user.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent  {
  
  public type: string = "";

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
    );

  constructor(private breakpointObserver: BreakpointObserver,
    private service: UserService,
    private router: Router
  ) {
    if(JSON.parse(localStorage.getItem('user')) != null){
      this.type = JSON.parse(localStorage.getItem('user')).type;
      console.log(this.type);
    }
   }
  
  public logout() {
    this.service.logout();
    this.service.removeToken();
    this.router.navigateByUrl('/login');
  }
}
