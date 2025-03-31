package com.bpichincha.microservicios.controller;

import com.bpichincha.microservicios.entity.Cliente;
import com.bpichincha.microservicios.entity.Cuenta;
import com.bpichincha.microservicios.repository.ClienteRepository;
import com.bpichincha.microservicios.repository.CuentaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void crearCuenta() throws Exception {

        cliente = new Cliente();
        cliente.setNombre("Carlos Peña");
        cliente.setGenero("Masculino");
        cliente.setEdad(35);
        cliente.setIdentificacion("9998887149"); //Modificar para pruebas
        cliente.setDireccion("Av. Central 456");
        cliente.setTelefono("123456789");
        cliente.setEstado(true);
        cliente.setContrasena("12345");

        cliente = clienteRepository.save(cliente);

        String numeroCuenta = "1234567892"; //Modificar para pruebas

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(5000.0);
        cuenta.setEstado(true);
        cuenta.setCliente(cliente); // Asociar con cliente

        // Convertir cuenta a JSON
        String cuentaJson = objectMapper.writeValueAsString(cuenta);

        // Enviar petición POST
        ResultActions resultado = mockMvc.perform(post("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cuentaJson));

        // Verificar la respuesta
        resultado.andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCuenta").value(numeroCuenta))
                .andExpect(jsonPath("$.tipoCuenta").value("Ahorros"));
    }

    @Test
    void obtenerCuentas() throws Exception {
        mockMvc.perform(get("/cuentas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}