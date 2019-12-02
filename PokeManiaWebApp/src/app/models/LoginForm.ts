//LoginForm model that matches the Java model serverside which holds the login creds of a user
export class LoginForm {

    private _username: string
    private _password: string

    constructor(username: string, password: string) {

        this._username = username
        this._password = password

    }

    get username(): string { return this._username }
    set username(username: string) { this._username = username }

    get password(): string { return this._password }
    set password(password: string) { this._password = password }

}