package com.mym.pedidosdm.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mym.pedidosdm.R;

import java.util.ArrayList;

public class AdaptadorProducto extends ArrayAdapter<Producto> {
private ArrayList<Producto> arrayList = new ArrayList<>();
private Context context;

    public AdaptadorProducto(@NonNull Context context) {
        super(context, R.layout.item_view);
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public Producto getItem(int position){
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position){
        return  position;
    }

    @Override
    public void add(@Nullable Producto object)
    {
        arrayList.add((object));
        notifyDataSetChanged();
    }

    @Override
    public void clear()
    {
        arrayList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = convertView==null? LayoutInflater.from(context).inflate(R.layout.item_view,parent,false):convertView;
        TextView item_text = view.findViewById(R.id.item_text);
        Producto producto = getItem(position);
        item_text.setText(producto.toString());
        ProductoBase.get().setProducto(producto);
        return view;
    }
}
