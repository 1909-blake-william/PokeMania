import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { UserService } from '../login/services/user.service';
import { FriendsComponent } from './components/friends/friends.component';



@NgModule({
  providers: [HttpClient, UserService],
  declarations: [FriendsComponent],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule
  ],
  exports: []
})
export class FriendsModule { }
