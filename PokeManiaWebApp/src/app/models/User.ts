//String username, String firstname, String lastname, int id, int badges, int wins, int losses

export class User {

    private _username: string
    private _firstname: string
    private _lastname: string
    private _id: number
    private _badges: number
    private _wins: number
    private _losses: number

    get username(): string { return this._username }
    set username(uname: string) { this._username = uname }

    get firstname(): string { return this._firstname }
    set firstname(fname: string) { this._firstname = fname }

    get lastname(): string { return this._lastname }
    set lastname(lname: string) { this._lastname = lname }

    get id(): number { return this._id }
    set id(id: number) { this._id = id }

    get badges(): number { return this._badges }
    set badges(badges: number) { this._badges = badges }

    get wins(): number { return this._wins }
    set wins(wins: number) { this._wins = wins }

    get losses(): number { return this._losses }
    set losses(losses: number) { this._losses = losses }

}