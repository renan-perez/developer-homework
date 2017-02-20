import { Injectable } from '@angular/core';
import { Http, Response, RequestMethod }  from '@angular/http';
import { Observable } from 'rxjs';

import { BaseService } from './base.service';
import { ServiceUrlUtil } from './service-url-util';

import { User } from '../model/user';
import { SystemResponse } from '../model/system-response';

// Import RxJs required methods
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class UserService extends BaseService<User, String> {

  private saveOrUpdateUserURL = `${ServiceUrlUtil.serverURL}/discussions-core/users/saveOrUpdateUser`;
  private listUsersURL = `${ServiceUrlUtil.serverURL}/discussions-core/users/listUsers`;
  private getUserURL = `${ServiceUrlUtil.serverURL}/discussions-core/users/getUser`;

  constructor(protected http: Http) {
    super(http);
  }

  getUser(email: String) {
    const url = `${this.getUserURL}/${email}/`;
    return super.get(url);
  }

  listUser() {
    return super.list(this.listUsersURL);
  }

  saveOrUpdateUser(user: User) {
    return super.saveOrUpdate(user, this.saveOrUpdateUserURL);
  }

}
