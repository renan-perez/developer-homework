import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../services/user.service';

import { BaseComponent } from '../base.component';

import { User } from '../../model/user';
import { SystemResponse } from '../../model/system-response';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent extends BaseComponent implements OnInit {

  user: User;
  userFound: Boolean;
  emailOk: Boolean;
  nameOk: Boolean;
  section: String;
  systemResponseUser: SystemResponse<User>;
  infoMessage: String;

  EMAIL: String = "email";
  NAME: String = "name";

  constructor(
    private router: Router,
    private userService: UserService
  ) {
    super();
  }

  ngOnInit() {
    this.user = new User();
    this.user.email = 'reperez.rp@gmail.com';
    this.userFound = null;
    this.emailOk = false;
    this.nameOk = false;
    this.section = this.EMAIL;
  }

  ok() {
    this.getUser(this.user.email);
  }

  register() {
    this.section = this.NAME;
    this.infoMessage = null;
  }

  saveUser() {
    this.nameOk = false;
    this.userService.saveOrUpdateUser(this.user) 
            .subscribe(
                (response: SystemResponse<User>) => this.systemResponseUser = response,
                err => console.log(err),
                () => this.validateUserFound(this.systemResponseUser)
            );
  }

  cancel() {
    this.section = this.EMAIL;
    this.userFound = null;
    this.user.name = null;
  }

  startDiscussions(user: User) {
    localStorage.setItem('currentUserEmail', user.email.toString());
    localStorage.setItem('currentUserName', user.name.toString());
    this.router.navigate(['/discussions/discussionsBoard'], { skipLocationChange: true });
  }

  onKeyUp() {
    switch (this.section) {
      case this.EMAIL:
        this.userFound = null;
        this.emailOk = this.validateEmail(this.user.email);
        this.infoMessage = null;
        break;
    
      default:
        this.nameOk = this.validateName(this.user.name);
        break;
    }   
  }

  validateEmail(email: String) {
    if (this.user.email != null && this.user.email.length > 0) {
      let EMAIL_REGEXP = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;
      return EMAIL_REGEXP.test(email.toString()) ? true : false;
    } else {
      return false;
    }
  }

  validateName(name: String) {
    return this.user.name != null && this.user.name.length > 3 ? true : false;
  }

  getUser(email: String) {
    this.emailOk = false;
    this.userService.getUser(email) 
            .subscribe(
                (response: SystemResponse<User>) => this.systemResponseUser = response,
                err => console.log(err),
                () => this.validateUserFound(this.systemResponseUser)
            );
  }

  validateUserFound(response: SystemResponse<User>) {
    if (response != null) {
      if (super.validateResponse(response.status)) {
        this.userFound = response.content == null ? false : true;
        if (!this.userFound) {
          this.infoMessage = "User not found. You need to register.";
        } else {
          this.startDiscussions(response.content);
        }
      }
      super.addMessage(response.message, response.status)
    }
    this.emailOk = this.validateEmail(this.user.email);
    this.nameOk = this.validateName(this.user.name);
  }



}
