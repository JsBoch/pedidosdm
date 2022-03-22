package com.mym.pedidosdm.model;

public class ProductList {
    private String codigo;
    private String nombre;
    private Double venta;
    private Double uno;
    private Double dos;
    private Double tres;

    public ProductList(String codigo,String nombre,Double venta,Double uno,Double dos,Double tres)
    {
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setVenta(venta);
        this.setUno(uno);
        this.setDos(dos);
        this.setTres(tres);
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

    public Double getVenta() {
        return venta;
    }

    public void setVenta(Double venta) {
        this.venta = venta;
    }

    public Double getUno() {
        return uno;
    }

    public void setUno(Double uno) {
        this.uno = uno;
    }

    public Double getDos() {
        return dos;
    }

    public void setDos(Double dos) {
        this.dos = dos;
    }

    public Double getTres() {
        return tres;
    }

    public void setTres(Double tres) {
        this.tres = tres;
    }
}
