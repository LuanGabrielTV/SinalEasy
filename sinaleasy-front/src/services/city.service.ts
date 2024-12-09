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
    console.log(url)
    return this.httpClient.get<City>(url);
  }
  
  createCity(city: City){
    console.log(JSON.stringify(city));
    return this.httpClient.post<City>(this.url, JSON.stringify(city), this.httpOptions);
  }


}
