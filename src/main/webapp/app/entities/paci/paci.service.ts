import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaci } from 'app/shared/model/paci.model';

type EntityResponseType = HttpResponse<IPaci>;
type EntityArrayResponseType = HttpResponse<IPaci[]>;

@Injectable({ providedIn: 'root' })
export class PaciService {
  public resourceUrl = SERVER_API_URL + 'api/pacis';

  constructor(protected http: HttpClient) {}

  create(paci: IPaci): Observable<EntityResponseType> {
    return this.http.post<IPaci>(this.resourceUrl, paci, { observe: 'response' });
  }

  update(paci: IPaci): Observable<EntityResponseType> {
    return this.http.put<IPaci>(this.resourceUrl, paci, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPaci>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaci[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
