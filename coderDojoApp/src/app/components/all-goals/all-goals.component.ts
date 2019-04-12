import { Component, OnInit } from '@angular/core';
import { Goal } from 'src/app/models/goal';

@Component({
  selector: 'app-all-goals',
  templateUrl: './all-goals.component.html',
  styleUrls: ['./all-goals.component.css']
})
export class AllGoalsComponent implements OnInit {

goals: Goal[];
selectedGoal: Goal = new Goal();


mode: string = 'index'

  constructor() { }

  ngOnInit() {
  }


  goalDetailView(goal: Goal){
    this.mode="goalDetail";
    this.selectedGoal = goal;
  }
  goBack(){
    this.mode="index";
    this.selectedGoal = new Goal();
  }
}
