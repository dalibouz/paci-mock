import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaciContext } from 'app/shared/model/paci-context.model';
import { PaciContextService } from './paci-context.service';
import { PaciContextDeleteDialogComponent } from './paci-context-delete-dialog.component';

@Component({
  selector: 'jhi-paci-context',
  templateUrl: './paci-context.component.html'
})
export class PaciContextComponent implements OnInit, OnDestroy {
  paciContexts: IPaciContext[];
  eventSubscriber: Subscription;

  constructor(
    protected paciContextService: PaciContextService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.paciContextService.query().subscribe((res: HttpResponse<IPaciContext[]>) => {
      this.paciContexts = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPaciContexts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaciContext) {
    return item.id;
  }

  registerChangeInPaciContexts() {
    this.eventSubscriber = this.eventManager.subscribe('paciContextListModification', () => this.loadAll());
  }

  delete(paciContext: IPaciContext) {
    const modalRef = this.modalService.open(PaciContextDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paciContext = paciContext;
  }
}
