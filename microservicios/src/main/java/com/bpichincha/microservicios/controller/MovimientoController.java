package com.bpichincha.microservicios.controller;

import com.bpichincha.microservicios.DTOs.MovimientoReporteDTO;
import com.bpichincha.microservicios.entity.Cuenta;
import com.bpichincha.microservicios.entity.Movimiento;
import com.bpichincha.microservicios.service.CuentaService;
import com.bpichincha.microservicios.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;
    @Autowired
    private CuentaService cuentaService;

    // Crear un nuevo movimiento
    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@Valid @RequestBody Movimiento movimiento) {
        try {
            Movimiento nuevoMovimiento = movimientoService.crearMovimiento(movimiento);
            return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener movimientos en un rango de fechas
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Movimiento>> obtenerMovimientosPorFechas(
            @RequestParam Long cuentaId, @RequestParam Date fechaInicio, @RequestParam Date fechaFin) {
        List<Movimiento> movimientos = movimientoService.obtenerPorFecha(cuentaId, fechaInicio, fechaFin);
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Movimiento>> obtenerMovimientos() {
        List<Movimiento> movimientos = movimientoService.obtenerTodas();
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtenerMovimientoPorId(@PathVariable Long id) {
        Movimiento movimiento = movimientoService.obtenerPorId(id);
        return ResponseEntity.ok(movimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> actualizarMovimiento(@PathVariable Long id, @RequestBody Movimiento movimiento) {
        try {
            Movimiento actualizada = movimientoService.actualizarMovimiento(id, movimiento);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reportes")
    public ResponseEntity<List<MovimientoReporteDTO>> obtenerReporte(
            @RequestParam Long idCuenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {

        List<MovimientoReporteDTO> reporte = movimientoService.obtenerPorCuenta(idCuenta, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/reportes/pdf")
    public ResponseEntity<String> obtenerReportePdfBase64(
            @RequestParam Long idCuenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {

        List<MovimientoReporteDTO> datos = movimientoService.obtenerPorCuenta(idCuenta, fechaInicio, fechaFin);
        String pdfBase64 = movimientoService.generarPdfReporte(datos);

        return ResponseEntity.ok(pdfBase64);
    }
}