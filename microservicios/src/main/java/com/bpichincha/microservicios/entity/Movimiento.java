package com.bpichincha.microservicios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "movimientos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    private Date fecha;

    @Column(nullable = false)
    @Pattern(regexp = "Crédito|Débito", message = "El movimiento debe ser Crédito o Débito")
    private String tipoMovimiento; // "Crédito" o "Débito"

    @Column(nullable = false)
    @NotNull(message = "El valor es obligatorio")
    private Double valor;

    @Column(nullable = false)
    private Double saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false) // FK que referencia a Cuenta
    private Cuenta cuenta;
}