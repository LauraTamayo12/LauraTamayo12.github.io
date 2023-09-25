package com.example.remake.repositories;

import com.example.remake.entities.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroRepository extends MongoRepository<Libro, String> {

    Optional<Libro> findByTitulo(String titulo); //Encontrar por titulo

}
