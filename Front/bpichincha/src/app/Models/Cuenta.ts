import { Cliente } from "./Cliente";

export enum TipoCuenta {
  AHORROS = 'Ahorros',
  CORRIENTE = 'Corriente'
}

export interface Cuenta {
  id: number;
  numeroCuenta: string;
  tipoCuenta: TipoCuenta;
  saldoInicial: number;
  estado: boolean;
  cliente: Cliente;
}
