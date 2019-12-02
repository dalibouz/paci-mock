import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaciContext } from 'app/shared/model/paci-context.model';

@Component({
  selector: 'jhi-paci-context-detail',
  templateUrl: './paci-context-detail.component.html'
})
export class PaciContextDetailComponent implements OnInit {
  paciContext: IPaciContext;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paciContext }) => {
      this.paciContext = paciContext;
    });
  }

  previousState() {
    window.history.back();
  }
}
