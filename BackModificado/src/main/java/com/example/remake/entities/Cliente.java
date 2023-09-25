package com.example.remake.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name= "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name= "id_cliente")
    private int idCliente;

    @Column (name= "nombre")
    private String nombre;

    @Column (name="apellido")
    private String apellido;

    @Column (name="fecha_nacimiento")
    private Date fecha_Nacimiento;

    @Column (name= "usuario")
    private String usuario;

    @Column (name= "password")
    private String password;

    @Column(name = "rol")
    private String rol;
    public Cliente() {

    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFecha_Nacimiento() {
        return fecha_Nacimiento;
    }

    public void setFecha_Nacimiento(Date fecha_Nacimiento) {
        this.fecha_Nacimiento = fecha_Nacimiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
