package com.example.remake.controller;

import com.example.remake.entities.Cliente;
import com.example.remake.repositories.IARepositoriCliente;
import com.example.remake.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDate;
import java.time.Period;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/Cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private IARepositoriCliente iaRepositoriCliente;

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Busca al cliente por nombre de usuario en la base de datos
        Cliente cliente = iaRepositoriCliente.findByUsuario(username);

        if (cliente != null) {
            // Compara el hash de la contraseña proporcionada con el hash almacenado
            if (BCrypt.checkpw(password, cliente.getPassword())) {
                // La contraseña es correcta, autenticación exitosa
                String role = cliente.getRol();

                Map<String, Object> response = new HashMap<>();
                response.put("authenticated", true);

                if ("cliente".equals(role)) {
                    // Redirige a la URL externa de Users.html para clientes
                    response.put("authenticated", true);
                    response.put("redirectUrl", "http://127.0.0.1:5500/Users.html");
                    return ResponseEntity.ok(response);
                } else if ("admin".equals(role)) {
                    // Redirige al HTML de administradores
                    response.put("redirectUrl", "/admin.html");
                } else {
                    // Otros roles pueden ser manejados de manera similar
                    response.put("redirectUrl", "/otra_pagina.html");
                }
                return ResponseEntity.ok(response);
            }
        }
        // Autenticación fallida
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("authenticated", false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    @PostMapping("/saveCliente")
    public Cliente saveCliente(@RequestBody Cliente cliente) {
        System.out.println("id_cliente recibido en el backend: " + cliente.getIdCliente());
        return clienteService.saveCliente(cliente);
    }
    @GetMapping("/listClientes")
    public List<Cliente> listClientes() {
        return clienteService.listClientes();
    }
    @GetMapping("/checkUniqueness/{idCliente}")
    public ResponseEntity<Map<String, Boolean>> checkIdUniqueness(@PathVariable Long idCliente) {
        boolean isUnique = !clienteService.existsByIdCliente(idCliente);

        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Cliente userData) {
        try {
            // Verifica si el nombre de usuario es alfanumérico con punto (.)
            if (!userData.getUsuario().matches("^[a-zA-Z0-9.]+$")) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Nombre de usuario no válido");
                System.out.println("Revisó la Username");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Verifica la seguridad de la contraseña (mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número)
            String password = userData.getPassword();
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Contraseña no segura");
                System.out.println("Revisó la contraseña");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Convierte la fecha en un formato de cadena (por ejemplo, "yyyy-MM-dd")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaNacimientoStr = dateFormat.format(userData.getFecha_Nacimiento());

            // Verifica la fecha de nacimiento (mayor de 18 años)
            LocalDate birthDate = LocalDate.parse(fechaNacimientoStr);
            LocalDate today = LocalDate.now();
            Period age = Period.between(birthDate, today);
            if (age.getYears() < 18) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Debes ser mayor de 18 años para registrarte");
                System.out.println("Revisó la edad");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Realiza el hash de la contraseña antes de guardarla
            String hashedPassword = BCrypt.hashpw(userData.getPassword(), BCrypt.gensalt());
            userData.setPassword(hashedPassword);
            // Establece el rol por defecto como "cliente"
            userData.setRol("cliente");

            // Imprime los valores para verificar antes de guardar
            System.out.println("Nombre: " + userData.getNombre());
            System.out.println("Apellidos: " + userData.getApellido());
            System.out.println("Fecha de Nacimiento: " + fechaNacimientoStr);
            System.out.println("Usuario: " + userData.getUsuario());
            System.out.println("Password: " + userData.getPassword());

            // Guarda el usuario en la base de datos
            clienteService.saveCliente(userData);

            // Devuelve una respuesta JSON con un mensaje de éxito
            Map<String, String> response = new HashMap<>();
            response.put("message", "Registro exitoso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // En caso de error, devuelve una respuesta JSON con un mensaje de error
            e.printStackTrace(); // Agrega este registro para ver si se lanza alguna excepción
            Map<String, String> errorResponse = new HashMap<>();
            System.out.println("No Revisó ni mrda");
            errorResponse.put("error", "Error al registrar el usuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}