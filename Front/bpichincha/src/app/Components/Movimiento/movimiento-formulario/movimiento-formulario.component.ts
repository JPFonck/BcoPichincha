import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MovimientoService } from '../../../Services/movimiento.service';
import { TipoMovimiento, Movimiento } from '../../../Models/Movimiento';
import { Cuenta } from '../../../Models/Cuenta';
import { CuentaService } from '../../../Services/cuenta.service';

@Component({
  selector: 'app-movimiento-formulario',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './movimiento-formulario.component.html',
  styleUrl: './movimiento-formulario.component.css'
})
export class MovimientoFormularioComponent implements OnInit {
  movimientoForm: FormGroup;
  editar: boolean = false;
  tiposMovimiento = Object.values(TipoMovimiento);
  cuentas: Cuenta[] = [];

  constructor(
    private fb: FormBuilder,
    private movimientoService: MovimientoService,
    private cuentaService: CuentaService,
    private route: ActivatedRoute,
    public router: Router
  ) {
    this.movimientoForm = this.fb.group({
      id: [],
      fecha: [new Date().toISOString().slice(0, 10), Validators.required], // Fecha actual por defecto
      tipoMovimiento: ['', Validators.required],
      valor: [0, [Validators.required, Validators.min(1)]],
      cuenta: [null, Validators.required] // Se enviará el objeto Cuenta completo
    });
  }

  ngOnInit(): void {
    this.cargarCuentas();
    
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editar = true;
      this.movimientoService.ObtenerMovimientoPorId(+id).subscribe((data) => {
        this.movimientoForm.patchValue({
          ...data,
          cuenta: this.cuentas.find(c => c.id === data.cuenta.id) // Asigna el objeto Cuenta
        });
      });
    }
  }

  cargarCuentas(): void {
    this.cuentaService.ObtenerCuentas().subscribe((data) => {
      this.cuentas = data;
    });
  }

  guardarMovimiento(): void {
    if (this.movimientoForm.valid) {
      const movimientoData = this.movimientoForm.value;
      movimientoData.cuenta = this.cuentas.find(c => c.id === movimientoData.cuenta.id); // Enviar el objeto Cuenta completo

      if (this.editar) {
        this.movimientoService.ActualizarMovimiento(movimientoData).subscribe(() => {
          this.router.navigate(['/movimientos']);
        });
      } else {
        this.movimientoService.CrearMovimiento(movimientoData).subscribe(() => {
          this.router.navigate(['/movimientos']);
        });
      }
    }
  }
}
