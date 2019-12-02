import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaciMockTestModule } from '../../../test.module';
import { PaciContextDetailComponent } from 'app/entities/paci-context/paci-context-detail.component';
import { PaciContext } from 'app/shared/model/paci-context.model';

describe('Component Tests', () => {
  describe('PaciContext Management Detail Component', () => {
    let comp: PaciContextDetailComponent;
    let fixture: ComponentFixture<PaciContextDetailComponent>;
    const route = ({ data: of({ paciContext: new PaciContext('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaciMockTestModule],
        declarations: [PaciContextDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaciContextDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaciContextDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paciContext).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
