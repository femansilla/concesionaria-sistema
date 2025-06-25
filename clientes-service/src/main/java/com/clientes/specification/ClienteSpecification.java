package com.clientes.specification;

import com.clientes.model.Cliente;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ClienteSpecification {
    public static Specification<Cliente> filterBy(String nombre, String apellido, String dni) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
            }
            if (apellido != null) {
                predicates.add(cb.like(cb.lower(root.get("apellido")), "%" + apellido.toLowerCase() + "%"));
            }
            if (dni != null) {
                predicates.add(cb.equal(root.get("dni"), dni));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
