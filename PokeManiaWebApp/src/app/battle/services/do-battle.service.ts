import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BattleTurn } from 'src/app/models/BattleTurn';
import { Pokemon } from 'src/app/models/Pokemon';

@Injectable({
  providedIn: 'root'
})
export class DoBattleService {

  private _results: BattleTurn[] = []
  private _team1: Pokemon[] = []
  private _team2: Pokemon[] = []
  private _winner: number = null
  private _loser: number = null

  constructor(http: HttpClient) { }

  set team1(team: Pokemon[]) { this._team1 = team.reverse() }
  set team2(team: Pokemon[]) { this._team2 = team.reverse() }
  get winner() { return this._winner }
  get loser() { return this._loser }

  public async executeBattle(): Promise<BattleTurn[]> {

    let pokemon1: Pokemon = null
    let pokemon2: Pokemon = null

    //While there are pokemon who can fight
    while(this._team1.length > 0 && this._team2.length > 0) {

      //Get the next pokemon
      if(pokemon1 == null) pokemon1 = this._team1.pop()
      if(pokemon2 == null) pokemon2 = this._team2.pop()

      //While both fighting pokemon haven't fainted
      while(pokemon1.hp > 0 && pokemon2.hp > 0) {

        

      }

    }

    return this._results

  }

}
