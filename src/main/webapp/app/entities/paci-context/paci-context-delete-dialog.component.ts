import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaciContext } from 'app/shared/model/paci-context.model';
import { PaciContextService } from './paci-context.service';

@Component({
  templateUrl: './paci-context-delete-dialog.component.html'
})
export class PaciContextDeleteDialogComponent {
  paciContext: IPaciContext;

  constructor(
    protected paciContextService: PaciContextService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.paciContextService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'paciContextListModification',
        content: 'Deleted an paciContext'
      });
      this.activeModal.dismiss(true);
    });
  }
}
