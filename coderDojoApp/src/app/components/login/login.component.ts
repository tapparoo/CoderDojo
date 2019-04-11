import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  login(form: NgForm) {
    const user = form.value.username;
    const pw = form.value.password;

    this.auth.login(user, pw).subscribe(
      next => {
        console.log('LoginComponent.login(): user logged in, routing to default page by role/authority.');
        let auth = [];
        for (let a of next.authorities) {
          auth.push(a.authority);
        }

        if (auth.indexOf('ADMIN') > -1 ) {
          this.router.navigateByUrl('admin');
        } else {
          this.router.navigateByUrl('/');
        }
      },
      error => {
        console.error('LoginComponent.login(): error logging in.');
      }
    );
  }

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit() {
  }
}
