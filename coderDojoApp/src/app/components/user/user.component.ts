import { UserDetail } from './../../models/user-detail';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Address } from 'src/app/models/address';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  user = new UserDetail();
  currentAuth = null;
  editUser = false;
  editUserPass = false;

  updateUsernamePassword(form: NgForm) {
    let currentPasswordMatch;
    const creds = [form.value.username, atob(this.auth.getCredentials()).split(':')[1]];

    if (form.value.currentPass && form.value.newPass) {
      currentPasswordMatch = this.auth.generateBasicAuthCredentials(this.currentAuth.username.toLowerCase(), form.value.currentPass)
        === this.auth.getCredentials();

      if (currentPasswordMatch) {
          creds[1] = form.value.newPass;
      } else {
        return;
      }
    }

    this.currentAuth.username = creds[0];
    this.currentAuth.password = creds[1];

    this.userService.updateUser(this.currentAuth).subscribe(
        data => {
          this.auth.login(creds[0], creds[1]);
          this.editUserPass = false;
          this.currentAuth = data;
          this.router.navigateByUrl(`user/${form.value.username}`);
        },
        err => {
          this.router.navigateByUrl('not-found');
          console.error('Observer got an error: ' + err);
        }
      );
  }

  updateUserDetails(form: NgForm) {
    const deets = new UserDetail(
      form.value.nickname,
      form.value.firstName,
      form.value.lastName,
      form.value.email,
      form.value.phoneNumber,
      form.value.dob,
      form.value.gender,
      form.value.userImageUrl,
      form.value.userId
    );
    const addr = new Address();
    addr.street = form.value.street;
    addr.street2 = form.value.street2;
    addr.city = form.value.city;
    addr.state = form.value.state;
    addr.zip = form.value.zip;
    addr.country = form.value.country;
    addr.id = this.user.address.id;

    deets.id = this.user.id;
    deets.address = addr;

    this.userService.updateUserDetail(deets).subscribe(
      data => {
        this.user = data;
        this.editUser = false;
      },
      err => {
        this.router.navigateByUrl('not-found');
        console.error('Observer got an error: ' + err);
      }
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
        this.userService.getUser(this.currentRoute.snapshot.paramMap.get('username')).subscribe(
          data => {
            this.user = data;
            this.currentAuth = this.user.user;

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
