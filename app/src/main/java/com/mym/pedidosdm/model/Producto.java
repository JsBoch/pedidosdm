package com.mym.pedidosdm.model;

import org.json.JSONObject;

public class Producto {
    private String codigo;
    private String nombre;
    private Double venta;
    private Double uno;
    private Double dos;
    private Double tres;

    public Producto(JSONObject jsonObject)
    {
        codigo = jsonObject.optString("codigo");
        nombre = jsonObject.optString("nombre");
        venta = jsonObject.optDouble("venta");
        uno = jsonObject.optDouble("uno");
        dos = jsonObject.optDouble("dos");
        tres = jsonObject.optDouble("tres");
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
