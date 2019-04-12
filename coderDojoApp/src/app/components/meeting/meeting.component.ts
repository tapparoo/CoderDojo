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
  meeting = new Meeting();
  newMeeting = new Meeting();
  isAuthorized = false;
  editMeeting = false;
  selected = null;
  constructor(private meetingService: MeetingService,
              private auth: AuthService,
              private router: Router) { }

  ngOnInit() {
    this.reload();
  }
  checkAuthority() {
    const authorities = this.auth.getAuthorities();
    if (authorities !== null ) {
      const vals = Object.values(authorities);
      for (const val of vals) {
        if (val && val['authority'] === 'ADMIN') {
          this.isAuthorized = true;
        }
      }
    }
  }

  reload() {
    this.meetingService.index().subscribe(
      data => {
        console.log(data);
        this.meetings = data;
      },
      err => {
        console.error(err);
      }
    );
    this.checkAuthority();
    console.log(this.isAuthorized);

  }

  setEditMeeting(meeting) {
    this.editMeeting = true;
    this.meeting = meeting;
  }

  addMeeting() {
    console.log(this.newMeeting);
    // this.newMeeting.location.id = 1;
    console.log(this.newMeeting);
    this.meetingService.create(this.newMeeting).subscribe(
      data => {
        console.log(data);
        //this.reload();
      },
      err => {
          console.error(err);
      }
    );
    this.newMeeting = new Meeting();
  }

  deleteMeeting(meeting) {
    console.log(meeting);
  }

  updateMeeting(meeting) {

  }

}
