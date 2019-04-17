import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { NgForm, FormBuilder, FormArray } from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { LocationService } from 'src/app/services/location.service';
import { RoleService } from 'src/app/services/role.service';
import { UserDetail } from 'src/app/models/user-detail';
import { MatSort, MatPaginator, MatTableDataSource } from '@angular/material';
import { Role } from 'src/app/models/role';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  users = [];
  user = null;
  editUser = false;
  locations = null;
  searchKey: string;
  editUserRolesForm: FormGroup;

  selectedRoleFilter: Role;
  roles: Role[] = [];

  listData: MatTableDataSource<any>;
  displayedColumns =  [
    'firstName',
    'lastName',
    'nickname',
    'username',
    'phoneNumber'
  ];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  loadUsersByRole(role: string) {
    if (role.toUpperCase() !== 'all'.toUpperCase()) {
      this.roleService.getUsersByRole(role).subscribe(
        data => {
          this.listData = new MatTableDataSource(data);
          this.listData.sort = this.sort;
          this.listData.paginator = this.paginator;
          this.addUsernameToUserObjects(data);
        },
        err => {
          console.log(err);
          console.log('Error loading users from admin page');
        }
      );
    } else {
      this.displayUsers();
    }
  }

  displayUsers(): void {
    this.userService.index().subscribe(
      data => {
        this.addUsernameToUserObjects(data);
        this.listData = new MatTableDataSource(data);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
      },
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  addUsernameToUserObjects(users) {
    for (const user of users) {
      user.username = user.user.username;
    }

    this.users = users;
  }

  showUser(username: string) {
    this.userService.getUser(username).subscribe(
      data => {
        this.user = data;
        this.userService.getParents(username).subscribe(
          parents => {
            this.user.parents = parents;
          },
          err => {
            console.log(err);
            console.log('Error loading parents from admin page');
          }
        );
        this.userService.getRoles(username).subscribe(
          roles => {
            this.user.user.roles = roles;
            this.populateUserRolesCheckboxes(roles);
            // select user's current roles on load

          },
          err => {
            console.log(err);
            console.log('Error loading parents from admin page');
          }
        );
      },
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  updateUser(form: NgForm) {
    this.user.firstName = form.value.firstName;
    this.user.lastName = form.value.lastName;
    this.user.nickname = form.value.nickname;
    this.user.phoneNumber = form.value.phoneNumber;
    this.user.email = form.value.email;
    this.user.dob = form.value.dob;


    this.userService.updateUserDetail(this.user).subscribe(
      data => {
        this.user = data;
        this.editUser = false;
        const oldUser = this.user.user;

        if (oldUser.username !== form.value.username || form.value.password) {
          if (oldUser.username !== form.value.username) {
            oldUser.username = form.value.username;
          }
          if (form.value.password) {
            oldUser.password = form.value.password;
          }

          console.log('updating user');
          this.userService.updateUser(oldUser).subscribe(
            updatedUser => {
              this.user.user = updatedUser;
              this.displayUsers();
            },
            err => {
              console.log(err);
              console.log('Error updating username password from admin panel');
            }
          );
        } else {
          this.displayUsers();
        }

      },
      err => {
        console.log(err);
        console.log('Error loading users from admin page');
      }
    );
  }

  loadLocations() {
    this.locationService.showAllLocations().subscribe(
      data => {
        this.locations = data;
      },
      err => {
      }
    );
  }

  loadRoles() {
    this.roleService.getRoles().subscribe(
      data => {
        this.roles = data;
      },
      err => {
      }
    );
  }

  updateUserRoles(user: UserDetail) {
    this.user.user.roles = [...this.editUserRolesForm.value.userrole];

    this.userService.updateRoles(this.user).subscribe(
      data => {
        this.user.user.roles = data;
        this.displayUsers();
      },
      err => {
        console.log(err);
      }
    );
  }

  userHasRole(role: any) {
    for (let i = 0; i < this.user.user.roles.length; i ++) {
      if (this.user.user.roles[i].name === role) {
          return true;
      }
    }
    return false;
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  populateUserRolesCheckboxes(roles: any[]) {
    // reload user roles form
    this.editUserRolesForm = this.fb.group({
      userrole: this.fb.array([])
    });

    // apply
    for (const r of roles) {
        this.onChange(r, true);
    }
  }

  // For changing user roles in the admin panel
  onChange(role: any, checked) {

    const roleFormArray = this.editUserRolesForm.controls.userrole as FormArray;
    if (checked) {
      roleFormArray.push(new FormControl(role));
    } else {
      const index = roleFormArray.controls.findIndex(x => x.value === role);
      roleFormArray.removeAt(index);
    }
  }

  constructor(
    private userService: UserService,
    private roleService: RoleService,
    private locationService: LocationService,
    private router: Router,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.loadRoles();
    this.displayUsers();
  }
}
