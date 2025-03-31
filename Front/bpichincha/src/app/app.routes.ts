import { Routes } from '@angular/router';
import { ClienteComponent } from './Components/cliente/cliente.component';
import { ClienteFormularioComponent } from './Components/cliente-formulario/cliente-formulario.component';
import { CuentaComponent } from './Components/Cuenta/cuenta/cuenta.component';
import { CuentaFormularioComponent } from './Components/Cuenta/cuenta-formulario/cuenta-formulario.component';
import { MovimientoComponent } from './Components/Movimiento/movimiento/movimiento.component';
import { MovimientoFormularioComponent } from './Components/Movimiento/movimiento-formulario/movimiento-formulario.component';
import { ReporteComponent } from './Components/Reporte/reporte/reporte.component';

export const routes: Routes = [
    { path: '', redirectTo: 'clientes', pathMatch: 'full' },
    { path: 'clientes', component: ClienteComponent },
    { path: 'clientes/nuevo', component: ClienteFormularioComponent },
    { path: 'clientes/editar/:id', component: ClienteFormularioComponent },
    { path: 'cuentas', component: CuentaComponent },
    { path: 'cuentas/nueva', component: CuentaFormularioComponent },
    { path: 'cuentas/editar/:id', component: CuentaFormularioComponent },
    { path: 'movimientos', component: MovimientoComponent },
    { path: 'movimientos/nuevo', component: MovimientoFormularioComponent },
    { path: 'movimientos/editar/:id', component: MovimientoFormularioComponent },
    { path: 'reportes', component: ReporteComponent }
];
