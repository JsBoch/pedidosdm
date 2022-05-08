package com.mym.pedidosdm.model;

public class ClienteBase {
    private ClienteMYM cliente;

    private static ClienteBase clienteBase;
    public static ClienteBase get()
    {
        if(clienteBase == null)
        {
            clienteBase = new ClienteBase();
        }
        return clienteBase;
    }


    public ClienteMYM getCliente() {
        return cliente;
    }

    public void setCliente(ClienteMYM cliente) {
        this.cliente = cliente;
    }
}
