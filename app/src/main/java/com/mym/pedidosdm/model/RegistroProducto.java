package com.mym.pedidosdm.model;

import android.os.Parcelable;

import java.io.Serializable;

public class RegistroProducto implements Serializable {
    private Integer vendedorId;
    private String codigoCliente;
    private String codigo;
    private String nombre;
    private String observaciones;
    private String tipoPrecio;
    private Double precio;
    private Integer cantidad;
    private Double total;

    public RegistroProducto()
    {

    }
    public RegistroProducto(Integer vendedorId,String codigoCliente,String codigo,String nombre,String observaciones,
                            String tipoPrecio,Double precio,Integer cantidad,Double total)
    {
        this.setVendedorId(vendedorId);
        this.setCodigoCliente(codigoCliente);
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setObservaciones(observaciones);
        this.setTipoPrecio(tipoPrecio);
        this.setPrecio(precio);
        this.setCantidad(cantidad);
        this.setTotal(total);
    }
    public Integer getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Integer vendedorId) {
        this.vendedorId = vendedorId;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(String tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
