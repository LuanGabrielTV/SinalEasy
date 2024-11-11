import { Injectable } from '@angular/core';
import { Signal } from '../domain/Signal';
import { AddressService } from './address.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SignalService {

  url = 'http://localhost:8080/api/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', })
  };


  constructor(private addressService: AddressService, private httpClient: HttpClient) { }

  createSignal(signal: Signal) {
    // checagem da existencia de cidade
    let url = this.url + 'cities/' + signal.cityId;
  }
}
