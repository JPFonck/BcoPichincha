import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { appsettings } from '../Settings/appsettings';
import { Cliente } from '../Models/Cliente';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private http = inject(HttpClient);
  private apiUrl:string = appsettings.apiUrl + "clientes"

  constructor() { }

  ObtenerClientes(): Observable<Cliente[]>{
    return this.http.get<Cliente[]>(this.apiUrl);
  }

  ObtenerClientePorId(id:number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

  CrearCliente(cliente:Cliente): Observable<Cliente>{
    return this.http.post<Cliente>(this.apiUrl,cliente)
  }

  ActualizarCliente(cliente:Cliente): Observable<Cliente>{
    return this.http.put<Cliente>(`${this.apiUrl}/${cliente.id}`, cliente)
  }

  EliminarClientePorId(id:number){
    return this.http.delete<Cliente>(`${this.apiUrl}/${id}`);
  }
}
