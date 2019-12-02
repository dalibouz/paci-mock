import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPaci, Paci } from 'app/shared/model/paci.model';
import { PaciService } from './paci.service';
import { IPaciContext } from 'app/shared/model/paci-context.model';
import { PaciContextService } from 'app/entities/paci-context/paci-context.service';

@Component({
  selector: 'jhi-paci-update',
  templateUrl: './paci-update.component.html'
})
export class PaciUpdateComponent implements OnInit {
  isSaving: boolean;

  contexts: IPaciContext[];

  editForm = this.fb.group({
    id: [],
    hasPaci: [],
    url: [],
    context: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected paciService: PaciService,
    protected paciContextService: PaciContextService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paci }) => {
      this.updateForm(paci);
    });
    this.paciContextService.query({ filter: 'paci-is-null' }).subscribe(
      (res: HttpResponse<IPaciContext[]>) => {
        if (!this.editForm.get('context').value || !this.editForm.get('context').value.id) {
          this.contexts = res.body;
        } else {
          this.paciContextService
            .find(this.editForm.get('context').value.id)
            .subscribe(
              (subRes: HttpResponse<IPaciContext>) => (this.contexts = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(paci: IPaci) {
    this.editForm.patchValue({
      id: paci.id,
      hasPaci: paci.hasPaci,
      url: paci.url,
      context: paci.context
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paci = this.createFromForm();
    if (paci.id !== undefined) {
      this.subscribeToSaveResponse(this.paciService.update(paci));
    } else {
      this.subscribeToSaveResponse(this.paciService.create(paci));
    }
  }

  private createFromForm(): IPaci {
    return {
      ...new Paci(),
      id: this.editForm.get(['id']).value,
      hasPaci: this.editForm.get(['hasPaci']).value,
      url: this.editForm.get(['url']).value,
      context: this.editForm.get(['context']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaci>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPaciContextById(index: number, item: IPaciContext) {
    return item.id;
  }
}
