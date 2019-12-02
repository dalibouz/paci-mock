import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaciMockTestModule } from '../../../test.module';
import { PaciDetailComponent } from 'app/entities/paci/paci-detail.component';
import { Paci } from 'app/shared/model/paci.model';

describe('Component Tests', () => {
  describe('Paci Management Detail Component', () => {
    let comp: PaciDetailComponent;
    let fixture: ComponentFixture<PaciDetailComponent>;
    const route = ({ data: of({ paci: new Paci('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaciMockTestModule],
        declarations: [PaciDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaciDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaciDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paci).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
