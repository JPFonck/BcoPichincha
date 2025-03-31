package com.bpichincha.microservicios.repository;

import com.bpichincha.microservicios.entity.Cuenta;
import com.bpichincha.microservicios.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaId(Long cuentaId);
    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, Date fechaInicio, Date fechaFin);
    List<Movimiento> findByCuentaAndFechaBetweenOrderByFechaAsc(Cuenta cuenta, Date fechaInicio, Date fechaFin);
}