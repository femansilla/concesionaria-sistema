package main.java.com.concesionaria.repository;

import com.concesionaria.model.Concesionaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcesionariaRepository extends JpaRepository<Concesionaria, Long> {}