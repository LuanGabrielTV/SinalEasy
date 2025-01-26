import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../domain/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = 'http://localhost:8080/auth/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200/" })
  };

  constructor(private httpClient: HttpClient) { }

  register(user: User) {
    let url = this.url + 'register';
    return this.httpClient.post(url, JSON.stringify(user), this.httpOptions);
  }
}
