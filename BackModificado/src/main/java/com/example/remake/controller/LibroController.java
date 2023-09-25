package com.example.remake.controller;

import com.example.remake.entities.Libro;
import com.example.remake.repositories.LibroRepository;
import com.example.remake.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository; // Un repositorio de Spring Data MongoDB

    @Autowired
    private LibroService libroService;
    @PostMapping("/guardar")
    public Libro guardarLibro(@RequestBody Libro libro) {
        return libroService.guardarLibro(libro);
    }
    @GetMapping("/mostrar/{nombre}")
    public ResponseEntity<String> mostrarLibroPorNombre(@PathVariable String nombre) {
        // Consulta la entidad "Libro" por nombre
        System.out.println("Nombre recibido en mostrarLibroPorNombre: " + nombre);

        // Realiza una consulta a la base de datos para encontrar el libro por nombre
        Optional<Libro> libroOptional = libroService.buscarLibroPorTitulo(nombre);

        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();
            System.out.println("Encontró el libro");
            // Obtén el enlace al libro desde el campo "archivoPDF"
            String enlaceLibro = libro.getArchivoPDF();

            // Devuelve el enlace como respuesta HTTP
            return ResponseEntity.ok(enlaceLibro);
        } else {
            System.out.println("No encontró el libro");
            // El libro no se encontró, puedes manejar el error de acuerdo a tus necesidades
            return ResponseEntity.notFound().build();
        }
    }

}