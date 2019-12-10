import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from 'src/app/login/services/user.service';
import { User } from 'src/app/models/User';
import { Pokemon } from 'src/app/models/Pokemon';
import { ReplaySubject, Subject } from 'rxjs';

// this service is used to get pokemon from DB for team and box
// also used to send from box and to team
// used to put pokemon from catch component into box

@Injectable({
  providedIn: 'root'
})
export class PokemonService {

  teamSize = 0;
  // used to track number of pokemon in team,
  // if it = 6 then can't move from box into team

  boxStream = new ReplaySubject<Pokemon[]>(1);
  $box = this.boxStream.asObservable();

  teamStream = new ReplaySubject<Pokemon[]>(1);
  $team = this.teamStream.asObservable();

  user: User;
  userSubscription = this.userService.getUser().subscribe(element => {
    this.user = element;
  });

  // something weird here, with observables.
  // = this.userService.getUser();

  constructor(private httpClient: HttpClient, private userService: UserService) {

    // user id is hard coded in for now, should use this ${this.user.id}
    this.httpClient.get<Pokemon[]>('http://localhost:8080/PokeManiaAPI/api/pokemon?userId=285', {
      withCredentials: true
    })
      .subscribe(data => {
        // let allPokemon = data;
        this.boxStream.next(data);
      }, err => {
        console.log(err);
      });


    this.httpClient.get<Pokemon[]>('http://localhost:8080/PokeManiaAPI/api/pokemonteam?userId=285', {
      withCredentials: true
    })
      .subscribe(data => {
        this.teamStream.next(data);
        this.teamSize = data.length;
      }, err => {
        console.log(err);
      });

  }

  // get the current team then add the new pokemon to it then update both streams.
  toTeam(poke, pokeBox) {

    // first get the team then add the new pokemon onto the end
    let pokeTeam: Pokemon[];
    const teamSubscription = this.$team.subscribe(pokes => {
      pokeTeam = pokes;
    });
    pokeTeam.push(poke);
    this.teamStream.next(pokeTeam);
    teamSubscription.unsubscribe();

    // http request to add to team in DB
    this.httpClient.post(`http://localhost:8080/PokeManiaAPI/api/pokemonteam?userId=${poke.id}`, {
      withCredentials: true
    }).subscribe(
      data => {
        console.log('added to team');
      },
      err => {
        console.log(err);
      }
    );



    // remove pokemon going to team
    let i = 0;
    pokeBox.forEach(element => {
      if (element === poke) {
        pokeBox.splice(i, 1);
      }
      i++;
    });
    this.boxStream.next(pokeBox);
  }

  // get box, then add pokemon to it (remove from team) then update both streams.
  toBox(poke, pokeTeam) {
    // first get the box then add the new pokemon onto the end
    let pokeBox: Pokemon[];
    const boxSubscription = this.$box.subscribe(pokes => {
      pokeBox = pokes;
    });
    pokeBox.push(poke);
    this.boxStream.next(pokeBox); // send data to stream so components will update
    boxSubscription.unsubscribe(); // subscription no longer need

    // http request to remove poke from team in DB
    this.httpClient.delete(`http://localhost:8080/PokeManiaAPI/api/pokemonteam?userId=${poke.id}`, {
      withCredentials: true
    }).subscribe(
      data => {
        console.log('removed from team');
      },
      err => {
        console.log(err);
      }
    );



    // remove pokemon going to box
    let i = 0;
    pokeTeam.forEach(element => {
      if (element === poke) {
        pokeTeam.splice(i, 1);
      }
      i++;
    });
    this.teamStream.next(pokeTeam);

  }

  catchPoke(poke) {

    const dexId: number = Math.ceil(Math.random() * 810); // get random poke 1-810
    
  }

  release(id) {
    let pokeBox: Pokemon[];
    const boxSubscription = this.$box.subscribe(pokes => {
      pokeBox = pokes;
    });
    boxSubscription.unsubscribe();
    this.httpClient.delete<Pokemon>(`http://localhost:8080/PokeManiaAPI/api/pokemon?pokemonId=${id}`, {
      withCredentials: true,
    })
      .subscribe(data => {
        let i = 0;
        pokeBox.forEach(element => {
          if (element.id === id) {
            pokeBox.splice(i, 1);
          }
          i++;
        });
        this.boxStream.next(pokeBox);
      }, err => {
        console.log(err);
      });
  }

}
