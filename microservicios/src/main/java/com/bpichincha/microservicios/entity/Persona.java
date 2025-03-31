package com.bpichincha.microservicios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, message = "El nombre debe contener al menos 3 caracteres")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El género es obligatorio")
    private String genero;

    @Column(nullable = false)
    @NotNull(message = "La edad es obligatoria")
    private int edad;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "La identificación es obligatoria")
    @Size(min = 10, max = 10, message = "La identificación debe contener 10 caracteres")
    private String identificacion;

    @Column(nullable = false)
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @Column(nullable = false)
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
}
