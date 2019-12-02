import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaci } from 'app/shared/model/paci.model';
import { PaciService } from './paci.service';
import { PaciDeleteDialogComponent } from './paci-delete-dialog.component';

@Component({
  selector: 'jhi-paci',
  templateUrl: './paci.component.html'
})
export class PaciComponent implements OnInit, OnDestroy {
  pacis: IPaci[];
  eventSubscriber: Subscription;

  constructor(protected paciService: PaciService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.paciService.query().subscribe((res: HttpResponse<IPaci[]>) => {
      this.pacis = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPacis();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaci) {
    return item.id;
  }

  registerChangeInPacis() {
    this.eventSubscriber = this.eventManager.subscribe('paciListModification', () => this.loadAll());
  }

  delete(paci: IPaci) {
    const modalRef = this.modalService.open(PaciDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paci = paci;
  }
}
