import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PaciMockTestModule } from '../../../test.module';
import { PaciContextDeleteDialogComponent } from 'app/entities/paci-context/paci-context-delete-dialog.component';
import { PaciContextService } from 'app/entities/paci-context/paci-context.service';

describe('Component Tests', () => {
  describe('PaciContext Management Delete Component', () => {
    let comp: PaciContextDeleteDialogComponent;
    let fixture: ComponentFixture<PaciContextDeleteDialogComponent>;
    let service: PaciContextService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaciMockTestModule],
        declarations: [PaciContextDeleteDialogComponent]
      })
        .overrideTemplate(PaciContextDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaciContextDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaciContextService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
