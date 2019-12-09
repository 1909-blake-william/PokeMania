import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from 'src/app/login/services/user.service';
import { User } from 'src/app/models/User';
import { Pokemon } from 'src/app/models/Pokemon';
import { PokemonService } from 'src/app/pokemon/services/pokemon.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-box',
  templateUrl: './box.component.html',
  styleUrls: ['./box.component.css']
})
export class BoxComponent implements OnInit {

  constructor(private httpClient: HttpClient, private userService: UserService, private pokemonService: PokemonService) { }

  pokemon: Pokemon[] = [];
  team: Pokemon[] = [];
  teamSize = this.pokemonService.teamSize;

  boxSubscription: Subscription;
  teamSubscription: Subscription;

  // When this page is routed to, it gets all pokemon for the user
  // this is done by subscribing to box observable from pokemon.service
  // it also gets the team so it can compare what is in the box to the team
  // and then only display what isn't in the team

  ngOnInit() {
    this.boxSubscription = this.pokemonService.$box.subscribe(pokes => {
      this.pokemon = pokes;
        let i = 0;
      for (let poke of this.team) {
       for (let pokeB of this.pokemon) {
        if(poke.id === pokeB.id) {
         this.pokemon.splice(i,1);
      }
      i++;
    }
    i = 0;
  }
    });

    // check is done in both getting the team and box because they are done
    // asynchronously so either could finish first
    this.teamSubscription = this.pokemonService.$team.subscribe(pokes => {
      this.team = pokes;
       let i = 0;
      for (let poke of this.team) {
       for (let pokeB of this.pokemon) {
        if(poke.id === pokeB.id) {
         this.pokemon.splice(i,1);
      }
      i++;
    }
    i = 0;
  }
  this.teamSubscription.unsubscribe();
  //this.pokemonService.boxStream.next(this.pokemon);
    });
    
 

  }

  toTeam(poke) {
    this.pokemonService.toTeam(poke, this.pokemon);
  }

  // when component is destroyed, we dont want subscription left open
  ngOnDestroy() {
    this.boxSubscription.unsubscribe();
    
  }




}
