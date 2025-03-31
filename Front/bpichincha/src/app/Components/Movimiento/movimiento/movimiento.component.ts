import { Component, OnInit } from '@angular/core';
import { MovimientoService } from '../../../Services/movimiento.service';
import { Movimiento } from '../../../Models/Movimiento';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-movimiento',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './movimiento.component.html',
  styleUrl: './movimiento.component.css'
})
export class MovimientoComponent implements OnInit {
  movimientos: Movimiento[] = [];
  filtro: string='';

  constructor(private movimientoService: MovimientoService) {}

  ngOnInit(): void {
    this.cargarMovimientos();
  }

  cargarMovimientos(): void {
    this.movimientoService.ObtenerMovimientos().subscribe((data) => {
      this.movimientos = data;
    });
  }

  eliminarMovimiento(id: number): void {
    this.movimientoService.EliminarMovimiento(id).subscribe(() => {
      this.cargarMovimientos();
    });
  }

  filtrarMovimientos(): Movimiento[] {
    return this.movimientos.filter((movimiento) =>
      `${movimiento.cuenta.numeroCuenta}`
        .toLowerCase()
        .includes(this.filtro.toLowerCase())
    );
  }
}
