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
    console.log(signal)
    this.cityService.getCityById(signal.cityId!).subscribe((res) => {
      if (res == null) {
        this.createCity(signal);
        let url = this.url + 'signs/';
        console.log(JSON.stringify(signal))
        this.httpClient.post<Signal>(url, JSON.stringify(signal), this.httpOptions).subscribe((res)=>{
          console.log(res);
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
        return;
      });
    })
  }


  updateSignal(signal: Signal) {
    // checagem da existencia de cidade
    let url = this.url + 'cities/' + signal.cityId;
  }
}
