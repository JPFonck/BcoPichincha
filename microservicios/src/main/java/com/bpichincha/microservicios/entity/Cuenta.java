package com.bpichincha.microservicios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El número de cuenta es es obligatorio")
    private String numeroCuenta;

    @Column(nullable = false)
    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "Ahorros|Corriente", message = "Tipo de cuenta debe ser 'Ahorros' o 'Corriente'")
    private String tipoCuenta; // Ahorro, Corriente

    @Column(nullable = false)
    @NotNull(message = "El saldo inicial es obligatorio")
    private Double saldoInicial;

    @Column(nullable = false)
    @NotNull(message = "El estado de la cuenta es obligatorio")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) // FK que referencia a Cliente
    private Cliente cliente;
}
