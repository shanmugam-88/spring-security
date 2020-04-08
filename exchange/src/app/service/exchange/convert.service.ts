import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URL, AUTHENTICATED_USER, TOKEN } from './../../app.constants';
import { CurrencyConversion } from 'src/app/modules/currency-conversion';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ConvertService {

  constructor(private http : HttpClient

  ) { }

  convert(from:string, to:string,quantity:number) {
    let url = `${API_URL}`+`/currency-conversion-service/currency-converter-feign/from/${from}/to/${to}/quantity/${quantity}`
    console.log(url)
    return this.http.get<CurrencyConversion>(url);
  }

  exchangeValue() {
    let url = `${API_URL}`+ '/currency-conversion-service/currency-converter-exchange-values-feign';
    console.log(url)
    return this.http.get<any>(url);
  }

  exchangeValueById(id) {
    let url = `${API_URL}`+ `/currency-conversion-service/currency-converter-exchange-value/id/${id}`;
    console.log(url)
    return this.http.get<CurrencyConversion>(url);
  }

  updateExchangeValue(exchangeValue) {
    let url = `${API_URL}`+ `/currency-conversion-service/currency-converter-exchange-value`;
    console.log(url);
    return this.http.put(url,exchangeValue)
  }

  createExchangeValue(exchangeValue) {
    let url = `${API_URL}`+ `/currency-conversion-service/currency-converter-exchange-value`;
    console.log(url);
    return this.http.post(url,exchangeValue)
  }

  deleteExchangeValue(id) {
    let url = `${API_URL}`+ `/currency-conversion-service/currency-converter-exchange-value/id/${id}`;
    console.log(url);
    return this.http.delete(url);
  }
}
