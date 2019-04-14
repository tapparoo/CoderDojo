import { UserDetail } from './../../models/user-detail';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Address } from 'src/app/models/address';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  reload() {

  }

  constructor(
    private auth: AuthService,
    private currentRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService
    ) { }

  ngOnInit() {
  }

}
