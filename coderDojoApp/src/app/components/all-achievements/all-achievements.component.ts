import { Achievement } from './../../models/achievement';
import { Component, OnInit } from '@angular/core';
import { Goal } from 'src/app/models/goal';

@Component({
  selector: 'app-all-achievements',
  templateUrl: './all-achievements.component.html',
  styleUrls: ['./all-achievements.component.css']
})
export class AllAchievementsComponent implements OnInit {
achievements: Achievement[];
selectedAchievement: Achievement = new Achievement();
mode: string='index';
newGoal: Goal = new Goal();
newAchievement: Achievement = new Achievement();

  constructor() { }

  ngOnInit() {
  }
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
