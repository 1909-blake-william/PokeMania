import { Injectable } from '@angular/core';
import { User } from '../../models/User';
import { HttpClient, HttpHeaders, HttpClientModule } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FriendsService {

  constructor(
    private http: HttpClient,
    // private userservice: UserService
    ) { }

}
