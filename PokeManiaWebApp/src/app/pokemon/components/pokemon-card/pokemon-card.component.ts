import { Component, OnInit } from '@angular/core';
import { Pokemon } from 'src/app/models/Pokemon';
import { HttpClient } from '@angular/common/http';
import { UserService } from 'src/app/login/services/user.service';
import { User } from 'src/app/models/User';

// component for displaying each pokemon, will be used in team and box

@Component({
  selector: 'app-pokemon-card',
  templateUrl: './pokemon-card.component.html',
  styleUrls: ['./pokemon-card.component.css']
})
export class PokemonCardComponent implements OnInit {

  pokemon: Pokemon[] = [];

  constructor(private httpClient: HttpClient, private userService: UserService) { }

  user: User;
  // somehting weird here, with observables.
  // = this.userService.getUser();
  ngOnInit() {
    // user id is hard coded in for now, should use this ${this.user._id} /285
    this.httpClient.get<Pokemon[]>('http://localhost:8080/PokeManiaAPI/api/pokemon', {
      withCredentials: true
    })
      .subscribe(data => {
        this.pokemon = data;
      }, err => {
        console.log(err);
      });

      // for catching pokemon
      // this.httpClient.get<Pokemon[]>('https://pokeapi.co/api/v2/pokemon/${id}', {

  }

}
