package com.spinoffpyme.notas.Models;

import java.io.Serializable;

/**
 * Created by Tomás on 16/11/2017.
 */

public class Usuario implements Serializable {
    private int idusuario;
    private String nombre;
    private String email;
    private String password;

    public Usuario(int idusuario, String nombre, String email, String password) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }
    public Usuario(int idusuario,String nombre,String email){
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.email = email;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
