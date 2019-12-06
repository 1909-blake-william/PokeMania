import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/User'
import { Pokemon } from '../../../models/Pokemon'
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public username: string
  public password1: string
  public password2: string
  public firstname: string
  public lastname: string
  public pokemon: number

  public usernameError: boolean = false
  public passwordError: boolean = false
  public password2Error: boolean = false
  public pokemonError: boolean = false

  public showError: boolean = false
  public errorMsg: string = ''

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  async submit() {

    let user: User
    let pokemon: Pokemon

    if(!this.verify()) return

    user = new User(this.username, this.firstname, this.lastname, 0, 0, 0, 0)
    pokemon = await this.getPokemon()

    //send new user data to API
    //send pokemon to API

  }

  private async getPokemon(): Promise<Pokemon> {
    
    let pokemon: Pokemon
    let response: any //Has to be for the pokemon constructor
    let type1: string
    let type2: string

    try {
      
      response = await this.http.get(`https://pokeapi.co/api/v2/pokemon/${this.pokemon}`).toPromise()

    } catch(err) {

      console.error(err)
      return

    }

    if(response.types[0].slot == 1) {
    
      type1 = response.types[0].type.name
      type1 = type1.charAt(0).toUpperCase() + type1.substring(1)

    }
      
    else {

      type1 = response.types[1].type.name
      type2 = response.types[0].type.name

      type1 = type1.charAt(0).toUpperCase() + type1.substring(1)
      type2 = type2.charAt(0).toUpperCase() + type2.substring(1)

    }

    pokemon = new Pokemon(0, 0, response.id, 1, response.stats[5].base_stat, response.stats[4].base_stat,
                          response.stats[3].base_stat, response.stats[0].base_stat, type1, type2, 
                          response.sprites['front_default'], response.sprites['back_default'])

    return pokemon

  }

  verify(): boolean {

    let allGood: boolean = true

    this.clearErrors()

    if(!this.username) {
      
      this.usernameError = true
      allGood = false

    }
    if(!this.password1 || !this.goodPassword()) {

      this.passwordError = true
      allGood = false

    }

    if(!this.password2 || !(this.password1 === this.password2)) {

      this.password2Error = true
      allGood = false

    }

    if(!this.pokemon) {

      this.pokemonError = true
      allGood = false

    }

    return allGood

  }

  clearErrors(): void {

    this.usernameError = false
    this.passwordError = false
    this.password2Error = false
    this.pokemonError = false

  }

  goodPassword(): boolean {

    let lowercasePattern  = new RegExp('(?=.*[a-z])')
    let uppercasePattern  = new RegExp('(?=.*[A-Z])')
    let numPattern        = new RegExp('(?=.*[0-9])')
    let lengthPattern     = new RegExp('(?=.{8,})') 

    return lowercasePattern.test(this.password1) && uppercasePattern.test(this.password1) && numPattern.test(this.password1) && lengthPattern.test(this.password1)

  }

  showUsernameError(): void {

    if(this.usernameError) {

      this.showError = true
      this.errorMsg = 'Username cannot be blank'

    }

  }

  showpasswordError(): void {

    if(this.passwordError) {

      this.showError = true
      this.errorMsg = 'Password must have at least 1 lowercase, 1 uppercase, 1 number, and 8 characters in total'

    }

  }

  showpassword2Error(): void {

    if(this.password2Error) {

      this.showError = true
      this.errorMsg = 'Password does not match'

    }

  }

  showPokemonError(): void {

    if(this.pokemonError) {

      this.showError = true
      this.errorMsg = 'A starter pokemon must be picked'

    }

  }

  clearError(): void {

    this.showError = false
    this.errorMsg = ''

  }

}
