import { UserDetail } from './../../models/user-detail';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  user = null;
  selected = null;
  achievements = [];

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
            this.user = data;
            this.userService.getUserAchievements(this.user.user.username).subscribe(
              achieves => {
                this.user.achievements = achieves;
                this.achievements = achieves;
                console.log(this.user);

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
