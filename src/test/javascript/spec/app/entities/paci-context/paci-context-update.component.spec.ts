import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaciMockTestModule } from '../../../test.module';
import { PaciContextUpdateComponent } from 'app/entities/paci-context/paci-context-update.component';
import { PaciContextService } from 'app/entities/paci-context/paci-context.service';
import { PaciContext } from 'app/shared/model/paci-context.model';

describe('Component Tests', () => {
  describe('PaciContext Management Update Component', () => {
    let comp: PaciContextUpdateComponent;
    let fixture: ComponentFixture<PaciContextUpdateComponent>;
    let service: PaciContextService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaciMockTestModule],
        declarations: [PaciContextUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PaciContextUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaciContextUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaciContextService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaciContext('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaciContext();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
