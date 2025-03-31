import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Cliente } from '../../../Models/Cliente';
import { Movimiento } from '../../../Models/Movimiento';
import { MovimientoService } from '../../../Services/movimiento.service';
import { ClienteService } from '../../../Services/cliente.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reporte',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './reporte.component.html',
  styleUrl: './reporte.component.css'
})
export class ReporteComponent implements OnInit {
  busquedaForm: FormGroup;
  clientes: Cliente[] = [];
  movimientos: Movimiento[] = [];
  movimientosPorCuenta: { [key: string]: Movimiento[] } = {};

  constructor(
    private fb: FormBuilder,
    private movimientoService: MovimientoService,
    private clienteService: ClienteService
  ) {
    this.busquedaForm = this.fb.group({
      clienteId: [null, Validators.required],
      fechaInicio: ['', Validators.required],
      fechaFin: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void {
    this.clienteService.ObtenerClientes().subscribe((data) => this.clientes = data);
  }

  buscarMovimientos(): void {
    if (this.busquedaForm.valid) {
      const { clienteId, fechaInicio, fechaFin } = this.busquedaForm.value;
  
      this.movimientoService.ObtenerMovimientos().subscribe((data) => {
        this.movimientos = data.filter(mov => {
          const fechaMov = new Date(mov.fecha);
          const inicio = new Date(fechaInicio);
          const fin = new Date(fechaFin);
          const cliente = mov.cuenta.cliente.id;
  
          return fechaMov >= inicio && fechaMov <= fin && cliente==clienteId;
        });
      });
    }
  }

  descargarReportePdf(): void {
    if (this.movimientos.length === 0) return;

    const cuentaId = this.movimientos[0].cuenta.id;
    const { fechaInicio, fechaFin } = this.busquedaForm.value;

    this.movimientoService.ObtenerReportePdf(cuentaId, fechaInicio, fechaFin).subscribe(base64 => {
      const link = document.createElement('a');
      link.href = `data:application/pdf;base64,${base64}`;
      link.download = 'Reporte-Estado-Cuenta.pdf';
      link.click();
    });
  }
}
