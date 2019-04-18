import { UserService } from 'src/app/services/user.service';
import { Location } from './../../models/location';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { MeetingService } from './../../services/meeting.service';
import { Meeting } from './../../models/meeting';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { LocationService } from 'src/app/services/location.service';

@Component({
  selector: 'app-meeting',
  templateUrl: './meeting.component.html',
  styleUrls: ['./meeting.component.css']
})
export class MeetingComponent implements OnInit {
  meetings: Meeting[] = [];
  meeting;
  newMeeting;
  isAuthorized = false;
  editMeeting = false;
  selected = null;
  displayAttendees = false;
  searchKey: string;
  isCreateMeeting = false;

  selectedLocationValue: Location;
  locations: Location[] = [];


  constructor(
    private meetingService: MeetingService,
    private auth: AuthService,
    private router: Router,
    private locationService: LocationService,
    private userService: UserService
  ) {}
  createMeeting = false;

  listData: MatTableDataSource<any>;
  displayedColumns = [  ];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.loadLocations();
    if (this.router.url === '/schedule') {
      this.reload();
    } else if (this.router.url === '/admin') {
      this.reloadAdmin();
    }
  }

  loadMeetingsByLocation(event) {
    if (typeof event.value === 'number') {
    this.reloadAdminByLocation(event.value);
  } else {
      this.reload();
    }
  }

  loadLocations() {
    this.locationService.showAllLocations().subscribe(
      data => {
        this.locations = data;
      },
      err => {
        console.error(err);
      }
    );
  }

  reloadAdminByLocation(id: number) {
    this.meetingService.getMeetingsByLocation(id).subscribe(
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
        this.meetings = data;
        this.isAuthorized = true;
      },
      err => {
        console.error(err);
      }
    );
  }

  reload() {
    this.meetingService.showSchedule().subscribe(
      data => {
        this.displayedColumns = [
          'name',
          'location.name',
          'scheduledTime',
          'register'
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
        this.meetings = data;
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
  cancelCreate(){
    this.createMeeting = false;
  }
  openCreate(){
    this.createMeeting = !this.createMeeting;
  }

  closeDisplayAttend(){
    this.displayAttendees = false;
  }
  addMeeting() {
    this.meetingService.create(this.newMeeting).subscribe(
      data => {
        this.isCreateMeeting = !this.createMeeting;
        this.reloadAdmin();
      },
      err => {
        console.error(err);
      }
    );
    this.newMeeting = null;
  }

  registerUserForMeeting(meetingId: number) {
    this.meetingService.registerUserForMeeting(meetingId, this.auth.getLoggedInUsername()).subscribe(
      data => {
        this.reload();
      },
      err => {
        console.error(err);
      }
    );

  }


  deleteMeeting(id) {
    this.meetingService.destroy(id).subscribe(
      data => {
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
  }

  openCreateForm() {
    this.isCreateMeeting = !this.isCreateMeeting;
  }
  closeCreateForm(){
    this.isCreateMeeting =false;
  }
  changeStatus(meetId:number, meetingAttendent, e){
      this.meetingService.updateMeetingAttendeeStatus(meetId, meetingAttendent, e.target.checked ).subscribe(
        data => {
          this.reloadAdmin();
          this.editMeeting = false;
        },
        err => {
            console.error(err);
            console.log('Error in update');
        }
      );

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
