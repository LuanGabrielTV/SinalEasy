import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../domain/User';
import { Auth } from '../domain/Auth';
import { jwtDecode } from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = 'https://backend-sinaleasy-xdau.onrender.com/api/auth/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200/" })
  };

  constructor(private httpClient: HttpClient) { }

  register(user: User) {
    let url = this.url + 'register';
    return this.httpClient.post(url, JSON.stringify(user), this.httpOptions);
  }

  login(auth: Auth) {
    let url = this.url + 'login';
    return this.httpClient.post(url, JSON.stringify(auth), this.httpOptions);
  }

  getToken(){
    return JSON.parse(sessionStorage.getItem('token') as string);
  }

  decode(): any {
    let token = this.getToken();
    if(token!=null){
      return jwtDecode(token);
    }else{
      return null;
    }
  }
}
