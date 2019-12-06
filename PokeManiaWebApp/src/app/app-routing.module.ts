import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'
import { LoginComponent } from './login/components/login/login.component';
import { LoginModule } from './login/login.module';
import { RegisterModule } from './register/register.module'
import { RegisterComponent } from './register/components/register/register.component'

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    LoginModule,
    RegisterModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
