package com.mym.pedidosdm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.NotSerializableException;
import java.io.Serializable;

public class RegistroProducto implements Parcelable {
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
    public RegistroProducto(String codigo,String nombre,String observaciones,
                            String tipoPrecio,Double precio,Integer cantidad,Double total)
    {
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setObservaciones(observaciones);
        this.setTipoPrecio(tipoPrecio);
        this.setPrecio(precio);
        this.setCantidad(cantidad);
        this.setTotal(total);
    }

    protected RegistroProducto(Parcel in) {
        codigo = in.readString();
        nombre = in.readString();
        observaciones = in.readString();
        tipoPrecio = in.readString();
        if (in.readByte() == 0) {
            precio = null;
        } else {
            precio = in.readDouble();
        }
        if (in.readByte() == 0) {
            cantidad = null;
        } else {
            cantidad = in.readInt();
        }
        if (in.readByte() == 0) {
            total = null;
        } else {
            total = in.readDouble();
        }
    }

    public static final Creator<RegistroProducto> CREATOR = new Creator<RegistroProducto>() {
        @Override
        public RegistroProducto createFromParcel(Parcel in) {
            return new RegistroProducto(in);
        }

        @Override
        public RegistroProducto[] newArray(int size) {
            return new RegistroProducto[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(codigo);
        parcel.writeString(nombre);
        parcel.writeString(observaciones);
        parcel.writeString(tipoPrecio);
        if (precio == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(precio);
        }
        if (cantidad == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(cantidad);
        }
        if (total == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(total);
        }
    }
}
