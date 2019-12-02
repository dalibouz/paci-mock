import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PaciContext } from 'app/shared/model/paci-context.model';
import { PaciContextService } from './paci-context.service';
import { PaciContextComponent } from './paci-context.component';
import { PaciContextDetailComponent } from './paci-context-detail.component';
import { PaciContextUpdateComponent } from './paci-context-update.component';
import { IPaciContext } from 'app/shared/model/paci-context.model';

@Injectable({ providedIn: 'root' })
export class PaciContextResolve implements Resolve<IPaciContext> {
  constructor(private service: PaciContextService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaciContext> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((paciContext: HttpResponse<PaciContext>) => paciContext.body));
    }
    return of(new PaciContext());
  }
}

export const paciContextRoute: Routes = [
  {
    path: '',
    component: PaciContextComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaciContexts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaciContextDetailComponent,
    resolve: {
      paciContext: PaciContextResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaciContexts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaciContextUpdateComponent,
    resolve: {
      paciContext: PaciContextResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaciContexts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaciContextUpdateComponent,
    resolve: {
      paciContext: PaciContextResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaciContexts'
    },
    canActivate: [UserRouteAccessService]
  }
];
