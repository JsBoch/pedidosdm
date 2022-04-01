package com.mym.pedidosdm.model;

public class DepartamentoPais {
    private int iddepartamento;
    private String nombre;
    private String codigo_postal;

public DepartamentoPais(int iddepartamento,String nombre,String codigo_postal){
    this.iddepartamento = iddepartamento;
    this.nombre = nombre;
    this.codigo_postal = codigo_postal;
}
    public int getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(int iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
