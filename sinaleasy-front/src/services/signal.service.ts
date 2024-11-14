import { Injectable } from '@angular/core';
import { Signal } from '../domain/Signal';
import { AddressService } from './address.service';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { CityService } from './city.service';
import { tap, catchError, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignalService {

  url = 'http://localhost:8080/api/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', })
  };


  constructor(private addressService: AddressService, private cityService: CityService, private httpClient: HttpClient) { }

  createSignal(signal: Signal) {
    this.cityService.getCityById(signal.cityId!).subscribe((res)=>{
      console.log(res);
    });
  }

  private createCity(error: HttpErrorResponse){

  }

  updateSignal(signal: Signal) {
    // checagem da existencia de cidade
    let url = this.url + 'cities/' + signal.cityId;
  }
}
