package com.bpichincha.microservicios.controller;

import com.bpichincha.microservicios.entity.Cliente;
import com.bpichincha.microservicios.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ClienteRepository clienteRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    //PRUEBA PARA AGREGAR UN CLIENTE (POST /clientes)
    @Test
    public void testAgregarCliente() throws Exception {
        String nombre = "Kelly Báez";

        // Crear un objeto Cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setGenero("Femenino");
        cliente.setEdad(30);
        cliente.setIdentificacion("1234567813");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono("0987654321");
        cliente.setContrasena("123");
        cliente.setEstado(true);

        // Convertir cliente a JSON
        String clienteJson = objectMapper.writeValueAsString(cliente);

        // Simular la petición POST
        ResultActions resultado = mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson));

        // Verificar que la respuesta sea 201 Created
        resultado.andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value(nombre));
    }

    // PRUEBA PARA OBTENER UN CLIENTE (GET /clientes/{id})
    @Test
    public void testObtenerClientePorId() throws Exception {

        // Realizar una petición GET para obtener el cliente por su ID
        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}