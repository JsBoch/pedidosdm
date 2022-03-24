package com.mym.pedidosdm.model;

import java.io.Serializable;

public class ClienteMYM implements Serializable {
    private String codigo;
    private String nombre;

    public ClienteMYM(String codigo,String nombre)
    {
        this.setCodigo(codigo);
        this.setNombre(nombre);
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
