import { Injectable } from '@angular/core';
import { Http, Response, RequestMethod }  from '@angular/http';
import { Observable } from 'rxjs';

import { BaseService } from './base.service';

// Import RxJs required methods
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class WeatherService extends BaseService<null, null> {

  private getWeatherInformationURL = 'http://api.openweathermap.org/data/2.5/weather?q';

  constructor(protected http: Http) { 
    super(http);
  }

  getWeatherInformation(city: String, region: String) {
    let url = `${this.getWeatherInformationURL}=${city},${region}&appid=4b90179de79431c1b1814aca1849e141&units=metric`;
    return this.http
                .get(url, { headers: this.getHeaders(RequestMethod.Get) })
                .map(response => response.json())
                .catch(this.handleError);
  }

}
