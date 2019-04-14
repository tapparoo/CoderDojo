import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  registering = false;
  buttonText = 'Login';
  newUser = null;
  loggedIn = this.auth.checkLogin();
  routerPath = '';

  login(form: NgForm) {
    const user = form.value.username;
    const pw = form.value.password;

    this.auth.login(user, pw).subscribe(
      next => {
        this.loggedIn = true;
        this.buttonText = next.name;
        this.routerPath = `/user/${next.name}`;
        document.getElementById('loginDropdown').classList.remove('show');
        console.log('LoginComponent.login(): user logged in, routing to default page by role/authority.');
        const auth = [];

        for (const a of next.authorities) {
          auth.push(a.authority);
        }

        if (auth.indexOf('ADMIN') > -1 ) {
          this.router.navigateByUrl('admin');
        } else {
          this.router.navigateByUrl(`user/${next.name}`);
        }
      },
      error => {
        console.error('LoginComponent.login(): error logging in.');
        console.log(error);

      }
    );
  }

  register(form: NgForm) {
    const user = new User(
      form.value.username, form.value.password, true
    );
    this.auth.register(user).subscribe(
      data => {
        this.loggedIn = true;
        this.buttonText = data.name;
        this.routerPath = `/user/${data.name}`;
        document.getElementById('loginDropdown').classList.remove('show');
        this.newUser = data;
        this.newUser.email = form.value.email;
        this.newUser.phoneNumber = form.value.phoneNumber;
        this.userService.updateUserDetail(this.newUser).subscribe(
          userDetailData => {
            this.registering = false;
            this.router.navigateByUrl(`/user/${user.username}`);
          },
          err => {
            console.log(err);
            console.log('Error updating new userdetails from registration page');
          }
        );
      },
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  logout() {
    this.auth.logout();
    this.buttonText = 'Login';
    this.registering = false;
    this.newUser = null;
    this.routerPath = null;
    this.loggedIn = false;
    document.getElementById('profileDropdown').classList.remove('show');
    this.router.navigateByUrl('/home');
  }

  constructor(private auth: AuthService, private userService: UserService, private router: Router) { }

  ngOnInit() {
  }
}
