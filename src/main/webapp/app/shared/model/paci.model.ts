import { IPaciContext } from 'app/shared/model/paci-context.model';

export interface IPaci {
  id?: string;
  hasPaci?: boolean;
  url?: string;
  context?: IPaciContext;
}

export class Paci implements IPaci {
  constructor(public id?: string, public hasPaci?: boolean, public url?: string, public context?: IPaciContext) {
    this.hasPaci = this.hasPaci || false;
  }
}
