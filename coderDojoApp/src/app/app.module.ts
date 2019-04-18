import { MaterialModule } from './material/material.module';
import { MeetingService } from './services/meeting.service';
import { AuthService } from './services/auth.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MeetingComponent } from './components/meeting/meeting.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { AdminComponent } from './components/admin/admin.component';
import { UserComponent } from './components/user/user.component';
import { AllAchievementsComponent } from './components/all-achievements/all-achievements.component';
import { AllGoalsComponent } from './components/all-goals/all-goals.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AdminDasboardComponent } from './components/admin-dasboard/admin-dasboard.component';
import { ProfileComponent } from './components/profile/profile.component';
import { UserAchievementComponent } from './components/user-achievement/user-achievement.component';
import { StudentAchievementComponent } from './components/student-achievement/student-achievement.component';
import { DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material';
import {FlexLayoutModule} from '@angular/flex-layout';

@NgModule({
  declarations: [
    AppComponent,
    MeetingComponent,
    LoginComponent,
    HomeComponent,
    AdminComponent,
    UserComponent,
    AllAchievementsComponent,
    AllGoalsComponent,
    NavbarComponent,
    AdminDasboardComponent,
    AllAchievementsComponent,
    ProfileComponent,
    UserAchievementComponent,
    StudentAchievementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule.forRoot(),
    MaterialModule,
    FormsModule,
    MatButtonModule,
    FlexLayoutModule
  ],
  providers: [AuthService, MeetingService, DatePipe ],
  bootstrap: [AppComponent]
})
export class AppModule { }
