import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { MeetingService } from './../../services/meeting.service';
import { Meeting } from './../../models/meeting';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-meeting',
  templateUrl: './meeting.component.html',
  styleUrls: ['./meeting.component.css']
})
export class MeetingComponent implements OnInit {

  meetings: Meeting[] = [];
  isAuthorized = false;
  constructor(private meetingService: MeetingService,
              private auth: AuthService,
              private router: Router) { }

  ngOnInit() {
    this.reload();
  }
  checkLogin() {
    if (!this.auth.checkLogin()) {
        return this.router.navigateByUrl('/home');
    }
  }

  reload() {
    console.log(localStorage);
    this.meetingService.index().subscribe(
      data => {
        console.log(data);
        this.meetings = data;
      },
      err => {
        console.error(err);
      }
    );
  }
}
