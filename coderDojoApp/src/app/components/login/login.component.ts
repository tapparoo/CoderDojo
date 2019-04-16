import { Component, OnInit } from "@angular/core";
import { NgForm } from "@angular/forms";
import { Router } from "@angular/router";
import { User } from "src/app/models/user";
import { AuthService } from "src/app/services/auth.service";
import { UserService } from "src/app/services/user.service";
import { Location } from "./../../models/location";
import { LocationService } from 'src/app/services/location.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  registering = false;
  buttonText = this.loggedIn() ? this.auth.getLoggedInUsername() : 'Login';
  newUser = null;
  selectedLocationValue: Location;
  locations: Location[] = [];

  selectUserLocation(){
    console.log(event);
    console.log(this.selectedLocationValue);


  }
  constructor(private auth: AuthService, private userService: UserService, private router: Router,
    private locationService: LocationService) { }

  ngOnInit() {
    this.loadLocations();
    this.loggedIn();
  }
  loadLocations() {
    this.locationService.showAllLocations().subscribe(
      data => {
        this.locations = data;
        console.log(data);

      },
      err => {
        console.error(err);
      }
    );
  }

  loggedIn() {
    if (this.auth.checkLogin()) {
      this.auth.setUsername();
      return true;
    } else {
      return false;
    }
  }

  login(form: NgForm) {
    const user = form.value.username;
    const pw = form.value.password;

    this.auth.login(user, pw).subscribe(
      next => {
        this.loggedIn();
        this.buttonText = this.auth.getLoggedInUsername();
        document.getElementById('loginDropdown').classList.remove('show');
        console.log('LoginComponent.login(): user logged in, routing to default page by role/authority.');

        const auth = [];
        for (const a of next.authorities) {
          auth.push(a.authority);
        }

        if (auth.indexOf('ADMIN') > -1 ) {
          this.router.navigateByUrl('admin');
        } else {
          this.router.navigateByUrl(`user/${this.auth.getLoggedInUsername()}`);
        }
      },
      error => {
        console.error('LoginComponent.login(): error logging in.');
        console.log(error);

      }
    );
  }

  register(form: NgForm) {
    let userLocation = {
      id : this.selectedLocationValue
    }
    const user = new User(
      form.value.username, form.value.password, true
    );
    this.auth.register(user).subscribe(
      data => {
        this.loggedIn();
        this.buttonText = this.auth.getLoggedInUsername();
        document.getElementById('loginDropdown').classList.remove('show');
        this.newUser = data;
        this.newUser.email = form.value.email;
        this.newUser.phoneNumber = form.value.phoneNumber;
        this.newUser.location = userLocation;
        console.log(this.newUser);
        this.userService.updateUserDetail(this.newUser).subscribe(
          userDetailData => {
            this.registering = false;
            this.router.navigateByUrl(`/user/${user.username}/profile`);
          },
          err => {
            console.log(err);
            console.log('Error updating new userdetails from registration page');
          }
        );
      },
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  logout() {
    this.auth.logout();
    this.buttonText = 'Login';
    this.registering = false;
    this.newUser = null;
    this.loggedIn();
    document.getElementById('profileDropdown').classList.remove('show');
    this.router.navigateByUrl('/home');
  }

  getUsername(){
    this.auth.setUsername();
    return this.auth.getLoggedInUsername();
  }

}
