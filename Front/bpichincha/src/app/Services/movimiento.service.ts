import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { appsettings } from '../Settings/appsettings';
import { Observable } from 'rxjs';
import { Movimiento } from '../Models/Movimiento';

@Injectable({
  providedIn: 'root'
})
export class MovimientoService {
  private http = inject(HttpClient);
  private apiUrl:string = appsettings.apiUrl + "movimientos"

  constructor() { }

  ObtenerMovimientos(): Observable<Movimiento[]> {
    return this.http.get<Movimiento[]>(this.apiUrl);
  }

  ObtenerMovimientoPorId(id: number): Observable<Movimiento> {
    return this.http.get<Movimiento>(`${this.apiUrl}/${id}`);
  }

  CrearMovimiento(movimiento: Movimiento): Observable<Movimiento> {
    return this.http.post<Movimiento>(this.apiUrl, movimiento);
  }

  ActualizarMovimiento(movimiento: Movimiento): Observable<Movimiento> {
    return this.http.put<Movimiento>(`${this.apiUrl}/${movimiento.id}`, movimiento);
  }

  EliminarMovimiento(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  ObtenerMovimientosPorCliente(clienteId: number, fechaInicio: string, fechaFin: string): Observable<Movimiento[]> {
    return this.http.get<Movimiento[]>(
      `${this.apiUrl}/reportes?clienteId=${clienteId}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
    );
  }

  ObtenerReportePdf(idCuenta: number, fechaInicio: string, fechaFin: string): Observable<string> {
    const params = new HttpParams()
      .set('idCuenta', idCuenta.toString())
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);

    return this.http.get(this.apiUrl + '/reportes/pdf', { params, responseType: 'text' });
  }
}
