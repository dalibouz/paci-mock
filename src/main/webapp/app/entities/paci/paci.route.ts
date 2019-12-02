import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Paci } from 'app/shared/model/paci.model';
import { PaciService } from './paci.service';
import { PaciComponent } from './paci.component';
import { PaciDetailComponent } from './paci-detail.component';
import { PaciUpdateComponent } from './paci-update.component';
import { IPaci } from 'app/shared/model/paci.model';

@Injectable({ providedIn: 'root' })
export class PaciResolve implements Resolve<IPaci> {
  constructor(private service: PaciService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaci> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((paci: HttpResponse<Paci>) => paci.body));
    }
    return of(new Paci());
  }
}

export const paciRoute: Routes = [
  {
    path: '',
    component: PaciComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pacis'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaciDetailComponent,
    resolve: {
      paci: PaciResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pacis'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaciUpdateComponent,
    resolve: {
      paci: PaciResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pacis'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaciUpdateComponent,
    resolve: {
      paci: PaciResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pacis'
    },
    canActivate: [UserRouteAccessService]
  }
];
