import { Http, Headers, Response, RequestMethod }  from '@angular/http';

import { SystemResponse } from '../model/system-response';

export class BaseService<T, ID> {

    constructor(protected http: Http) {
        this.http = http;
     }

    get(url: String) {
        return this.http
                .get(url.toString(), { headers: this.getHeaders(RequestMethod.Get) })
                .map(response => <SystemResponse<T>>response.json())
                .catch(this.handleError);
    }

    getById(id: ID, url: String) {
        return this.http
                .get(url.toString(), { 
                        headers: this.getHeaders(RequestMethod.Get), 
                        body: JSON.stringify(id), })
                .map(response => <SystemResponse<T>>response.json())
                .catch(this.handleError);
    }

    list(url: String) {
        return this.http
                .get(url.toString(), { headers: this.getHeaders(RequestMethod.Get) })
                .map(response => <SystemResponse<T[]>>response.json())
                .catch(this.handleError);
    }

    saveOrUpdate(value: T, url: String) {
        return this.http
               .post(
                      url.toString(), 
                      JSON.stringify(value),
                      { headers: this.getHeaders(RequestMethod.Post) }
                    )
               .map(response => <SystemResponse<T>>response.json())
               .catch(this.handleError);
    }

    delete(id: ID, url: String) {
        return this.http
               .post(
                      url.toString(), 
                      JSON.stringify(id),
                      { headers: this.getHeaders(RequestMethod.Post) }
                    )
               .map(response => <SystemResponse<T>>response.json())
               .catch(this.handleError);
    }
    
  
    protected getHeaders(method: RequestMethod): Headers {
        let headers = new Headers();
        
        if (method == RequestMethod.Get) {
            headers.append('Accept', 'application/json');
        } else if (method == RequestMethod.Post) {
            headers.append('Content-Type', 'application/json');
        }
        
        return headers;
    }

    protected handleError(error: any): Promise<any> {
        console.log('An error occurred', error);
        return Promise.reject(error.message || error);
    }

}