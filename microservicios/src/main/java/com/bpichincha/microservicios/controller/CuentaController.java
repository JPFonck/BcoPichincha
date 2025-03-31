package com.bpichincha.microservicios.controller;

import com.bpichincha.microservicios.entity.Cuenta;
import com.bpichincha.microservicios.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    // Crear una nueva cuenta
    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@Valid @RequestBody Cuenta cuenta) {
        Cuenta nuevaCuenta = cuentaService.crearCuenta(cuenta);
        return new ResponseEntity<>(nuevaCuenta, HttpStatus.CREATED);
    }

    // Obtener todas las cuentas
    @GetMapping
    public ResponseEntity<List<Cuenta>> obtenerCuentas() {
        List<Cuenta> cuentas = cuentaService.obtenerTodas();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    // Obtener cuenta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.obtenerPorId(id);
        return ResponseEntity.ok(cuenta);
    }

    // Obtener cuentas de un cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Cuenta>> obtenerCuentasPorCliente(@PathVariable Long clienteId) {
        List<Cuenta> cuentas = cuentaService.obtenerPorClienteId(clienteId);
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    // Actualizar una cuenta
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        try {
            Cuenta actualizada = cuentaService.actualizarCuenta(id, cuenta);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
