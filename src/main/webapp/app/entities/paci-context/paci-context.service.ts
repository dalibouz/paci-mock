import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaciContext } from 'app/shared/model/paci-context.model';

type EntityResponseType = HttpResponse<IPaciContext>;
type EntityArrayResponseType = HttpResponse<IPaciContext[]>;

@Injectable({ providedIn: 'root' })
export class PaciContextService {
  public resourceUrl = SERVER_API_URL + 'api/paci-contexts';

  constructor(protected http: HttpClient) {}

  create(paciContext: IPaciContext): Observable<EntityResponseType> {
    return this.http.post<IPaciContext>(this.resourceUrl, paciContext, { observe: 'response' });
  }

  update(paciContext: IPaciContext): Observable<EntityResponseType> {
    return this.http.put<IPaciContext>(this.resourceUrl, paciContext, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPaciContext>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaciContext[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
