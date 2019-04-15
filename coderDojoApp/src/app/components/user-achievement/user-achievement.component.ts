import { UserService } from './../../services/user.service';
import { UserDetail } from './../../models/user-detail';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-user-achievement',
  templateUrl: './user-achievement.component.html',
  styleUrls: ['./user-achievement.component.css']
})
export class UserAchievementComponent implements OnInit {
  user: User = null;


  constructor(private userService: UserService) { }

  ngOnInit() {
  }
// loadUser(){
//   this.user = this.userService.findByUsername(principal.getName());
// return user.children;
// }
}
