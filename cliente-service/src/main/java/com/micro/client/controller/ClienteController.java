package com.micro.client.controller;

import com.micro.client.dto.ClientRequest;
import com.micro.client.dto.ClientResponse;
import com.micro.client.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClientResponse> crearCliente(@Valid @RequestBody ClientRequest request) {
        log.info("POST /clientes - Crear cliente: {}", request.getEmail());

        ClientResponse response = clienteService.crearCliente(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> obtenerTodosLosClientes() {
        log.info("GET /clientes - Obtener todos los clientes");

        List<ClientResponse> clientes = clienteService.obtenerTodosLosClientes();

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> obtenerClientePorId(@PathVariable Long id) {
        log.info("GET /clientes/{} - Obtener cliente por ID", id);

        ClientResponse cliente = clienteService.obtenerClientePorId(id);

        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequest request) {

        log.info("PUT /clientes/{} - Actualizar cliente", id);

        ClientResponse response = clienteService.actualizarCliente(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> actualizarClienteParcial(
            @PathVariable Long id,
            @RequestBody ClientRequest request) {

        log.info("PATCH /clientes/{} - Actualización parcial", id);

        ClientResponse response = clienteService.actualizarClienteParcial(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        log.info("DELETE /clientes/{} - Eliminar cliente", id);

        clienteService.eliminarCliente(id);

        return ResponseEntity.noContent().build();
    }
}