package main.java.com.concesionaria.repository;

import com.concesionaria.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais, Long> {}