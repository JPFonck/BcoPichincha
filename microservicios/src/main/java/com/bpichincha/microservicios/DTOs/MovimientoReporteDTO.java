package com.bpichincha.microservicios.DTOs;

import java.util.Date;

public class MovimientoReporteDTO {

    private Date fecha;
    private String nombreCliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Double valorMovimiento;
    private Double saldoDisponible;

    public MovimientoReporteDTO(Date fecha, String nombreCliente, String numeroCuenta,
                                String tipoCuenta, Double saldoInicial, Double valorMovimiento,
                                Double saldoDisponible) {
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.valorMovimiento = valorMovimiento;
        this.saldoDisponible = saldoDisponible;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(Double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public Double getValorMovimiento() {
        return valorMovimiento;
    }

    public void setValorMovimiento(Double valorMovimiento) {
        this.valorMovimiento = valorMovimiento;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
}
