import { GoalService } from './../../services/goal.service';
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


  public isCollapsed: boolean[] = [];
  isNewGoal: boolean = true;
  isExistingGoal: boolean = true;

  achievements: Achievement[];


  selectedAchievement: Achievement = new Achievement();
  selectedAchievementGoals: Goal[] = this.selectedAchievement.goals;
  mode: string = 'index';
  newGoal: Goal = new Goal();
  newAchievement: Achievement = new Achievement();

  selectedGoal = null;

  storedIndex: number=0


  constructor(private achievementService: AchievementService, private goalService: GoalService) { }

  ngOnInit() {
    // this.achievements.push(this.achievement1);
    this.reload();
  }

  reload() {
    this.achievementService.index().subscribe(
      data => {

        this.achievements = data;

        //     this is for the collapsable lists of goals. 
        this.isCollapsed = [];
        for (let i = 0; i < data.length; i++) {
          this.isCollapsed[data[i].id] = false;
        }
        
        
        // this is for the new goal post -> refresh
        this.selectedAchievement = this.achievements[this.storedIndex];
        this.selectedAchievementGoals = this.selectedAchievement.goals;
        this.newGoal = new Goal();
      },
      err => {
        console.error('TodoListComponent.reload(): Error');
        console.error(err);
      }
    );
  };

  goback() {
    this.mode = "index";
    this.newAchievement = new Achievement();
    this.newGoal = new Goal();
    this.selectedAchievement = null;
  };
  addGoalForm(achievement: Achievement) {
    this.mode = "addGoalForm";
    this.selectedAchievement = achievement;
  };
  achievementEditForm(achievement: Achievement) {
    // console.log(achievement.name);
    this.selectedAchievement = achievement;
    this.mode = 'achievementEditForm';
    this.selectedAchievementGoals = this.selectedAchievement.goals;
  };
  addGoalToAchievement(){
    this.newGoal.achievement = this.selectedAchievement;
    this.storedIndex = this.achievements.indexOf(this.selectedAchievement);
    this.goalService.create(this.newGoal).subscribe(
      data => {
        this.reload();
 
        // console.log(this.storedIndex);
        // console.log(this.selectedAchievement);
        
     
          },
          err => {
            console.error('all-achievementsComponent.newGoal(): Error');
            console.error(err);
          }
    )
  }

  deleteGoal(goal: Goal){
  this.goalService.destroy(goal.id).subscribe(
    data => {
      this.reload();

   
        },
        err => {
          console.error('all-achievementsComponent.newGoal(): Error');
          console.error(err);
        }
  )
}
 
editGoalForm(goal: Goal){
 this.selectedGoal = goal;
 this.mode="editGoalForm"
}

}
