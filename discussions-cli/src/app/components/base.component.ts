import { Headers, RequestMethod }  from '@angular/http';
import { Observable } from 'rxjs';

import { HttpStatus } from '../model/http-status';

export class BaseComponent {


    validateResponse(status: String) {
        return status == 'OK' ? true : false;
    }

    addMessage(message: String, status: String) {
        if (this.validateResponse(status)) {
            this.addInfoMessage(message);
        } else {
            this.addErrorMessage(message);
        }
    }

    addErrorMessage(message: String) {
        if (message != null) {

        }
    }

    addInfoMessage(message: String) {
        if (message != null) {

        }
    }

}