import { Injectable } from '@angular/core';
import { Http, Response, RequestMethod }  from '@angular/http';
import { Observable } from 'rxjs';

import { BaseService } from './base.service';

// Import RxJs required methods
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class WeatherService extends BaseService<null, null> {

  private getWeatherInformationURL = 'https://api.apixu.com/v1/current.json?key=b6d753b662d740c4b56220324172102&q=';

  constructor(protected http: Http) { 
    super(http);
  }

  getWeatherInformation(city: String) {
    let url = `${this.getWeatherInformationURL}=${city}`;
    return this.http
                .get(url, { headers: this.getHeaders(RequestMethod.Get) })
                .map(response => response.json())
                .catch(this.handleError);
  }

}
