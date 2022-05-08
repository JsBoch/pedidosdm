package com.mym.pedidosdm.model;

public class ProductoBase {
    private Producto producto;

    private static ProductoBase productoBase;
    public static ProductoBase get()
    {
        if(productoBase == null)
        {
            productoBase = new ProductoBase();
        }
        return productoBase;
    }


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
