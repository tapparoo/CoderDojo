import { AllAchievementsComponent } from './components/all-achievements/all-achievements.component';
import { AdminDasboardComponent } from './components/admin-dasboard/admin-dasboard.component';
import { MeetingComponent } from './components/meeting/meeting.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from './components/user/user.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', component: HomeComponent },
  { path: 'meetings', component: MeetingComponent },
  { path: 'schedule', component: MeetingComponent },
  { path: 'admin', component: AdminDasboardComponent },
  // { path: 'admin', component: AdminComponent },
  { path: 'user', component: UserComponent },
  { path: 'user/:username', component: UserComponent },
  { path: 'all-achievements', component: AllAchievementsComponent  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
