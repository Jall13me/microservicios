package com.micro.client.service;

import com.micro.client.dto.ClientRequest;
import com.micro.client.dto.ClientResponse;
import com.micro.client.event.ClientEvent;
import com.micro.client.exception.ClientNotFoundException;
import com.micro.client.exception.DuplicateEmailException;
import com.micro.client.mapper.ClientMapper;
import com.micro.client.messaging.ClientMessageProducer;
import com.micro.client.model.Client;
import com.micro.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteService {

    private final ClientRepository clienteRepository;
    private final ClientMapper clienteMapper;
    private final ClientMessageProducer messageProducer;

    public ClientResponse crearCliente(ClientRequest request) {
        log.info("Creando nuevo cliente con email: {}", request.getEmail());


        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }


        Client cliente = clienteMapper.toEntity(request);
        Client savedCliente = clienteRepository.save(cliente);

        log.info("Cliente creado exitosamente con ID: {}", savedCliente.getId());


        ClientEvent event = ClientEvent.builder()
                .clienteId(savedCliente.getId())
                .nombre(savedCliente.getNombre())
                .email(savedCliente.getEmail())
                .accion("CREATED")
                .timestamp(LocalDateTime.now())
                .build();

        messageProducer.sendClientCreatedEvemt(event);


        return clienteMapper.toResponse(savedCliente);
    }

    @Transactional(readOnly = true)
    public List<ClientResponse> obtenerTodosLosClientes() {
        log.info("Obteniendo todos los clientes");

        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ClientResponse obtenerClientePorId(Long id) {
        log.info("Obteniendo cliente con ID: {}", id);

        Client cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return clienteMapper.toResponse(cliente);
    }

    public ClientResponse actualizarCliente(Long id, ClientRequest request) {
        log.info("Actualizando cliente con ID: {}", id);


        Client cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));


        if (!cliente.getEmail().equals(request.getEmail())
                && clienteRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        clienteMapper.updateEntityFromRequest(request, cliente);
        Client updatedCliente = clienteRepository.save(cliente);

        log.info("Cliente actualizado exitosamente con ID: {}", id);


        ClientEvent event = ClientEvent.builder()
                .clienteId(updatedCliente.getId())
                .nombre(updatedCliente.getNombre())
                .email(updatedCliente.getEmail())
                .accion("UPDATED")
                .timestamp(LocalDateTime.now())
                .build();

        messageProducer.sendClientUpdatedEvemt(event);

        return clienteMapper.toResponse(updatedCliente);
    }

    public ClientResponse actualizarClienteParcial(Long id, ClientRequest request) {
        log.info("Actualizando parcialmente cliente con ID: {}", id);


        Client cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));


        if (request.getEmail() != null
                && !cliente.getEmail().equals(request.getEmail())
                && clienteRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }


        clienteMapper.updateEntityFromRequest(request, cliente);
        Client updatedCliente = clienteRepository.save(cliente);

        log.info("Cliente actualizado parcialmente con ID: {}", id);


        ClientEvent event = ClientEvent.builder()
                .clienteId(updatedCliente.getId())
                .nombre(updatedCliente.getNombre())
                .email(updatedCliente.getEmail())
                .accion("UPDATED")
                .timestamp(LocalDateTime.now())
                .build();

        messageProducer.sendClientUpdatedEvemt(event);

        return clienteMapper.toResponse(updatedCliente);
    }

    public void eliminarCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);


        Client cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));


        clienteRepository.delete(cliente);

        log.info("Cliente eliminado exitosamente con ID: {}", id);

        ClientEvent event = ClientEvent.builder()
                .clienteId(id)
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .accion("DELETED")
                .timestamp(LocalDateTime.now())
                .build();

        messageProducer.sendClientDeletedEvemt(event);
    }
}