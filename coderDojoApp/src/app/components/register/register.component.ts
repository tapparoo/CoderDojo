import { AuthService } from 'src/app/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user = null;

  register(form: NgForm) {
    const user = new User(
      form.value.username, form.value.password, true
    );
    this.authService.register(user).subscribe(
      data => {
        this.user = data;
        console.log(data);

      },
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );

  }



  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
  }

}
