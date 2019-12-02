export interface IPaciContext {
  id?: string;
  paciDispatcher?: string;
  paciEngine?: string;
  paciRedirect?: string;
}

export class PaciContext implements IPaciContext {
  constructor(public id?: string, public paciDispatcher?: string, public paciEngine?: string, public paciRedirect?: string) {}
}
