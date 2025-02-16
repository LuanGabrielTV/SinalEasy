import { Injectable } from '@angular/core';
import { Signal } from '../domain/Signal';
import { AddressService } from './address.service';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { CityService } from './city.service';
import { tap, catchError, of, throwError, concatMap, switchMap, concat, filter, iif, defaultIfEmpty, map } from 'rxjs';
import { City } from '../domain/City';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class SignalService {

  // url = 'https://backend-sinaleasy-xdau.onrender.com/api/';
  url = 'http://localhost:8080/api/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200/" })
  };


  constructor(private addressService: AddressService, private cityService: CityService, private httpClient: HttpClient, private userService: UserService) {
  }

  createSignal(signal: Signal) {
    let options = this.httpOptions;
    options.headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200/", 'Authorization': 'Bearer ' + this.userService.getToken() });
    let url = this.url + 'signals/';
    return this.cityService.getCityById(signal.cityId!)
      .pipe(
        switchMap((city) => city ? of(city) : this.createCity(signal)), concatMap(_ => this.httpClient.post<Signal>(url, JSON.stringify(signal), options)));
  }

  private createCity(signal: Signal) {
    return this.addressService.getCityById(signal.cityId!).pipe((switchMap(res => {
      let city: City = new City();
      city.cityId = String((res['id' as keyof Object] as Object) as number);
      city.name = (res['nome' as keyof Object] as Object) as string;
      city.state = (res['microrregiao' as keyof Object]['mesorregiao' as keyof Object]["UF" as keyof Object]["sigla" as keyof Object] as Object) as string;
      city.rating = 0;
      city.signals = [];
      return this.cityService.createCity(city, this.userService.getToken());
    })))
  }

  updateSignal(signal: Signal) {
    let options = this.httpOptions;
    options.headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200/", 'Authorization': 'Bearer ' + this.userService.getToken() });
    let url = this.url + 'signals/' + signal.signalId;
    return this.cityService.getCityById(signal.cityId!)
      .pipe(
        switchMap((city) => city ? of(city) : this.createCity(signal)), concatMap(city => this.httpClient.put<Signal>(url, JSON.stringify(signal), options)));
  }

  getSignalsByCity(cityId: string) {
    let url = this.url + 'signals/city/' + cityId;
    let options = this.httpOptions;
    if (this.userService.getToken() != null) {
      url += "/auth";
      options.headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200/", 'Authorization': 'Bearer ' + this.userService.getToken() });
    }
    return this.httpClient.get(url, options);
  }

  getSignalById(signalId: string) {
    let url = this.url + 'signals/' + signalId;
    return this.httpClient.get<Signal>(url, this.httpOptions);
  }

  voteOnSignal(changedVotes: Array<string>) {
    let options = this.httpOptions;
    options.headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200/", 'Authorization': 'Bearer ' + this.userService.getToken() });
    let url = this.url + 'signals/vote';
    return this.httpClient.post<Signal>(url, JSON.stringify(changedVotes), options).subscribe();
  }
}
