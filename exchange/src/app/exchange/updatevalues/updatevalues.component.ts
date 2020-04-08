import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConvertService } from 'src/app/service/exchange/convert.service';
import { CurrencyConversion } from 'src/app/modules/currency-conversion';

@Component({
  selector: 'app-updatevalues',
  templateUrl: './updatevalues.component.html',
  styleUrls: ['./updatevalues.component.css']
})
export class UpdatevaluesComponent implements OnInit {

  id:number
  exchangeValue : CurrencyConversion
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private exchange: ConvertService
  ) { }

  ngOnInit(): void {
    this.exchangeValue = new CurrencyConversion();
    this.exchangeValue.from='';
    this.exchangeValue.to='';
    this.exchangeValue.conversionMultiple=0;
    this.id = this.route.snapshot.params['id'];     
    if(this.id!=-1) {
      this.refreshValue()
    }
  }

  refreshValue() {
    this.exchange.exchangeValueById(this.id).subscribe(
      success=> {
        console.log(success)
        this.exchangeValue = success
      },
      error=>{
        console.log(error)
      }
    )
  }

  saveExchangeValue() {
    console.log(this.exchangeValue)
    if(this.id == -1) {
        this.exchange.createExchangeValue(this.exchangeValue).subscribe(
          success=>{
            console.log(success)
            this.router.navigate(['exchangevalues'])
            
          }
        )
    } else {      
        this.exchange.updateExchangeValue(this.exchangeValue).subscribe(
          success=>{
            console.log(success)
            this.router.navigate(['exchangevalues'])            
          }
        )
      }
    }
    

}
