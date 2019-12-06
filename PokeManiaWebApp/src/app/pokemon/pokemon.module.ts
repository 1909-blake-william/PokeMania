import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { UserService } from '../login/services/user.service';
import { BoxComponent } from './components/box/box.component';
import { PokemonCardComponent } from './components/pokemon-card/pokemon-card.component';
import { PokemonComponentComponent } from './components/pokemon-component/pokemon-component.component';
import { TeamComponent } from './components/team/team.component';



@NgModule({
  declarations: [BoxComponent, PokemonCardComponent, PokemonComponentComponent, TeamComponent],
  providers: [UserService, HttpClient],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule
  ],
  exports: [PokemonCardComponent]
})
export class PokemonModule { }
