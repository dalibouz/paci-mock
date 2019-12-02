import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PaciMockTestModule } from '../../../test.module';
import { PaciContextComponent } from 'app/entities/paci-context/paci-context.component';
import { PaciContextService } from 'app/entities/paci-context/paci-context.service';
import { PaciContext } from 'app/shared/model/paci-context.model';

describe('Component Tests', () => {
  describe('PaciContext Management Component', () => {
    let comp: PaciContextComponent;
    let fixture: ComponentFixture<PaciContextComponent>;
    let service: PaciContextService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaciMockTestModule],
        declarations: [PaciContextComponent],
        providers: []
      })
        .overrideTemplate(PaciContextComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaciContextComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaciContextService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaciContext('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paciContexts[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
