import { Achievement } from './../../models/achievement';
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
  selectedStudentAchievements: UserAchievement[] = [];
  selectedUserAchievement: UserAchievement = null;

  avaliableAchievements: Achievement[] = [];

  newUserAchievement: UserAchievement = new UserAchievement();
  selectedAchievement: number;

  constructor(private userService: UserService, private roleService: RoleService, private achievementService: AchievementService, private userGoalService: UserGoalService, private userAchievementService: UserAchievementService) { }

  ngOnInit() {
    this.reload();
  }

  index() {
    this.reload();
    this.mode = "studentList"
    this.selectedStudent = null;

  }
  loadAvaliableAchievements() {
    this.achievementService.index().subscribe(
      data => {
        this.avaliableAchievements = data;
        for (let index = 0; index < this.selectedStudentAchievements.length; index++) {
          for (let index2 = 0; index2 < this.avaliableAchievements.length; index2++) {
            if (this.selectedStudentAchievements[index].achievement.id === this.avaliableAchievements[index2].id) {
              this.avaliableAchievements.splice(index2, 1);
              break;
            }

          }

        }


      },
      err => {
        console.error('AvaliableAchievement.index(): Error');
        console.error(err);
      }
    );
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

  goBack() {
    this.selectedStudent = null;
    this.selectedStudentAchievements = [];
    this.newUserAchievement = new UserAchievement();
    this.index();
  }
  studentAchievementDetailView(student: UserDetail) {
    this.selectedStudent = student;
    // this.getStudentAchievements(student);
    this.getStudentUserAchievements(student);
    this.mode = "studentAchievementDetailView";
    // console.log(this.selectedStudent.achievements);

  }


  getStudentUserAchievements(student: UserDetail) {
    this.userAchievementService.getUserAchievementsByUserDetail(student).subscribe(
      data => {

        this.selectedStudentAchievements = data;
        this.loadAvaliableAchievements();
      },
      err => {
        console.error('StudentList.reload(): Error');
        console.error(err);
      }
    );
  };

  achievedCheckBox(userAchievement: UserAchievement) {
    if (!userAchievement.achieved) {
      let currentDate = new Date();
      console.log(currentDate);
      userAchievement.achievedDate = currentDate;
    }
    userAchievement.achieved = !userAchievement.achieved;
    this.userAchievementService.update(userAchievement, this.selectedStudent).subscribe(
      data => {
        this.getStudentUserAchievements(this.selectedStudent);


      },
      err => {
        console.error('StudentList.reload(): Error');
        console.error(err);
      }
    );
  };

  achievementDetailView(userachievement: UserAchievement) {
    this.selectedUserAchievement = userachievement;
    // console.log(userachievement);

    this.mode = 'userAchievementDetail';
  }
  goalAchieved(userGoal: UserGoal) {
    userGoal.completed = !userGoal.completed;
    this.userGoalService.update(userGoal).subscribe(
      data => {
        this.getStudentUserAchievements(this.selectedStudent);


      },
      err => {
        console.error('StudentList.reload(): Error');
        console.error(err);
      }
    );
  }

  addAchievementToUser() {


    // let selectedAchievement: Achievement = ;
    console.log(this.selectedAchievement);
    console.log(this.selectedUserAchievement);
    // console.log(this.avaliableAchievements.length)
    let ach: Achievement = null;
    for (let index = 0; index < this.avaliableAchievements.length; index++) {
      this.selectedAchievement = Number(this.selectedAchievement);
      if (this.avaliableAchievements[index].id === this.selectedAchievement) {
        ach = this.avaliableAchievements[index];
        // console.log(ach);
        break;
      }
    }

    this.newUserAchievement.userDetail = this.selectedStudent;
    this.newUserAchievement.achieved = false;
    this.newUserAchievement.achievedDate = null;
    this.newUserAchievement.id = null;
    this.newUserAchievement.achievement=ach;
    console.log(this.newUserAchievement);
    

    this.userAchievementService.create(this.newUserAchievement, this.selectedStudent.user.username).subscribe(
      data => {
      this.index();
      },
      err => {
        console.error('UserAchievement.create(): Error');
        console.error(err);
      }
    );


    // console.log(this.newUserAchievement)

  }

  deleteUserAchievement(){
  this.userAchievementService.destroy(this.selectedUserAchievement.id).subscribe(
    data => {
      this.index();
      },
      err => {
        console.error('deleteUserAchievement.create(): Error');
        console.error(err);
      }
    );

  }
}
