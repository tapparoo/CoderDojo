import { UserService } from 'src/app/services/user.service';
import { UserDetail } from 'src/app/models/user-detail';
import { Component, OnInit } from '@angular/core';
import { AchievementService } from 'src/app/services/achievement.service';
import { GoalService } from 'src/app/services/goal.service';
import { ResourceLoader } from '@angular/compiler';
import { RoleService } from 'src/app/services/role.service';

@Component({
  selector: 'app-student-achievement',
  templateUrl: './student-achievement.component.html',
  styleUrls: ['./student-achievement.component.css']
})
export class StudentAchievementComponent implements OnInit {
  mode: string = "studentList"
  students: UserDetail[] = [];
  selectedStudent: UserDetail = null;

  constructor(private userService: UserService, private roleService: RoleService, private achievementService: AchievementService, private goalService: GoalService) { }

  ngOnInit() {
    this.reload();
  }

  index() {
    this.reload();
    this.mode = "studentList"
    this.selectedStudent = null;

  }
  reload() {
    this.roleService.getUsersByRole('STUDENT').subscribe(
      data => {

        this.students = data;
      },
      err => {
        console.error('StudentList.reload(): Error');
        console.error(err);
      }
    );
  };


  studentAchievementDetailView(student: UserDetail) {
    this.selectedStudent = student;
    this.getStudentAchievements(student);
    this.mode = "studentAchievementDetailView";
    console.log(this.selectedStudent.achievements);
    
  }

  getStudentAchievements(student: UserDetail){
    this.userService.getUserAchievements(student.user.username).subscribe(
      data => {

        this.selectedStudent.achievements = data;
      },
      err => {
        console.error('StudentList.reload(): Error');
        console.error(err);
      }
    );
  };

}
