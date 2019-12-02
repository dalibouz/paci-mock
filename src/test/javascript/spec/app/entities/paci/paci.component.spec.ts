import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PaciMockTestModule } from '../../../test.module';
import { PaciComponent } from 'app/entities/paci/paci.component';
import { PaciService } from 'app/entities/paci/paci.service';
import { Paci } from 'app/shared/model/paci.model';

describe('Component Tests', () => {
  describe('Paci Management Component', () => {
    let comp: PaciComponent;
    let fixture: ComponentFixture<PaciComponent>;
    let service: PaciService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaciMockTestModule],
        declarations: [PaciComponent],
        providers: []
      })
        .overrideTemplate(PaciComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaciComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaciService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Paci('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pacis[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
