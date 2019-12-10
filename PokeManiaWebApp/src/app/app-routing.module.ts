import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/components/login/login.component';
import { LoginModule } from './login/login.module';
import { PokemonModule } from './pokemon/pokemon.module';
import { PokemonComponentComponent } from './pokemon/components/pokemon-component/pokemon-component.component';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'poke',
    component: PokemonComponentComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    LoginModule,
    PokemonModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
