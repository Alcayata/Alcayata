package com.senamoviles.alcayata.alcayata;

/**
 * Created by danielardila on 10/11/17.
 */

public class Paso {
    private String nombre;
    private String imagen;
    private String descripcion;
    private String sabias;
    //private String sabiasQueMas;
    //private boolean modeloPaso;


    public Paso() {
    }

    public Paso(String nombre, String imagen, String descripcion, String sabias) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.sabias = sabias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSabias() {
        return sabias;
    }

    public void setSabias(String sabias) {
        this.sabias = sabias;
    }
}


