package com.mym.pedidosdm.model;

public class MunicipioDepartamento {
    private int id_municipio;
    private int id_departamento;
    private String nombre;

    public MunicipioDepartamento(int id_municipio,int id_departamento,String nombre){
        this.id_municipio = id_municipio;
        this.id_departamento = id_departamento;
        this.nombre = nombre;
    }


    public int getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
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
