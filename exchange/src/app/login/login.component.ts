import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../service/authenticate/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userName = 'user@test.com'
  password = 'pass'
  errorMessage='Invalid User'
  invalidLogin=false                                                  
  constructor(private router: Router, private authentication:AuthenticationService) {

  }

  ngOnInit(): void {
  }

  handleLogin(): void {
    this.authentication.authentication(this.userName,this.password).subscribe(
      user=>{
        console.log(user)
        this.invalidLogin=false
        let name = user.firstName +' '+ user.lastName
        this.router.navigate(['welcome',name])  
      },
      error => {
        console.log(error)
        this.invalidLogin = true
      }
    )
  }

}
