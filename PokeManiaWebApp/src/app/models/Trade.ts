//Trade model that matches the serverside Java model which holds relevent data about a trade offer
export class Trade {

    private _id: number
    private _trainerID1: number
    private _trainerID2: number
    private _pokemonID1: number
    private _pokemonID2: number
    private _status: string

    constructor(id: number, trainerID1: number, trainerID2: number, pokemonID1: number, pokemonID2: number) {

        this._id = id
        this._trainerID1 = trainerID1
        this._trainerID2 = trainerID2
        this._pokemonID1 = pokemonID1
        this._pokemonID2 = pokemonID2
        this._status = status

    }

    get id(): number { return this._id }
    set id(id: number) { this._id = id }

    get trainerID1(): number { return this._trainerID1 }
    set trainerID1(trainerID: number) { this._trainerID1 = trainerID }

    get trainerID2(): number { return this._trainerID2 }
    set trainerID2(trainerID: number) { this._trainerID2 = trainerID }

    get pokemonID1(): number { return this._pokemonID1 }
    set pokemonID1(pokemonID: number) { this._pokemonID1 = pokemonID }

    get pokemonID2(): number { return this._pokemonID2 }
    set pokemonID2(pokemonID: number) { this._pokemonID2 = pokemonID }

    get status(): string { return this._status }
    set status(status: string) { this._status = status }

}