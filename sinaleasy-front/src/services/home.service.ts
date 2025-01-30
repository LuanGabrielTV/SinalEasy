import { Injectable } from '@angular/core';
import { City } from '../domain/City';
import { State } from '../domain/State';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private latestCity: City | undefined;
  private latestState: State | undefined;

  constructor() {
    // this.latestState = new State();
    // this.latestState.id = "31";
    // this.latestState.sigla = "MG";
    // this.latestState.name = "Minas Gerais";

    // this.latestCity = new City();
    // this.latestCity.cityId = "3168705";
    // this.latestCity.name = "Timóteo";
    // this.latestCity.rating = 0;
  }

  setLatestCity(city: City){
    this.latestCity = city;
  }

  setLatestState(state: State){
    this.latestState = state;
  }

  getLatestCity(){
    return this.latestCity;
  }

  getLatestState(){
    return this.latestState;
  }
}
