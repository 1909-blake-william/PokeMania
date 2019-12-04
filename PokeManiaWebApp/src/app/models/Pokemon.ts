//Pokemon model that matches the Java model from the server which holds relevent data about a pokemon
export class Pokemon {

    private _id: number
    private _trainerID: number
    private _dexNum: number
    private _level: number
    private _hp: number
    private _att: number
    private _def: number
    private _spd: number
    private _type1: string
    private _type2: string
    private _frontImg: string
    private _backImg: string

    constructor(id: number, trainerID: number, dexNum: number, level: number, hp: number, att: number, def: number, spd: number, type1: string, type2: string, frontImg: string, backImg: string) {

        this._id = id
        this._trainerID = trainerID
        this._dexNum = dexNum
        this._level = level
        this._hp = hp
        this._att = att
        this._def = def
        this._spd = spd
        this._type1 = type1
        this._type2 = type2
        this._frontImg = frontImg
        this._backImg = backImg

    }

    get id(): number { return this._id }
    set id(id: number) { this._id = id }

    get trainerID(): number { return this._trainerID }
    set trainerID(tid: number) { this._trainerID = tid }

    get dexNum(): number { return this._dexNum }
    set dexNum(dex: number) { this._dexNum = dex }

    get level(): number { return this._level }
    set level(level: number) { this._level = level }

    get hp(): number { return this._hp }
    set hp(hp: number) { this._hp = hp }

    get att(): number { return this._att }
    set att(att: number) { this._att = att }

    get def(): number { return this._def }
    set def(def: number) { this._def = def }

    get speed(): number { return this.speed }
    set speed(spd: number) { this._spd = spd }

    get type1(): string { return this._type1 }
    set type1(type: string) { this._type1 = type }

    get type2(): string { return this._type2 }
    set type2(type: string) { this._type2 = type }

    get frontImg(): string { return this._frontImg }
    set frontImg(img: string) { this._frontImg = this.frontImg }

    get backImg(): string { return this._backImg }
    set backImg(img: string) { this._backImg = img }

}