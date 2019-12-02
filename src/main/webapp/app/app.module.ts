import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PaciMockSharedModule } from 'app/shared/shared.module';
import { PaciMockCoreModule } from 'app/core/core.module';
import { PaciMockAppRoutingModule } from './app-routing.module';
import { PaciMockHomeModule } from './home/home.module';
import { PaciMockEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PaciMockSharedModule,
    PaciMockCoreModule,
    PaciMockHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PaciMockEntityModule,
    PaciMockAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class PaciMockAppModule {}
