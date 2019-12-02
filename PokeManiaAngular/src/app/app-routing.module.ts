import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [
  // {
  //   path: 'login',

  //   // When we go to the /login, load up the Login Module lazily
  //   loadChildren: () => import('./login/login.module').then(mod => mod.LoginModule)
  // },
  // {
  //   path: 'home',
  //   loadChildren: () => import('./home/home.module').then(mod => mod.HomeModule)
  // },
  {
    path: '**',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
