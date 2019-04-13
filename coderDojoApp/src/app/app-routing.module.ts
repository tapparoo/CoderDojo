import { AdminDasboardComponent } from './components/admin-dasboard/admin-dasboard.component';
import { RegisterComponent } from './components/register/register.component';
import { MeetingComponent } from './components/meeting/meeting.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AdminComponent } from './components/admin/admin.component';
import { UserComponent } from './components/user/user.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'meetings', component: MeetingComponent },
  { path: 'schedule', component: MeetingComponent },
  // {path: 'admin', component: MeetingComponent },
  { path: 'admin', component: AdminDasboardComponent },
  // { path: 'admin', component: AdminComponent },
  { path: 'user', component: UserComponent },
  { path: 'user/:username', component: UserComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
