import { Component, OnInit } from '@angular/core';
import { ConvertService } from 'src/app/service/exchange/convert.service';
import { CurrencyConversion } from 'src/app/modules/currency-conversion';
import { Router } from '@angular/router';

@Component({
  selector: 'app-conversionvalues',
  templateUrl: './conversionvalues.component.html',
  styleUrls: ['./conversionvalues.component.css']
})
export class ConversionvaluesComponent implements OnInit {

  errorMessage='Unable to process your request at this moment, pelase try again later!!'

  exchangeData : CurrencyConversion[]
  processStatus=false
  errorMessageStatus=false
  constructor(
    private exchange: ConvertService,
    private router : Router
  ) { }

  ngOnInit(): void {
    this.exchangeValue();
  }

  exchangeValue() {
    this.exchange.exchangeValue().subscribe(response=>{
      console.log(response.exchangeValues)
      this.exchangeData = response.exchangeValues
      this.processStatus = true;
        this.errorMessageStatus=false;
    },error=>{
      console.log(error)
      this.processStatus = false;
        this.errorMessageStatus=true;
    })
  }

  updateExchangeValue(id) {
    this.router.navigate(['update',id])
  }

  deleteExchangeValue(id) {
    this.exchange.deleteExchangeValue(id).subscribe(
      success=>{
        console.log(success)
        this.exchangeValue();
      }, error=>{
        console.log(error)
        //this.router.navigate(['exchangevalues']) 
      }
    )
  }

  addExchange() {
    this.router.navigate(['update',-1])
  }


}
