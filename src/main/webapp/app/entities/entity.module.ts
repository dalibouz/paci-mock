import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'paci',
        loadChildren: () => import('./paci/paci.module').then(m => m.PaciMockPaciModule)
      },
      {
        path: 'paci-context',
        loadChildren: () => import('./paci-context/paci-context.module').then(m => m.PaciMockPaciContextModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PaciMockEntityModule {}
