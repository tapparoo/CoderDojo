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
  selected = null;
  achievements = [];
  children = [];
  newChild = false;


  navigateToUserProfile(username, parentname){
    this.router.navigateByUrl('user/'+username+ '/profile/parent');
  }

  addChild(form: NgForm){
    const user = new User(form.value.nickname, 'password', true);
    // Add User
    this.auth.registerChild(user).subscribe(
      data => {
        this.userService.addChild(this.user.user.username, data).subscribe(
          child => {
            // Add UserDetail
            child.nickname = form.value.nickname;
            child.firstName = form.value.firstName;
            child.lastName = form.value.lastName;
            child.dob = form.value.dob;
            this.userService.updateUserDetail(child).subscribe(
              deets => {
                console.log(deets);
                // Reload parent's children array
                this.reloadChildren();
              }
            );
          }
          );
      }
    );
  }

  // Reload parent's children array
  reloadChildren() {
    this.userService.getChildren(this.user.user.username).subscribe(
      data => this.children = data
    );
  }

  reloadMeetings() {
    this.userService.getUserMeetings(this.user.user.username).subscribe(
      data => this.user.meetings = data
    );
  }

  constructor(
    private auth: AuthService,
    private router: Router,
    private currentRoute: ActivatedRoute,
    private userService: UserService
    ) { }

  ngOnInit() {
    if (this.auth.checkLogin()) {
      if (this.currentRoute.snapshot.paramMap.get('username')) {
        this.userService.getUser(this.currentRoute.snapshot.paramMap.get('username')).subscribe(
          data => {
            console.log(data)
            this.user = data;
            this.userService.getUserAchievements(this.user.user.username).subscribe(
              achieves => {
                this.user.achievements = achieves;
                this.achievements = achieves;
              },
              err => console.error('Observer got an error: ' + err)
            );
            this.reloadMeetings();
            this.reloadChildren();
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
