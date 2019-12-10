import { Component, OnInit } from '@angular/core';
import { TeamfetchService } from '../../services/teamfetch.service';
import { DoBattleService } from '../../services/do-battle.service';
import { Pokemon } from 'src/app/models/Pokemon';
import { BattleTurn } from 'src/app/models/BattleTurn';

@Component({
  selector: 'app-battle',
  templateUrl: './battle.component.html',
  styleUrls: ['./battle.component.css']
})
export class BattleComponent implements OnInit {

  private _team1: Pokemon[]
  private _team2: Pokemon[]
  public pokemon1: Pokemon
  public pokemon2: Pokemon
  public isDataReady: boolean = false
  public opponentDmg: number = 0
  public trainerDmg: number = 0
  private _battleTurns: BattleTurn[]

  constructor(private teamFetcher: TeamfetchService, private battleCalc: DoBattleService) { }

  ngOnInit() {
    this.run()
  }

  public async run() {

    this.teamFetcher.fetchTeam(1).then(team => {

      this._team1 = team
      this.pokemon1 = team[0]

      this.teamFetcher.fetchTeam(2).then(team => {

        this._team2 = team
        this.pokemon2 = team[0]

        this.getBattleResults().then(turns => {

          this._battleTurns = turns
          this.isDataReady = true

        })

      })

    })

  }

  private async getBattleResults() {

    this.battleCalc.team1 = this._team1
    this.battleCalc.team2 = this._team2

    return await this.battleCalc.executeBattle()
    
  }

  private setWinner(): void {

    // this.battleCalc.winner send to db

  }

  private setLoser(): void {

    // this.battleCalc.loser send to db

  }

  private async displayResults() {

    

  }

}
