import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URL, AUTHENTICATED_USER, TOKEN } from './../../app.constants';
import {map} from 'rxjs/operators';
import { User } from 'src/app/modules/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(
    private http:HttpClient
  ) { }

  authentication(username:string, password:string) {
    return this.http.post<any>(
      `${API_URL}/authenticate`,{
        username,
        password
      }).pipe(
        map(
          data => {
            sessionStorage.setItem(`${AUTHENTICATED_USER}`, JSON.stringify(data));
            return data;
          }
        )
      );
  }

  isUserLogggedIn() {
    let user = sessionStorage.getItem(`${AUTHENTICATED_USER}`)
    return !(user===null) 
  }

  public getLoggedInUser() : User {
    return JSON.parse(sessionStorage.getItem(`${AUTHENTICATED_USER}`));
  }

  logout() {
    sessionStorage.removeItem(`${AUTHENTICATED_USER}`)
  }
}
