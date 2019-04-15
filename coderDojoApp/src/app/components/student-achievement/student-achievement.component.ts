import { UserService } from 'src/app/services/user.service';
import { UserDetail } from 'src/app/models/user-detail';
import { Component, OnInit } from '@angular/core';
import { AchievementService } from 'src/app/services/achievement.service';
import { GoalService } from 'src/app/services/goal.service';
import { ResourceLoader } from '@angular/compiler';

@Component({
  selector: 'app-student-achievement',
  templateUrl: './student-achievement.component.html',
  styleUrls: ['./student-achievement.component.css']
})
export class StudentAchievementComponent implements OnInit {
mode: string = "studentList"
students: UserDetail[] = [];

  constructor(private userService: UserService, private achievementService: AchievementService, private goalService: GoalService) { }

  ngOnInit() {
    this.reload();
  }

  index(){

  }
reload() {
    this.userService.getUsersByRole('STUDENT').subscribe(
      data => {

        this.students = data;
      },
      err => {
        console.error('StudentList.reload(): Error');
        console.error(err);
      }
    );
  };

}
