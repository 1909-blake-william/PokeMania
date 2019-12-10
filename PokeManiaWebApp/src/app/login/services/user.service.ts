import { Injectable } from '@angular/core';
import { User } from '../../models/User';
import { Observable, of} from 'rxjs';

@Injectable()
export class UserService {

  private user: User;

  constructor() { }

  getUser(): Observable<User> {

    return of(this.user);

  }

  setUser(user: User) {

    this.user = user;

  }

}
