import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CuentaFormularioComponent } from './cuenta-formulario.component';

describe('CuentaFormularioComponent', () => {
  let component: CuentaFormularioComponent;
  let fixture: ComponentFixture<CuentaFormularioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CuentaFormularioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CuentaFormularioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
