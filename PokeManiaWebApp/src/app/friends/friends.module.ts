import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FriendsComponent } from './components/friends/friends.component';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [FriendsComponent],
  imports: [
    CommonModule,
    RouterModule
  ],
  bootstrap: [FriendsComponent]
})
export class FriendsModule { }
