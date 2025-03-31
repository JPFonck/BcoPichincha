import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TipoCuenta } from '../../../Models/Cuenta';
import { CuentaService } from '../../../Services/cuenta.service';
import { Cliente } from '../../../Models/Cliente';
import { ClienteService } from '../../../Services/cliente.service';

@Component({
  selector: 'app-cuenta-formulario',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './cuenta-formulario.component.html',
  styleUrl: './cuenta-formulario.component.css'
})
export class CuentaFormularioComponent implements OnInit {
  cuentaForm: FormGroup;
  editar: boolean = false;
  tiposCuenta = Object.values(TipoCuenta);
  clientes: Cliente[] = [];

  constructor(
    private fb: FormBuilder,
    private cuentaService: CuentaService,
    private clienteService: ClienteService,
    private route: ActivatedRoute,
    public router: Router
  ) {
    this.cuentaForm = this.fb.group({
      id: [],
      numeroCuenta: ['', Validators.required],
      tipoCuenta: ['', Validators.required],
      saldoInicial: [0, [Validators.required, Validators.min(0)]],
      estado: [true],
      cliente: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.cargarClientes();
    
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editar = true;
      this.cuentaService.ObtenerCuentaPorId(+id).subscribe((data) => {
        this.cuentaForm.patchValue({
          ...data,
          cliente: this.clientes.find(c => c.id === data.cliente.id) // Asigna el objeto completo del cliente
        });
      });
    }
  }

  cargarClientes(): void {
    this.clienteService.ObtenerClientes().subscribe((data) => {
      this.clientes = data;
    });
  }

  guardarCuenta(): void {
    if (this.cuentaForm.valid) {
      const cuentaData = this.cuentaForm.value;
      cuentaData.cliente = this.clientes.find(c => c.id === cuentaData.cliente.id); // Enviar el objeto Cliente completo

      if (this.editar) {
        this.cuentaService.ActualizarCuenta(cuentaData).subscribe(() => {
          this.router.navigate(['/cuentas']);
        });
      } else {
        this.cuentaService.crearCuenta(cuentaData).subscribe(() => {
          this.router.navigate(['/cuentas']);
        });
      }
    }
  }
}
