package com.bpichincha.microservicios.service;

import com.bpichincha.microservicios.DTOs.MovimientoReporteDTO;
import com.bpichincha.microservicios.entity.Cuenta;
import com.bpichincha.microservicios.entity.Movimiento;
import com.bpichincha.microservicios.exception.RecursoNoEncontradoException;
import com.bpichincha.microservicios.repository.CuentaRepository;
import com.bpichincha.microservicios.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    // Crear un nuevo movimiento (validando saldo)
    public Movimiento crearMovimiento(Movimiento movimiento) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(movimiento.getCuenta().getId());

        if (cuentaOpt.isEmpty()) {
            throw new RuntimeException("Cuenta no encontrada");
        }

        Cuenta cuenta = cuentaOpt.get();
        if(movimiento.getTipoMovimiento().equals("Débito")){
            movimiento.setValor(- movimiento.getValor());
        }
        double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();

        if (movimiento.getValor() < 0 && nuevoSaldo < 0) {
            throw new RuntimeException("Saldo no disponible");
        }

        movimiento.setSaldo(cuenta.getSaldoInicial());
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        return movimientoRepository.save(movimiento);
    }

    public List<MovimientoReporteDTO> obtenerPorCuenta(Long cuentaId, Date fechaInicio, Date fechaFin) {
        // Buscar la cuenta por ID
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // Obtener movimientos de esa cuenta en el rango de fechas
        List<Movimiento> movimientos = movimientoRepository.findByCuentaAndFechaBetweenOrderByFechaAsc(
                cuenta, fechaInicio, fechaFin);

        // Mapear cada movimiento a un DTO
        return movimientos.stream().map(mov -> new MovimientoReporteDTO(
                mov.getFecha(),
                cuenta.getCliente().getNombre(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                mov.getValor(),
                mov.getSaldo()
        )).collect(Collectors.toList());
    }

    // Obtener movimientos en un rango de fechas
    public List<Movimiento> obtenerPorFecha(Long cuentaId, Date fechaInicio, Date fechaFin) {
        return movimientoRepository.findByCuentaIdAndFechaBetween(cuentaId, fechaInicio, fechaFin);
    }

    public List<Movimiento> obtenerTodas() {
        return movimientoRepository.findAll();
    }

    public Movimiento obtenerPorId(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Movimiento con id: " +id+ " no encontrado"));
    }

    public Movimiento actualizarMovimiento(Long id, Movimiento nuevoMovimiento) {
        return movimientoRepository.findById(id)
                .map(movimiento -> {
                    movimiento.setCuenta(nuevoMovimiento.getCuenta());
                    movimiento.setFecha(nuevoMovimiento.getFecha());
                    movimiento.setValor(nuevoMovimiento.getValor());
                    movimiento.setTipoMovimiento(nuevoMovimiento.getTipoMovimiento());
                    movimiento.setSaldo(nuevoMovimiento.getSaldo());
                    return movimientoRepository.save(movimiento);
                })
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    public void eliminarMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }

    public String generarPdfReporte(List<MovimientoReporteDTO> movimientos) {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();
            document.add(new Paragraph("REPORTE DE MOVIMIENTOS"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.addCell("Fecha");
            table.addCell("Cliente");
            table.addCell("Cuenta");
            table.addCell("Tipo Cuenta");
            table.addCell("Saldo Inicial");
            table.addCell("Valor Movimiento");
            table.addCell("Saldo Disponible");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            for (MovimientoReporteDTO mov : movimientos) {
                table.addCell(sdf.format(mov.getFecha()));
                table.addCell(mov.getNombreCliente());
                table.addCell(mov.getNumeroCuenta());
                table.addCell(mov.getTipoCuenta());
                table.addCell(String.valueOf(mov.getSaldoInicial()));
                table.addCell(String.valueOf(mov.getValorMovimiento()));
                table.addCell(String.valueOf(mov.getSaldoDisponible()));
            }

            document.add(table);
            document.close();

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }
}
