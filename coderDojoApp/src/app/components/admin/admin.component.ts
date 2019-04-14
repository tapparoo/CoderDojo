import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  users = [];
  user = null;
  editUser = false;

  displayUsers(): void {
    this.userService.index().subscribe(
      data => {
        this.users = data;

      },
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  showUser(username: string) {
    this.userService.getUser(username).subscribe(
      data => this.user = data,
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  updateUser(form: NgForm) {
    this.user.firstName = form.value.firstName;
    this.user.lastName = form.value.lastName;
    this.user.nickname = form.value.nickname;
    this.user.phoneNumber = form.value.phoneNumber;
    this.user.email = form.value.email;
    this.user.dob = form.value.dob;


    this.userService.updateUserDetail(this.user).subscribe(
      data => {
        this.user = data;
        this.editUser = false;
        const oldUser = this.user.user;

        oldUser.username = form.value.username;
        oldUser.password = form.value.password;

        if (oldUser.username !== form.value.username || form.value.password) {
          console.log('updating user');
          this.userService.updateUser(oldUser).subscribe(
            updatedUser => {
              this.user.user = updatedUser;
              this.displayUsers();
            },
            err => {
              console.log(err);
              console.log('Error updating username password from admin panel');
            }
          );
        } else {
          this.displayUsers();
        }

      },
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.displayUsers();
  }

}
