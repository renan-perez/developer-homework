import { Injectable } from '@angular/core';
import { Http, Response, RequestMethod }  from '@angular/http';
import { Observable } from 'rxjs';

import { BaseService } from './base.service';

import { Location } from '../model/location';
import { PostId } from '../model/post-id';
import { SystemResponse } from '../model/system-response';

// Import RxJs required methods
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class GeolocationService extends BaseService<Location, Number> {

  private getGeolocationInformationURL = 'http://maps.googleapis.com/maps/api/geocode/json?latlng=';

  constructor(protected http: Http) { 
    super(http);
  }

  getLocation(latitude: String, longitude: String) {
    let url = `${this.getGeolocationInformationURL}${latitude},${longitude}`;
    return this.http
                .get(url.toString(), { headers: this.getHeaders(RequestMethod.Get) })
                .map(response => response.json())
                .catch(this.handleError);
  }

}
