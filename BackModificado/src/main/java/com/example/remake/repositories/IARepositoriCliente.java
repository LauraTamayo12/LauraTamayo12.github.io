package com.example.remake.repositories;


import com.example.remake.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IARepositoriCliente extends JpaRepository<Cliente, Integer> {

    public Cliente findByIdCliente(int idCliente);

    Cliente findByUsuario(String username);
}
