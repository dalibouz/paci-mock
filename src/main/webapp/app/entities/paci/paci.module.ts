import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaciMockSharedModule } from 'app/shared/shared.module';
import { PaciComponent } from './paci.component';
import { PaciDetailComponent } from './paci-detail.component';
import { PaciUpdateComponent } from './paci-update.component';
import { PaciDeleteDialogComponent } from './paci-delete-dialog.component';
import { paciRoute } from './paci.route';

@NgModule({
  imports: [PaciMockSharedModule, RouterModule.forChild(paciRoute)],
  declarations: [PaciComponent, PaciDetailComponent, PaciUpdateComponent, PaciDeleteDialogComponent],
  entryComponents: [PaciDeleteDialogComponent]
})
export class PaciMockPaciModule {}
