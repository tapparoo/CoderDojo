import { UserGoal } from './../../models/user-goal';
import { UserAchievementService } from './../../services/user-achievement.service';
import { UserGoalService } from './../../services/user-goal.service';
import { UserAchievement } from './../../models/user-achievement';
import { UserService } from 'src/app/services/user.service';
import { UserDetail } from 'src/app/models/user-detail';
import { Component, OnInit } from '@angular/core';
import { AchievementService } from 'src/app/services/achievement.service';
import { GoalService } from 'src/app/services/goal.service';
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
  selectedStudentAchievementsStatus: UserAchievement[] = [];
  selectedUserAchievement = null;
  constructor(private userService: UserService, private roleService: RoleService, private achievementService: AchievementService, private userGoalService: UserGoalService, private userAchievementService: UserAchievementService) { }

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

goBack(){
  this.selectedStudent = null;
  this.selectedStudentAchievementsStatus = [];
  this.index();
}
  studentAchievementDetailView(student: UserDetail) {
    this.selectedStudent = student;
    // this.getStudentAchievements(student);
    this.getStudentUserAchievements(student);
    this.mode = "studentAchievementDetailView";
    // console.log(this.selectedStudent.achievements);
    
  }

  getStudentAchievements(student: UserDetail){
    this.userService.getUserAchievements(student.user.username).subscribe(
      data => {
        
        this.selectedStudent.achievements = data;
        
        // console.log(this.selectedStudent.achievements)
        this.getStudentUserAchievements(student)
      },
      err => {
        console.error('StudentList.reload(): Error');
        console.error(err);
      }
    );
  };

  getStudentUserAchievements(student: UserDetail){
    this.userAchievementService.getUserAchievementsByUserDetail(student).subscribe(
      data => {
        
        this.selectedStudentAchievementsStatus = data;
        console.log(this.selectedStudentAchievementsStatus);
        
        
        // console.log(this.selectedStudent.achievements)
      },
      err => {
        console.error('StudentList.reload(): Error');
        console.error(err);
      }
    );
  };

  achievedCheckBox(userAchievement: UserAchievement){
    if(!userAchievement.achieved){
      let currentDate = new Date();
      console.log(currentDate);
      userAchievement.achievedDate= currentDate;
    }
    userAchievement.achieved = !userAchievement.achieved;
    this.userAchievementService.update(userAchievement).subscribe(
      data => {
        this.getStudentUserAchievements(this.selectedStudent);
        
        
      },
      err => {
        console.error('StudentList.reload(): Error');
        console.error(err);
      }
    );
  };

  achievementDetailView(userachievement: UserAchievement){
    this.selectedUserAchievement = userachievement;
    // console.log(userachievement);
    
    this.mode='userAchievementDetail';
  }
  goalAchieved(userGoal: UserGoal){
    userGoal.completed = !userGoal.completed;
    this.userGoalService.update(userGoal).subscribe(

    )

  }
}
