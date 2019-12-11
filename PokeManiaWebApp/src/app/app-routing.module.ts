import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/components/login/login.component';
import { LoginModule } from './login/login.module';
import { BattleComponent } from './battle/components/battle/battle.component';
import { BattleModule } from './battle/battle.module';
import { PokemonModule } from './pokemon/pokemon.module';
import { PokemonComponentComponent } from './pokemon/components/pokemon-component/pokemon-component.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { RegisterComponent } from './login/components/register/register.component';


const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'login',
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
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    LoginModule,
    BattleModule,
    PokemonModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
