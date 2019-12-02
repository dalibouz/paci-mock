import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPaciContext, PaciContext } from 'app/shared/model/paci-context.model';
import { PaciContextService } from './paci-context.service';

@Component({
  selector: 'jhi-paci-context-update',
  templateUrl: './paci-context-update.component.html'
})
export class PaciContextUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    paciDispatcher: [],
    paciEngine: [],
    paciRedirect: []
  });

  constructor(protected paciContextService: PaciContextService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paciContext }) => {
      this.updateForm(paciContext);
    });
  }

  updateForm(paciContext: IPaciContext) {
    this.editForm.patchValue({
      id: paciContext.id,
      paciDispatcher: paciContext.paciDispatcher,
      paciEngine: paciContext.paciEngine,
      paciRedirect: paciContext.paciRedirect
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paciContext = this.createFromForm();
    if (paciContext.id !== undefined) {
      this.subscribeToSaveResponse(this.paciContextService.update(paciContext));
    } else {
      this.subscribeToSaveResponse(this.paciContextService.create(paciContext));
    }
  }

  private createFromForm(): IPaciContext {
    return {
      ...new PaciContext(),
      id: this.editForm.get(['id']).value,
      paciDispatcher: this.editForm.get(['paciDispatcher']).value,
      paciEngine: this.editForm.get(['paciEngine']).value,
      paciRedirect: this.editForm.get(['paciRedirect']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaciContext>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
