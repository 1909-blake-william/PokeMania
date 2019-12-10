import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/components/login/login.component';
import { LoginModule } from './login/login.module';
import { RegisterModule } from './register/register.module';
import { RegisterComponent } from './register/components/register/register.component';
import { BattleComponent } from './battle/components/battle/battle.component';
import { BattleModule } from './battle/battle.module';
import { PokemonModule } from './pokemon/pokemon.module';
import { PokemonComponentComponent } from './pokemon/components/pokemon-component/pokemon-component.component';
import { FriendsComponent } from './friends/components/friends/friends.component';
import { FriendsModule } from './friends/friends.module';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';


const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'battle',
    component: BattleComponent
  },
  {
    path: 'poke',
    component: PokemonComponentComponent
  },
  {
    path: 'friends',
    component: PageNotFoundComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    LoginModule,
    RegisterModule,
    BattleModule,
    PokemonModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
