import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaci } from 'app/shared/model/paci.model';
import { PaciService } from './paci.service';

@Component({
  templateUrl: './paci-delete-dialog.component.html'
})
export class PaciDeleteDialogComponent {
  paci: IPaci;

  constructor(protected paciService: PaciService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.paciService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'paciListModification',
        content: 'Deleted an paci'
      });
      this.activeModal.dismiss(true);
    });
  }
}
