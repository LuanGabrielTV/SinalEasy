import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { City } from '../domain/City';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  key: string;
  urlGeo = 'https://geocode.maps.co/';
  urlIBGE = 'https://servicodados.ibge.gov.br/api/v1/localidades/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', })
  };

  constructor(private httpClient: HttpClient) {
    this.key = '6728f77b2972b120799548croaf5401';
  }

  getStates(){
    let url = this.urlIBGE + 'estados';
    return this.httpClient.get<[]>(url);
  }

  getCitiesByState(state: string){
    let url = this.urlIBGE + 'estados/' + state + '/municipios/';
    return this.httpClient.get<[]>(url);
  }

  getCityById(id:number){
    let url = this.urlIBGE + 'municipios/' + id;
    return this.httpClient.get(url);
  }

  getCoordinates(address: string) {
    let url = this.urlGeo + 'search?q='+ address +'&api_key='+this.key;
    return this.httpClient.get<[]>(url);
  }

  getAddress(lat: number, long: number) {
    let url = this.urlGeo + 'reverse?lat=' + lat + '&lon=' + long + '&api_key=' + this.key;
    return this.httpClient.get(url);
  }


}
