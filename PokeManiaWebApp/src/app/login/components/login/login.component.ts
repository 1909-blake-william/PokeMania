import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../../models/User'
import { HttpClient } from '@angular/common/http'
import { LoginForm } from 'src/app/models/LoginForm';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username: string = ''
  public password: string = ''
  public errorExists: boolean = false
  public errorMsg: string = ''

  constructor(private userService: UserService, private http: HttpClient) { }

  ngOnInit() {
  }

  login() {

    let user: User

    if(!this.checkErrors()) return

    user = this.getUser()

    if(user !== null) {
    
      this.userService.setUser(user);
      //Route to next component

    } else this.displayError('Invalid login')

  }

  getUser(): User {

    let loginInfo: LoginForm = new LoginForm(this.username, this.password)
    const response = this.http.post('url', JSON.stringify(loginInfo))

    //Get data from response and put into a User obj and return
    return null

  }

  checkErrors() {

    if(this.username === '') {

      this.displayError('Username must be provided!')
      return false

    }

    if(this.password === '') {

      this.displayError('Password must be provided!')
      return false

    }

    return true

  }

  displayError(msg: string) {

    this.errorExists = true
    this.errorMsg = msg

    setTimeout(() => {

      this.errorExists = false
      this.errorMsg = ''

    }, 3000);

  }

}
