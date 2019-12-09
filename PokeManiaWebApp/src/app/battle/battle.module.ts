import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BattleComponent } from './components/battle/battle.component';
import { DoBattleService } from './services/do-battle.service';
import { TeamfetchService } from './services/teamfetch.service';
import { TypeAdvCalculatorService } from './services/type-adv-calculator.service';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [BattleComponent],
  imports: [
    CommonModule,
    RouterModule
  ],
  providers: [DoBattleService, TeamfetchService, TypeAdvCalculatorService]
})
export class BattleModule { }
