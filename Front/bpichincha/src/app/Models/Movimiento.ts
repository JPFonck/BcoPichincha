import { Cuenta } from "./Cuenta";

export enum TipoMovimiento {
  CREDITO = 'Crédito',
  DEBITO = 'Débito'
}

export interface Movimiento {
  id: number;
  fecha: string | Date;
  tipoMovimiento: TipoMovimiento;
  valor: number;
  saldo?: number;  
  cuenta: Cuenta;
}
