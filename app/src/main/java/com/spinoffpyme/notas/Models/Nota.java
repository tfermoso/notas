package com.spinoffpyme.notas.Models;

/**
 * Created by Tomás on 16/11/2017.
 */

public class Nota {
    private int idnota;
    private String titulo;
    private String descripcion;
    private int idusuario;

    public Nota(int idnota, String titulo, String descripcion, int idusuario) {
        this.idnota = idnota;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idusuario = idusuario;
    }
    public Nota( String titulo, String descripcion, int idusuario){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idusuario = idusuario;
    }

    public int getIdnota() {
        return idnota;
    }

    public void setIdnota(int idnota) {
        this.idnota = idnota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }
}
