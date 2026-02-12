package com.micro.cliente.service;

import com.micro.cliente.repository.ClienteRepository;
import com.micro.cliente.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listar() {
        return repo.findAll();
    }
    public Cliente Obtener(Long id) {
        return repo.findById(id).orElseThrow();
    }
    public Cliente crear(Cliente cliente) {
        return repo.save(cliente);
    }

}