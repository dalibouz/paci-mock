import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaciMockTestModule } from '../../../test.module';
import { PaciUpdateComponent } from 'app/entities/paci/paci-update.component';
import { PaciService } from 'app/entities/paci/paci.service';
import { Paci } from 'app/shared/model/paci.model';

describe('Component Tests', () => {
  describe('Paci Management Update Component', () => {
    let comp: PaciUpdateComponent;
    let fixture: ComponentFixture<PaciUpdateComponent>;
    let service: PaciService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaciMockTestModule],
        declarations: [PaciUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PaciUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaciUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaciService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Paci('123');
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
        const entity = new Paci();
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
