import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaciMockSharedModule } from 'app/shared/shared.module';
import { PaciContextComponent } from './paci-context.component';
import { PaciContextDetailComponent } from './paci-context-detail.component';
import { PaciContextUpdateComponent } from './paci-context-update.component';
import { PaciContextDeleteDialogComponent } from './paci-context-delete-dialog.component';
import { paciContextRoute } from './paci-context.route';

@NgModule({
  imports: [PaciMockSharedModule, RouterModule.forChild(paciContextRoute)],
  declarations: [PaciContextComponent, PaciContextDetailComponent, PaciContextUpdateComponent, PaciContextDeleteDialogComponent],
  entryComponents: [PaciContextDeleteDialogComponent]
})
export class PaciMockPaciContextModule {}
