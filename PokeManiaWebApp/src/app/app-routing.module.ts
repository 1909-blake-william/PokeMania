import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/components/login/login.component';
import { LoginModule } from './login/login.module';
import { RegisterModule } from './register/register.module'
import { RegisterComponent } from './register/components/register/register.component'
import { BattleComponent } from './battle/components/battle/battle.component';
import { BattleModule } from './battle/battle.module';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
<<<<<<< HEAD
    path: 'poke',
    component: PokemonComponentComponent
=======
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'battle',
    component: BattleComponent
>>>>>>> 6f059411fa6100c04581fea501f5c9c3a7c22ed4
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    LoginModule,
    RegisterModule,
    BattleModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
