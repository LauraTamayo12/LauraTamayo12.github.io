package com.example.remake.services;

import com.example.remake.entities.Libro;
import com.example.remake.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public Libro guardarLibro(Libro libro) {
        // Puedes realizar validaciones u operaciones adicionales antes de guardar el libro si es necesario.
        // Por ejemplo, verificar si ya existe un libro con el mismo título, etc.

        // Guardar el libro en la base de datos
        return libroRepository.save(libro);
    }

    public Optional<Libro> buscarLibroPorTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }

}
