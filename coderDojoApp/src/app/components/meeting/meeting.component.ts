import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { MeetingService } from './../../services/meeting.service';
import { Meeting } from './../../models/meeting';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormControl, FormGroup, Validators} from '@angular/forms';

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
  displayAttendees = false;
  searchKey: string;
  isCreateMeeting = false;

  constructor(
    private meetingService: MeetingService,
    private auth: AuthService,
    private router: Router
  ) {}

  listData: MatTableDataSource<any>;
  displayedColumns = [  ];
@ViewChild(MatSort) sort: MatSort;
@ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    if (this.router.url === '/schedule') {
      this.reload();
    } else if (this.router.url === '/admin') {
      this.reloadAdmin();

    }

  }

  reload() {
    this.meetingService.showSchedule().subscribe(
      data => {
        console.log(data);
        // this.meetings = data;
        this.displayedColumns = [
          'name',
          'location.name',
          'scheduledTime'
        ];
        this.listData = new MatTableDataSource(data);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
        console.log(this.meetings + 'this.meetings');
        this.isAuthorized = false;
      },
      err => {
        console.error(err);
      }
    );
  }

  reloadAdmin() {
    this.meetingService.index().subscribe(
      data => {
        // this.meetings = data;
        console.log(data);
        this.listData = new MatTableDataSource(data);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;

        this.displayedColumns = [
          'name',
          'location.name',
          'scheduledTime',
          'Attendees',
          'Actions'
        ];
        this.isAuthorized = true;
      },
      err => {
        console.error(err);
      }
    );
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  setEditMeeting(meeting) {
    this.editMeeting = true;
    this.meeting = meeting;
  }

  addMeeting() {
    console.log(this.newMeeting);
    this.meetingService.create(this.newMeeting).subscribe(
      data => {
        console.log(data);
        this.reloadAdmin();
      },
      err => {
        console.error(err);
      }
    );
    this.newMeeting = new Meeting();
  }

  addPerson(createForm){
    console.log(createForm);
    // this.newMeeting = {
    //   name: createForm.value.name,
    //   scheduledTime: createForm.value.scheduledTime,
    //   location: {
    //     id: createForm.value.locationId}
    // }
    this.meetingService.create(createForm.value).subscribe(
      data => {
        console.log(data);
        this.reloadAdmin();
      },
      err => {
        console.error(err);
      }
    );

  }


  deleteMeeting(id: number) {
    console.log(id);
    this.meetingService.destroy(id).subscribe(
      data => {
        console.log("delete data + " + data);
        this.reloadAdmin();
      },
      err => {
        console.error(err);
      }
    );
  }
  updateMeeting(meeting) {
    this.meetingService.update(meeting).subscribe(
      data => {
        this.reloadAdmin();
        // this.selected = data;
        console.log(data + 'datadatadata');
        this.editMeeting = false;
      },
      err => {
        console.error(err);
        console.log('Error in update');
      }
    );
  }

  displayAttendeesList(meeting) {
    this.selected = meeting;
    this.displayAttendees = true;
    console.log(this.selected);
  }

  openCreateForm() {
    this.isCreateMeeting = true;
  }
}

// export class MeetingComponent implements OnInit {
//   meetings: Meeting[] = [];
//   meeting = new Meeting();
//   newMeeting = new Meeting();
//   isAuthorized = false;
//   editMeeting = false;
//   selected = null;
//   displayAttendees = false;

//   constructor(private meetingService: MeetingService,
//               private auth: AuthService,
//               private router: Router) { }

//   ngOnInit() {
//     if (this.router.url === '/schedule') {
//       this.reload();
//     } else if (this.router.url === '/admin') {
//     this.reloadAdmin();
//   }
//     console.log(this.newMeeting, 'newMeeting');

//   }

//   reload() {
//     this.meetingService.showSchedule().subscribe(
//       data => {
//         console.log(data);
//         this.meetings = data;
//         console.log(this.meetings + 'this.meetings');
//         this.isAuthorized = false;
//       },
//       err => {
//         console.error(err);
//       }
//     );
//   }

//   reloadAdmin() {
//     this.meetingService.index().subscribe(
//       data => {
//         this.meetings = data;
//         this.isAuthorized = true;
//       },
//       err => {
//         console.error(err);
//       }
//     );
//   }
//   setEditMeeting(meeting) {
//     this.editMeeting = true;
//     this.meeting = meeting;
//   }

//   addMeeting() {
//     console.log(this.newMeeting);
//     this.meetingService.create(this.newMeeting).subscribe(
//       data => {
//         console.log(data);
//         this.reloadAdmin();
//       },
//       err => {
//           console.error(err);
//       }
//     );
//     this.newMeeting = new Meeting();
//   }

//   deleteMeeting(meeting) {
//     this.meetingService.destroy(meeting.id).subscribe(
//       data => {
//         console.log('updated datd + ' + data);
//         this.reloadAdmin();
//       },
//       err => {
//           console.error(err);
//       }
//     );
//   }

//   updateMeeting(meeting) {
//     this.meetingService.update(meeting).subscribe(
//       data => {
//         this.reloadAdmin();
//         // this.selected = data;
//         console.log(data + "datadatadata");
//         this.editMeeting = false;
//       },
//       err => {
//           console.error(err);
//           console.log('Error in update');
//       }
//     );

//   }

//   displayAttendeesList(meeting) {
//     this.selected = meeting;
//     this.displayAttendees = true;
//     console.log(this.selected);
//   }

// }
