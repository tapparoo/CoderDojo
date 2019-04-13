import { UserDetail } from './../../models/user-detail';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { User } from 'src/app/models/user';
import { Address } from 'src/app/models/address';

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
      form.value.firstName,
      form.value.lastName,
      form.value.email,
      form.value.phoneNumber,
      form.value.dob,
      form.value.gender,
      form.value.userImageUrl
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
    console.log(deets);

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
        this.userService.getUserByUsername(this.currentRoute.snapshot.paramMap.get('username')).subscribe(
          data => {
            console.log(data);

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
