package com.mym.pedidosdm.model;

public class ProductList {
    private String codigo;
    private String nombre;

    public ProductList(String codigo,String nombre)
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
        return codigo + " | " + nombre;
    }
}
