import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Cuenta } from '../../../Models/Cuenta';
import { CuentaService } from '../../../Services/cuenta.service';

@Component({
  selector: 'app-cuenta',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './cuenta.component.html',
  styleUrl: './cuenta.component.css'
})
export class CuentaComponent implements OnInit {

  cuentas: Cuenta[] = []
  filtro: string='';

  constructor(private cuentaService: CuentaService) {}

  ngOnInit(): void {
    this.cargarCuentas();
  }

  cargarCuentas(): void {
    this.cuentaService.ObtenerCuentas().subscribe((data) => {
      this.cuentas = data;
    });
  }

  eliminarCuenta(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta cuenta?')) {
      this.cuentaService.EliminarCuenta(id).subscribe(() => {
        this.cargarCuentas();
      });
    }
  }

  filtrarCuentas(): Cuenta[] {
    return this.cuentas.filter((cuenta) =>
      `${cuenta.numeroCuenta}`
        .toLowerCase()
        .includes(this.filtro.toLowerCase())
    );
  }
}
