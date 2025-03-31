package com.bpichincha.microservicios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "id") // Une la PK de Cliente con la de Persona
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {

    @Column(nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    @Column(nullable = false)
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}
