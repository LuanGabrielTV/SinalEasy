import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { City } from '../domain/City';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CityService {

  // url = 'https://backend-sinaleasy-xdau.onrender.com/api/cities/';
  url = 'http://localhost:8080/api/cities/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', })
  };


  constructor(private httpClient: HttpClient) { }

  getCityById(id: string) {
    let url = this.url + id;
    return this.httpClient.get<City>(url);
  }

  createCity(city: City, token: string) {
    let options = { headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Authorization': 'Bearer ' + token }) }
    return this.httpClient.post<City>(this.url, JSON.stringify(city), options);
  }


}
