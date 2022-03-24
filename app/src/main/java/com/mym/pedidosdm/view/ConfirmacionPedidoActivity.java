package com.mym.pedidosdm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mym.pedidosdm.R;
import com.mym.pedidosdm.databinding.ActivityConfirmacionPedidoBinding;
import com.mym.pedidosdm.model.ClienteMYM;
import com.mym.pedidosdm.model.CustomBaseAdapterProductos;
import com.mym.pedidosdm.model.RegistroProducto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ConfirmacionPedidoActivity extends AppCompatActivity {

    private ActivityConfirmacionPedidoBinding binding;

    ArrayList<RegistroProducto> listaRegistro;
    ClienteMYM itemCliente;
    int clientePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmacionPedidoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Código utilizado para colocar el navigationbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("CONFIRMACIÓN DE PEDIDO");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.primaryTextColor));
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.primaryColor));
        setSupportActionBar(toolbar);

        //LECTURA DE PARÁMETROS
        listaRegistro = (ArrayList<RegistroProducto>) getIntent().getSerializableExtra("listaProducto");
        itemCliente = (ClienteMYM) getIntent().getSerializableExtra("clientePedido");
        clientePosition = getIntent().getExtras().getInt("posicionCliente");

        CustomBaseAdapterProductos customAdapter = new CustomBaseAdapterProductos(getApplicationContext(),listaRegistro);
        binding.lvProductosPedido.setAdapter(customAdapter);
        SumarizarProductos();
        binding.lvProductosPedido.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int item_to_delete = i;
                new AlertDialog.Builder(ConfirmacionPedidoActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Eliminar item")
                        .setMessage("¿Está segur@ que desea eliminar el item?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listaRegistro.remove(item_to_delete);
                                customAdapter.notifyDataSetChanged();
                                SumarizarProductos();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();


                return true;
            }
        });

        binding.bttnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OrderProductActivity.class);
                intent.putExtra("clientePedido",itemCliente);
                intent.putExtra("listaProducto",listaRegistro);
                intent.putExtra("posicionCliente",clientePosition);
                intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP); //si ya existe el activity no lo vuelve a crear
                startActivity(intent);
            }
        });

        binding.bttnEnviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ConfirmacionPedidoActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("ENVIAR PEDIDO")
                        .setMessage("¿Está segur@ que desea enviar el pedido?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO
                                Toast.makeText(ConfirmacionPedidoActivity.this, "Hecho", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NO",null)
                        .show();
            }
        });
    }

    private void SumarizarProductos()
    {
        DecimalFormat formato = new DecimalFormat("###,###.00");

        Double total = 0d;

        for (RegistroProducto item: listaRegistro
             ) {
                total += item.getTotal();
        }

        //String montoFormateado = String.format(Locale.getDefault(), "%.2f",total);
        String montoFormateado = formato.format(total);
        binding.tvTotalPedido.setText(montoFormateado);
    }
}