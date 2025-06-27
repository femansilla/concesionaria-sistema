package com.clientes.service;

import com.clientes.DTO.ClienteDTO;
import com.clientes.model.Cliente;
import com.clientes.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ModelMapper mapper;

    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = repository.findAll();
        return clientes.stream()
            .map(cliente -> mapper.map(cliente, ClienteDTO.class))
            .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> findById(Long id) {
        return repository.findById(id).map(c -> mapper.map(c, ClienteDTO.class));
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        return mapper.map(repository.save(cliente), ClienteDTO.class);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente cliente = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setDni(dto.getDni());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());

        return mapper.map(repository.save(cliente), ClienteDTO.class);
    }

    public List<ClienteDTO> buscarClientes(String nombre, String apellido, String dni) {
        return repository.buscarPorFiltros(nombre, apellido, dni).stream()
            .map(c -> mapper.map(c, ClienteDTO.class))
            .toList();
    }
}