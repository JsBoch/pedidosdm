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

public class AdaptadorCliente extends ArrayAdapter<ClienteMYM> {
    private ArrayList<ClienteMYM> arrayList = new ArrayList<>();
    private Context context;

    public AdaptadorCliente(@NonNull Context context) {
        super(context, R.layout.client_item_view);
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public ClienteMYM getItem(int position){
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position){
        return  position;
    }

    @Override
    public void add(@Nullable ClienteMYM object)
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
        View view = convertView==null? LayoutInflater.from(context).inflate(R.layout.client_item_view,parent,false):convertView;
        TextView client_item_text = view.findViewById(R.id.client_item_text);
        ClienteMYM cliente = getItem(position);
        client_item_text.setText(cliente.toString());
        ClienteBase.get().setCliente(cliente);
        return view;
    }
}
