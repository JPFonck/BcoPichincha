import { Component, OnInit } from '@angular/core';
import { Cliente } from '../../Models/Cliente';
import { ClienteService } from '../../Services/cliente.service';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-cliente',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.css'
})
export class ClienteComponent implements OnInit {
  clientes: Cliente[] = [];
  filtro: string='';
  
  constructor(private clienteService: ClienteService){}

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void{
    this.clienteService.ObtenerClientes().subscribe((data) => {
      this.clientes = data;
    });
  }

  eliminarCliente(id: number): void {
    if (confirm('¿Seguro que deseas eliminar este cliente?')) {
      this.clienteService.EliminarClientePorId(id).subscribe(() => {
        this.cargarClientes();
      });
    }
  }

  filtrarClientes(): Cliente[] {
    return this.clientes.filter((cliente) =>
      `${cliente.nombre}`
        .toLowerCase()
        .includes(this.filtro.toLowerCase())
    );
  }
}
