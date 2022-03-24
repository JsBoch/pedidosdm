package com.mym.pedidosdm.model;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mym.pedidosdm.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomBaseAdapterProductos extends BaseAdapter {

    Context context;
    ArrayList<RegistroProducto> listaProductos;
    LayoutInflater inflater;

    public CustomBaseAdapterProductos(Context ctx, ArrayList<RegistroProducto> listaProductos)
    {
        this.context = ctx;
        this.listaProductos = listaProductos;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listaProductos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_custom_list_pedido,null);
        TextView tvCodigo = (TextView) view.findViewById(R.id.tvCustomListProductoCodigoView);
        TextView tvNombre = (TextView) view.findViewById(R.id.tvCustomListProductoNombreView);
        TextView tvTipoPrecio = (TextView)view.findViewById(R.id.tvCustomListProductoTipoPrecioView);
        TextView tvPrecio = (TextView)view.findViewById(R.id.tvCustomListProductoPrecioView);
        TextView tvCantidad = (TextView)view.findViewById(R.id.tvCustomListProductoCantidadView);
        TextView tvTotal = (TextView)view.findViewById(R.id.tvCustomListProductoTotalView);
        TextView tvObservaciones = (TextView)view.findViewById(R.id.tvCustomListProductoObservacionesView);

        tvCodigo.setText(listaProductos.get(i).getCodigo());
        tvNombre.setText(listaProductos.get(i).getNombre());
        tvTipoPrecio.setText(listaProductos.get(i).getTipoPrecio());
        tvPrecio.setText(listaProductos.get(i).getPrecio().toString());
        tvCantidad.setText(listaProductos.get(i).getCantidad().toString());
        tvTotal.setText(listaProductos.get(i).getTotal().toString());
        tvObservaciones.setText(listaProductos.get(i).getObservaciones());
        return view;
    }
}
