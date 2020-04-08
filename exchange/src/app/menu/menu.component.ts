import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../service/authenticate/authentication.service';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor( public authentication: AuthenticationService) { }

  ngOnInit(): void {
  }

}
