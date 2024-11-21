import { Injectable } from '@angular/core';
import { City } from '../domain/City';
import { State } from '../domain/State';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private latestCity: City | undefined;
  private latestState: State | undefined;

  constructor() {}

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
