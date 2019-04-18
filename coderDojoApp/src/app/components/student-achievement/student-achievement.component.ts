import { LocationService } from './../../services/location.service';
import { Achievement } from './../../models/achievement';
import { UserGoal } from './../../models/user-goal';
import { UserAchievementService } from './../../services/user-achievement.service';
import { UserGoalService } from './../../services/user-goal.service';
import { UserAchievement } from './../../models/user-achievement';
import { UserService } from 'src/app/services/user.service';
import { UserDetail } from 'src/app/models/user-detail';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AchievementService } from 'src/app/services/achievement.service';
import { GoalService } from 'src/app/services/goal.service';
import { RoleService } from 'src/app/services/role.service';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';

@Component({
  selector: "app-student-achievement",
  templateUrl: "./student-achievement.component.html",
  styleUrls: ["./student-achievement.component.css"]
})
export class StudentAchievementComponent implements OnInit {
  mode = 'studentList';
  students: UserDetail[] = [];
  selectedStudent: UserDetail = null;
  selectedStudentAchievements: UserAchievement[] = [];
  selectedUserAchievement: UserAchievement = null;
  avaliableAchievements: Achievement[] = [];
  newUserAchievement: UserAchievement = new UserAchievement();
  selectedAchievement: number;
  searchKey: string;
  locations = null;
  selectedLocationFilter: Location;

  listData: MatTableDataSource<any>;
  displayedColumns =  [
    'profileImage',
    'firstName',
    'lastName',
    'nickname',
  ];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;


  constructor(
    private userService: UserService,
    private roleService: RoleService,
    private achievementService: AchievementService,
    private userGoalService: UserGoalService,
    private userAchievementService: UserAchievementService,
    private locationService: LocationService
  ) { }

  ngOnInit() {
    this.loadLocations();
    this.index();
  }

  index() {
    this.getAllStudents();
    this.mode = 'studentList';
    this.selectedStudent = null;
  }

  loadStudentsByLocation(loc: any) {
    if (typeof loc === 'number') {
      this.locationService.getStudentsByLocation(loc).subscribe(
        data => {
          this.listData = new MatTableDataSource(data);
          this.listData.sort = this.sort;
          this.listData.paginator = this.paginator;
        },
        err => {
          console.error('StudentList.reload(): Error');
          console.error(err);
        }
      );
    } else {
      this.index();
    }
  }

  addUsernameToUserObjects(users) {
    for (const user of users) {
      user.username = user.user.username;
    }
    this.students = users;
  }

  loadAvaliableAchievements() {
    this.achievementService.index().subscribe(
      data => {
        this.avaliableAchievements = data;
        for (
          let index = 0;
          index < this.selectedStudentAchievements.length;
          index++
        ) {
          for (
            let index2 = 0;
            index2 < this.avaliableAchievements.length;
            index2++
          ) {
            if (
              this.selectedStudentAchievements[index].achievement.id ===
              this.avaliableAchievements[index2].id
            ) {
              this.avaliableAchievements.splice(index2, 1);
              break;
            }
          }
        }
      },
      err => {
        console.error("AvaliableAchievement.index(): Error");
        console.error(err);
      }
    );
  }

  getAllStudents() {
    this.roleService.getUsersByRole('STUDENT').subscribe(
      data => {
        this.students = data;
        this.listData = new MatTableDataSource(this.students);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
      },
      err => {
        console.error("StudentList.reload(): Error");
        console.error(err);
      }
    );
  }

  goBack() {
    this.selectedStudent = null;
    this.selectedStudentAchievements = [];
    this.newUserAchievement = new UserAchievement();
    this.index();
  }

  studentAchievementDetailView(student: UserDetail) {
    this.selectedStudent = student;
    this.getStudentUserAchievements(student);
    this.mode = 'studentAchievementDetailView';
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  loadLocations() {
    this.locationService.showAllLocations().subscribe(
      data => {
        this.locations = data;
      },
      err => {
      }
    );
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
  }

  achievedCheckBox(userAchievement: UserAchievement) {
    if (!userAchievement.achieved) {
      const currentDate = new Date();
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
  }

  achievementDetailView(userachievement: UserAchievement) {
    this.selectedUserAchievement = userachievement;
    this.mode = 'userAchievementDetail';
  }

  goalAchieved(userGoal: UserGoal) {
    userGoal.completed = !userGoal.completed;
    this.userGoalService.update(userGoal).subscribe(
      data => {
        this.getStudentUserAchievements(this.selectedStudent);
      },
      err => {
        console.error("StudentList.reload(): Error");
        console.error(err);
      }
    );
  }

  addAchievementToUser() {
    console.log(this.selectedAchievement);
    console.log(this.selectedUserAchievement);
    let ach: Achievement = null;
    for (let index = 0; index < this.avaliableAchievements.length; index++) {
      this.selectedAchievement = Number(this.selectedAchievement);
      if (this.avaliableAchievements[index].id === this.selectedAchievement) {
        ach = this.avaliableAchievements[index];
        break;
      }
    }

    this.newUserAchievement.userDetail = this.selectedStudent;
    this.newUserAchievement.achieved = false;
    this.newUserAchievement.achievedDate = null;
    this.newUserAchievement.id = null;
    this.newUserAchievement.achievement=ach;

    this.userAchievementService.create(this.newUserAchievement, this.selectedStudent.user.username).subscribe(
      data => {
      this.index();
      },
      err => {
        console.error('UserAchievement.create(): Error');
        console.error(err);
      }
    );
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
