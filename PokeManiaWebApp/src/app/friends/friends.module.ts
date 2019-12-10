import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FriendsComponent } from './components/friends/friends.component';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../login/services/user.service';
import { HttpRequest } from '@angular/common/http';


@NgModule({
  providers: [UserService, HttpRequest],
  declarations: [FriendsComponent],
  imports: [
    CommonModule,
    RouterModule,
    NgbModule
  ],
  bootstrap: [FriendsComponent]
})
export class FriendsModule { }
