import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';

import {Observable} from 'rxjs';

@Injectable()
export class HeaderInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(req: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {

    const item = localStorage.getItem('token');
    const decodedItem = JSON.parse(item);

    if (item) {
      const cloned = req.clone({
        headers: req.headers.set('X-Auth-Token', decodedItem)
      });
      return next.handle(cloned);
    } else {
      return next.handle(req);
    }
    
  }


}
