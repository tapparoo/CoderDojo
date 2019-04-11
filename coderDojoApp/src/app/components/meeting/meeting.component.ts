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
  constructor(private meetingService: MeetingService) { }

  ngOnInit() {
    this.reload();
  }

  reload() {
    this.meetingService.index().subscribe(
      data => {
        console.log(data);
        this.meetings = data;
        console.log();
      },
      err => {
        console.error(err);
      }
    );
  }
}
