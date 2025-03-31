package com.bpichincha.microservicios.service;

import com.bpichincha.microservicios.entity.Cuenta;
import com.bpichincha.microservicios.exception.RecursoNoEncontradoException;
import com.bpichincha.microservicios.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    // Crear una cuenta
    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    // Obtener todas las cuentas
    public List<Cuenta> obtenerTodas() {
        return cuentaRepository.findAll();
    }

    // Obtener cuenta por ID
    public Cuenta obtenerPorId(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta con id: " +id+ " no encontrada"));
    }

    // Obtener cuentas de un cliente
    public List<Cuenta> obtenerPorClienteId(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }

    // Actualizar una cuenta
    public Cuenta actualizarCuenta(Long id, Cuenta nuevaCuenta) {
        return cuentaRepository.findById(id)
                .map(cuenta -> {
                    cuenta.setTipoCuenta(nuevaCuenta.getTipoCuenta());
                    cuenta.setSaldoInicial(nuevaCuenta.getSaldoInicial());
                    cuenta.setEstado(nuevaCuenta.getEstado());
                    cuenta.setCliente(nuevaCuenta.getCliente());
                    return cuentaRepository.save(cuenta);
                })
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    // Eliminar una cuenta
    public void eliminarCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }
}