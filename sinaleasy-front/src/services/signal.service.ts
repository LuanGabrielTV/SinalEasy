import { Injectable } from '@angular/core';
import { Signal } from '../domain/Signal';
import { AddressService } from './address.service';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { CityService } from './city.service';
import { tap, catchError, of, throwError, concatMap, switchMap, concat, filter, iif, defaultIfEmpty, map } from 'rxjs';
import { City } from '../domain/City';

@Injectable({
  providedIn: 'root'
})
export class SignalService {

  // url = 'https://backend-sinaleasy-xdau.onrender.com/api/';
  url = 'http://localhost:8080/api/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200/" })
  };


  constructor(private addressService: AddressService, private cityService: CityService, private httpClient: HttpClient) { }

  createSignal(signal: Signal) {
    let url = this.url + 'signals/';
    return this.cityService.getCityById(signal.cityId!)
      .pipe(
        switchMap((city) => city ? of(city) : this.createCity(signal)), concatMap(_ => this.httpClient.post<Signal>(url, JSON.stringify(signal), this.httpOptions)));
  }

  private createCity(signal: Signal) {
    return this.addressService.getCityById(signal.cityId!).pipe((switchMap(res => {
      let city: City = new City();
      city.cityId = String((res['id' as keyof Object] as Object) as number);
      city.name = (res['nome' as keyof Object] as Object) as string;
      city.state = (res['microrregiao' as keyof Object]['mesorregiao' as keyof Object]["UF" as keyof Object]["sigla" as keyof Object] as Object) as string;
      city.rating = 0;
      city.signals = [];
      return this.cityService.createCity(city);
    })))
  }
  
  updateSignal(signal: Signal) {
    console.log(JSON.stringify(signal))
    let url = this.url + 'signals/' + signal.signalId;
    return this.cityService.getCityById(signal.cityId!)
      .pipe(
        switchMap((city) => city ? of(city) : this.createCity(signal)), concatMap(city => this.httpClient.put<Signal>(url, JSON.stringify(signal), this.httpOptions)));
  }

  getSignalsByCity(cityId: string) {
    let url = this.url + 'signals/city/' + cityId;
    return this.httpClient.get(url, this.httpOptions);
  }

  getSignalById(signalId: string) {
    let url = this.url + 'signals/' + signalId;
    return this.httpClient.get<Signal>(url, this.httpOptions);
  }

  voteOnSignal(changedVotes: Array<string>){
    let url = this.url + 'signals/vote';
    console.log(url)
    console.log(JSON.stringify(changedVotes))
    return this.httpClient.post<Signal>(url, JSON.stringify(changedVotes), this.httpOptions).subscribe();
  }
}
