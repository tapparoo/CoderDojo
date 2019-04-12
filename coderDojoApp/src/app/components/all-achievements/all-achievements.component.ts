import { AchievementService } from './../../services/achievement.service';
import { Achievement } from './../../models/achievement';
import { Component, OnInit } from '@angular/core';
import { Goal } from 'src/app/models/goal';


@Component({
  selector: 'app-all-achievements',
  templateUrl: './all-achievements.component.html',
  styleUrls: ['./all-achievements.component.css']
})
export class AllAchievementsComponent implements OnInit {

  // public isCollapsed = false;
  public isCollapsed: boolean[] = [];

  achievements: Achievement[];
//    = [ new Achievement(1, 'test', 'test test test', 'image test', 
//   [new Goal(1, 'testGoal', 'testGoalDescription')
//     ])
// ];
  
selectedAchievement: Achievement = new Achievement();
mode: string= 'index';
newGoal: Goal = new Goal();
newAchievement: Achievement = new Achievement();

  constructor(private achievementService: AchievementService) { }

  ngOnInit() {
    // this.achievements.push(this.achievement1);
    this.reload();
  }

  reload(){
    this.achievementService.index().subscribe(
      data => {
        this.achievements = data;
        this.isCollapsed = [];
        for (let i = 0; i < data.length; i++) {
          this.isCollapsed[data[i].id] = false;
        }
        console.log("this.isCollapsed:");
        console.log(this.isCollapsed);
      },
      err => {
        console.error('TodoListComponent.reload(): Error');
        console.error(err);
      }
    );
  };
  
  goback(){
    this.mode="index";
    this.newAchievement = new Achievement();
    this.newGoal = new Goal();
    this.selectedAchievement = null;
  };
  addGoalForm(achievement: Achievement){
  this.mode="addGoalForm";

  };
  addAchievementForm(){
    this.mode='addAchievementForm';

  };
}
