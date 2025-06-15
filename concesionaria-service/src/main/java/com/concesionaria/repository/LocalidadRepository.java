package main.java.com.concesionaria.repository;

import com.concesionaria.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalidadRepository extends JpaRepository<Localidad, Long> {}