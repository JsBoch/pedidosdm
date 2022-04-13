package com.mym.pedidosdm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ClienteMYM implements Parcelable {
    private String codigo;
    private String nombre;
    private int departamentoId;
    private int municipioId;

    public ClienteMYM(String codigo,String nombre,int departamentoId,int municipioId)
    {
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setDepartamentoId(departamentoId);
        this.setMunicipioId(municipioId);
    }


    protected ClienteMYM(Parcel in) {
        codigo = in.readString();
        nombre = in.readString();
        departamentoId = in.readInt();
        municipioId = in.readInt();
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
    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
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
        parcel.writeInt(departamentoId);
        parcel.writeInt(municipioId);
    }


    public int getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(int municipioId) {
        this.municipioId = municipioId;
    }
}
