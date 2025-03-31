import { Component, OnInit } from '@angular/core';
import { Cliente } from '../../Models/Cliente';
import { ClienteService } from '../../Services/cliente.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cliente-formulario',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './cliente-formulario.component.html',
  styleUrl: './cliente-formulario.component.css'
})
export class ClienteFormularioComponent implements OnInit {
  clienteForm: FormGroup;
  editar: boolean = false;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private route: ActivatedRoute,
    public router: Router
  ) {
    // Inicializar el formulario con validaciones
    this.clienteForm = this.fb.group({
      id: [],
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      contrasena: ['', [Validators.required]],
      direccion: ['', Validators.required],
      edad: [0, [Validators.required]],
      estado: [false],
      genero: ['', Validators.required],
      identificacion: ['', Validators.required],
      telefono: ['', [Validators.required, Validators.pattern('^[0-9]+$')]]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editar = true;
      this.clienteService.ObtenerClientePorId(+id).subscribe((data) => {
        this.clienteForm.patchValue(data); // Carga los datos en el formulario
      });
    }
  }

  guardarCliente(): void {
    if (this.clienteForm.valid) {
      if (this.editar) {
        this.clienteService.ActualizarCliente(this.clienteForm.value).subscribe(() => {
          this.router.navigate(['/clientes']);
        });
      } else {
        this.clienteService.CrearCliente(this.clienteForm.value).subscribe(() => {
          this.router.navigate(['/clientes']);
        });
      }
    }
  }
}
