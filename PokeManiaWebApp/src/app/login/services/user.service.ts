import { Injectable } from '@angular/core';
import { User } from '../../models/User';
import { Observable, of} from 'rxjs';

@Injectable()
export class UserService {

  private _user: User = null

  constructor() { }

  getUser(): Observable<User> {

    return of(this._user);

  }

  setUser(user: User) {

    this._user = user;

  }

}
