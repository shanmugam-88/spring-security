import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { AuthenticationService } from '../authenticate/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class HttpIntercepterAuthService implements HttpInterceptor{

  constructor(
    private Authenticate : AuthenticationService
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    let currentUser = this.Authenticate.getLoggedInUser()
    console.log(currentUser)
    if(currentUser && currentUser.token) {
      request = request.clone({
        setHeaders: {
          Authorization : 'Bearer '+currentUser.token
        }
      })
    }
    return next.handle(request);
  };
}
