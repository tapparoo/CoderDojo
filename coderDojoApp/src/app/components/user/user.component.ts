import { UserDetail } from './../../models/user-detail';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  user = null;
  editUser = false;

  updateUsernamePassword(form: NgForm) {
    this.user = new User(
      form.value.username,
      form.value.password,
      form.value.enabled,
      form.value.id
    );
  }

  updateUserDetails(form: NgForm) {
    const deets = new UserDetail(
      form.value.nickname,
      form.value.phoneNumber,
      form.value.dob
    );
  }

  reload() {

  }

  constructor(
    private auth: AuthService,
    private currentRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService
    ) { }

  ngOnInit() {
    if (this.auth.checkLogin()) {

      // Switch this to getAchievement / getGoal, etc.
      //   - Default will be to list all achievements completed/inprogress


      if (this.currentRoute.snapshot.paramMap.get('username')) {
        this.userService.getUserByUsername(this.currentRoute.snapshot.paramMap.get('username')).subscribe(
          data => {
            this.user = data;
          },
          err => {
            this.router.navigateByUrl('not-found');
            console.error('Observer got an error: ' + err);
          }
        );
      }
    }
  }

}
