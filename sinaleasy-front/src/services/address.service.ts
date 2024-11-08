import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

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

  getUFs(){
    let url = this.urlIBGE + 'estados';
    return this.httpClient.get<[]>(url);
  }

}
