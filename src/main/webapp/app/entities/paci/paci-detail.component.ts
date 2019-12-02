import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaci } from 'app/shared/model/paci.model';

@Component({
  selector: 'jhi-paci-detail',
  templateUrl: './paci-detail.component.html'
})
export class PaciDetailComponent implements OnInit {
  paci: IPaci;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paci }) => {
      this.paci = paci;
    });
  }

  previousState() {
    window.history.back();
  }
}
