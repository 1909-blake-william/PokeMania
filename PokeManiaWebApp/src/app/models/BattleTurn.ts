export class BattleTurn {

    private _attacker: number
    private _defender: number
    private _damage: number

    constructor(attacker: number, defender: number, damage: number) {

        this._attacker = attacker
        this._defender = defender
        this._damage = damage

    }

    get attacker(): number { return this._attacker }
    get defender(): number { return this._defender }
    get damage(): number { return this._damage }

}