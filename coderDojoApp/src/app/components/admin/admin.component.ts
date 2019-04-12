import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  users = [];
  user = null;

  displayUsers(): void {
    this.userService.index().subscribe(
      data => this.users = data,
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  showUser(id: number) {
    this.userService.getUser(id).subscribe(
      data => this.user = data,
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.userService.index().subscribe(
      data => this.users = data,
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

}
