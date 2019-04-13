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
  mode: string = 'index';
  newGoal: Goal = new Goal();
  newAchievement: Achievement = new Achievement();

  constructor(private achievementService: AchievementService, private goalService: GoalService) { }

  ngOnInit() {
    // this.achievements.push(this.achievement1);
    this.reload();
  }

  reload() {
    this.achievementService.index().subscribe(
      data => {
        this.achievements = data;
        this.isCollapsed = [];
        for (let i = 0; i < data.length; i++) {
          this.isCollapsed[data[i].id] = false;
        }
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
  addAchievementForm() {
    this.mode = 'addAchievementForm';
  };
  addGoalToAchievement(){
    this.newGoal.achievement = this.selectedAchievement;
    this.goalService.create(this.newGoal).subscribe(
      data => {
            this.reload();
            this.selectedAchievement = null;
            this.newGoal = new Goal();
          },
          err => {
            console.error('all-achievementsComponent.newGoal(): Error');
            console.error(err);
          }
    )
  }

}
// this.todoService.create(todo).subscribe(
//   data => {
//     this.reload();
//   },
//   err => {
//     console.error('TodoListComponent.addTodo(): Error');
//     console.error(err);
//   }
// );
// this.newTodo = new Todo();
// }