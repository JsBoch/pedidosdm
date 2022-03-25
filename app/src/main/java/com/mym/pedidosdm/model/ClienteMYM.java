package com.mym.pedidosdm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ClienteMYM implements Parcelable {
    private String codigo;
    private String nombre;

    public ClienteMYM(String codigo,String nombre)
    {
        this.setCodigo(codigo);
        this.setNombre(nombre);
    }


    protected ClienteMYM(Parcel in) {
        codigo = in.readString();
        nombre = in.readString();
    }

    public static final Creator<ClienteMYM> CREATOR = new Creator<ClienteMYM>() {
        @Override
        public ClienteMYM createFromParcel(Parcel in) {
            return new ClienteMYM(in);
        }

        @Override
        public ClienteMYM[] newArray(int size) {
            return new ClienteMYM[size];
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

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(codigo);
        parcel.writeString(nombre);
    }
}
