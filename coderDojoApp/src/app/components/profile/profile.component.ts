import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserDetail } from 'src/app/models/user-detail';
import { Address } from 'src/app/models/address';
import { AuthService } from 'src/app/services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user = null;
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
      }
    }

    this.currentAuth.username = creds[0];
    this.currentAuth.password = creds[1];

    this.userService.updateUser(this.currentAuth).subscribe(
        data => {
          // this.auth.login(creds[0], creds[1]);
          this.auth.logout();
          this.editUserPass = false;
          this.currentAuth = data;
          this.auth.setCredentials(creds[0], creds[1]);
        },
        err => {
          this.router.navigateByUrl('not-found');
          console.error('Observer got an error: ' + err);
        }
      );
  }

  updateUserDetails(form: NgForm) {
    this.user.nickname = form.value.nickname;
    this.user.firstName = form.value.firstName;
    this.user.lastName = form.value.lastName;
    this.user.email = form.value.email;
    this.user.phoneNumber = form.value.phoneNumber;
    this.user.dob = form.value.dob;
    this.user.gender = form.value.gender;
    this.user.imageUrl = form.value.userImageUrl;

    const addr = new Address();
    addr.street = form.value.street;
    addr.street2 = form.value.street2;
    addr.city = form.value.city;
    addr.state = form.value.state;
    addr.zip = form.value.zip;
    addr.country = form.value.country;
    addr.id = this.user.address.id;

    this.user.address = addr;

    this.userService.updateUserDetail(this.user).subscribe(
      data => {
        this.user = data;
        this.editUser = false;
        this.router.navigateByUrl(`user/${form.value.username}/profile`);
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

            this.userService.getUserAchievements(this.user.user.username).subscribe(
              achieves => {
                this.user.achievements = achieves;
              },
              err => console.error('Observer got an error: ' + err)
            );
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
