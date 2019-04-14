import { UserDetail } from './../../models/user-detail';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users = null;
  selected = null;

  showAll() {
    this.userService.index().subscribe(
      data => this.users = data,
      err => {
        console.log(err);
        console.log('Error getting list of users from UserComponent as admin');
      }
    );
  }

  reload() {

  }

  constructor(
    private auth: AuthService,
    private router: Router,
    private userService: UserService
    ) { }

  ngOnInit() {
    this.showAll();
  }

}
