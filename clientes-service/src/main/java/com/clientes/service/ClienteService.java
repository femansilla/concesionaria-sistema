package com.clientes.service;

import com.clientes.dto.ClienteDTO;
import com.clientes.model.Cliente;
import com.clientes.repository.ClienteRepository;
import com.clientes.specification.ClienteSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ClienteDTO> buscarClientes(String nombre, String apellido, String dni) {
        Specification<Cliente> spec = ClienteSpecification.filterBy(nombre, apellido, dni);
        return clienteRepository.findAll(spec)
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
}