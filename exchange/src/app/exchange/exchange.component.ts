import { Component, OnInit } from '@angular/core';
import { ConvertService } from '../service/exchange/convert.service';
import { CurrencyConversion } from '../modules/currency-conversion';

@Component({
  selector: 'app-exchange',
  templateUrl: './exchange.component.html',
  styleUrls: ['./exchange.component.css']
})
export class ExchangeComponent implements OnInit {
  from = ''
  to = ''
  quantity : number
  errorMessage='Unable to process your request at this moment, pelase try again later!!'
  data : CurrencyConversion
  processStatus=false
  errorMessageStatus=false
  constructor(
    private exchange: ConvertService
  ) { }

  ngOnInit(): void {
  }

  convertIt(): void {
    this.exchange.convert(this.from,this.to,this.quantity).subscribe(
      convertedRate=>{
        this.processStatus = true;
        this.data = convertedRate;
        this.errorMessageStatus=false;
      },error => {
        this.processStatus = false;
        this.errorMessageStatus=true;
      }
    )
  }  

}
