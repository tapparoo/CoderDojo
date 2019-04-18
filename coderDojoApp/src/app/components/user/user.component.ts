import { UserAchievementService } from './../../services/user-achievement.service';
import { UserAchievement } from 'src/app/models/user-achievement';
import { UserDetail } from './../../models/user-detail';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { User } from 'src/app/models/user';
import {FlexLayoutModule} from '@angular/flex-layout';

@Component({
  selector: "app-user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.component.css"]
})
export class UserComponent implements OnInit {
  user = null;
  selected = null;
  achievements = [];
  children = [];
  newChild = false;
  childrenAchivments = [];
  createChild=false;

  // for the UserAchievement objects
  pendingUserAchievements: UserAchievement[] = [];
  completedUserAchievements: UserAchievement[] = [];
  isFlipped: boolean[] = [];
  storedIndex: number = 0;

  // for card flexbox display
  colCounter: number = 0;

  navigateToUserProfile(username, parentname) {
    this.router.navigateByUrl('user/' + username + "/profile/parent");
  }
  openCreateChild(){
    this.createChild=true;
  }

  addChild(form: NgForm) {
    const user = new User(form.value.nickname, "password", true);
    // Add User
    this.auth.registerChild(user).subscribe(data => {
      this.userService
        .addChild(this.user.user.username, data)
        .subscribe(child => {
          // Add UserDetail
          child.nickname = form.value.nickname;
          child.firstName = form.value.firstName;
          child.lastName = form.value.lastName;
          child.dob = form.value.dob;
          child.userImageUrl = 'assets/default_profile_image.png';
          this.userService.updateUserDetail(child).subscribe(deets => {
            console.log(deets);
            // Reload parent's children array
            this.reloadChildren();
            this.createChild=false;
          });
        });
    });
  }

  // Reload parent's children array
  reloadChildren() {
    this.userService.getChildren(this.user.user.username).subscribe(data => {
      this.user.children = data;
      for (const child of this.user.children) {
        console.log(child);
        this.userAchievementService.getUserAchievementsByUserDetail(child).subscribe(
          achieves => {
            console.log(achieves);
            if (achieves) {
              child.achievments = achieves;
            }
          },
          err => {
            console.error('reloadChildren: Error');
            console.error(err);
          }
        );
      }
    });
  }

  reloadMeetings() {
    this.userService
      .getUserMeetings(this.user.user.username)
      .subscribe(data => (this.user.meetings = data));
  }

  getUserAchievements() {
    this.userAchievementService.getUserAchievementsByUserDetail(this.user).subscribe(
      data => {
        let ua: UserAchievement[] = data;
        for (let index = 0; index < ua.length; index++) {
          if (ua[index].achieved === true) {
            this.completedUserAchievements.push(ua[index])
            this.isFlipped[ua[index].id] = true;
          } else if (ua[index].achieved === false) {
            this.pendingUserAchievements.push(ua[index]);
            this.isFlipped[ua[index].id] = true;
          } else {
            console.log(ua[index]);
          }
        }
      },
      err => {
        console.error('UserAchievement.reload(): Error');
        console.error(err);
      }
    );
  }
  flipCard(id: number) {
    let flipped: string = '';
    this.isFlipped[id] = !this.isFlipped[id];
  }

  checkFlipped(id: number) {
    return this.isFlipped[id];
  }
  constructor(
    private auth: AuthService,
    private router: Router,
    private currentRoute: ActivatedRoute,
    private userService: UserService,
    private userAchievementService: UserAchievementService,
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
            this.getUserAchievements();


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
