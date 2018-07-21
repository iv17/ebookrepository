import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule, MatGridListModule, MatCardModule, MatMenuModule, MatDialogModule } from '@angular/material';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { HeaderInterceptor } from './header-interceptor';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { EbookListComponent } from './ebook-list/ebook-list.component';
import { UserProfileEditComponent } from './user-profile-edit/user-profile-edit.component';
import { EbookComponent } from './ebook/ebook.component';
import { UsersComponent } from './users/users.component';
import { EbookListSearchComponent } from './ebook-list-search/ebook-list-search.component';
import { UserCreateComponent } from './user-create/user-create.component';
import { EbookCreateComponent } from './ebook-create/ebook-create.component';
import { EbookEditComponent } from './ebook-edit/ebook-edit.component';

const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "change-password",
    component: ChangePasswordComponent
  },
  {
    path: "home",
    component: HomeComponent,
    children: [
      {
        path: "profile",
        component: UserProfileComponent
      },
      {
        path: "edit-profile",
        component: UserProfileEditComponent
      },
      {
        path: "users",
        component: UsersComponent
      },
      {
        path: "user-create",
        component: UserCreateComponent
      },
      {
        path: "categories",
        component: CategoryListComponent
      },
      {
        path: "categories/ebooks/:categoryId",
        component: EbookListComponent
      },
      {
        path: "categories/ebooks/:categoryId/ebook/:eBookId",
        component: EbookComponent
      },
      {
        path: "ebooks/:eBookId",
        component: EbookComponent
      },
      {
        path: "ebooks/:eBookId/edit",
        component: EbookEditComponent
      },
      {
        path: "ebooks",
        component: EbookListSearchComponent
      },
      {
        path: "ebook-create",
        component: EbookCreateComponent
      }
    ]
  }

]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ChangePasswordComponent,
    UserProfileComponent,
    CategoryListComponent,
    EbookListComponent,
    UserProfileEditComponent,
    EbookComponent,
    UsersComponent,
    EbookListSearchComponent,
    UserCreateComponent,
    EbookCreateComponent,
    EbookEditComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(
      routes
    ),
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatDialogModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HeaderInterceptor, multi: true },
  ],
  exports: [RouterModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
