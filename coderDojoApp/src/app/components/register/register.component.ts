import { AuthService } from 'src/app/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { routerNgProbeToken } from '@angular/router/src/router_module';

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
        this.router.navigateByUrl(`/user/${user.username}`);
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
