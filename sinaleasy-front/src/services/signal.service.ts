import { Injectable } from '@angular/core';
import { Signal } from '../domain/Signal';
import { AddressService } from './address.service';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { CityService } from './city.service';
import { tap, catchError, of, throwError } from 'rxjs';
import { City } from '../domain/City';

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
    this.cityService.getCityById(signal.cityId!).subscribe(async (res) => {
      if (res == null) {
        await this.createCity(signal);
      }else{
        let url = this.url + 'signs/';
        this.httpClient.post<Signal>(url, JSON.stringify(signal), this.httpOptions).subscribe((res) => {
        });
      }

    });
  }

  private createCity(signal: Signal) {
    this.addressService.getCityById(signal.cityId!).subscribe((res) => {
      let city: City = new City();
      city.cityId = String((res['id' as keyof Object] as Object) as number);
      city.name = (res['nome' as keyof Object] as Object) as string;
      city.state = (res['microrregiao' as keyof Object]['mesorregiao' as keyof Object]["UF" as keyof Object]["sigla" as keyof Object] as Object) as string;
      city.rating = 0;
      city.signals = [];
      this.cityService.createCity(city).subscribe((res) => {
        let url = this.url + 'signs/';
        this.httpClient.post<Signal>(url, JSON.stringify(signal), this.httpOptions).subscribe((res) => {
        });
      });
    })
  }


  updateSignal(signal: Signal) {
    // checagem da existencia de cidade
    let url = this.url + 'cities/' + signal.cityId;
  }
}
