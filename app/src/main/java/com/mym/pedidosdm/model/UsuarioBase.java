package com.mym.pedidosdm.model;

public class UsuarioBase {
    private int usuarioId;
    private String usuarioNombre ;

    private UsuarioBase()
    {
        
    }

    private static UsuarioBase usuarioBase;
    public static UsuarioBase get(){
        if(usuarioBase == null)
        {
            usuarioBase = new UsuarioBase();
        }
        return usuarioBase;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
}
