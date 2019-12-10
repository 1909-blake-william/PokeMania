import { Component, OnInit } from '@angular/core';
import { FriendsService } from '../../services/friends.service';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/login/services/user.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {

  constructor(private userservice: UserService) { }
  user: User;


  ngOnInit() {
  }

  

}
