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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mym.pedidosdm.R;
import com.mym.pedidosdm.databinding.ActivityConfirmacionPedidoBinding;
import com.mym.pedidosdm.model.ClienteMYM;
import com.mym.pedidosdm.model.CustomBaseAdapterProductos;
import com.mym.pedidosdm.model.ProductList;
import com.mym.pedidosdm.model.RegistroProducto;
import com.mym.pedidosdm.model.UsuarioBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
        //listaRegistro = (ArrayList<RegistroProducto>) getIntent().getSerializableExtra("listaProducto");
        listaRegistro = getIntent().getParcelableArrayListExtra("listaProducto");
        //itemCliente = (ClienteMYM) getIntent().getSerializableExtra("clientePedido");
        itemCliente = getIntent().getParcelableExtra("clientePedido");
        clientePosition = getIntent().getExtras().getInt("posicionCliente");

        //CAMBIO, SOLO ERA EL listaProducto
        CustomBaseAdapterProductos customAdapter = new CustomBaseAdapterProductos(getApplicationContext(), (ArrayList<RegistroProducto>) listaRegistro);
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
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("ENVIAR PEDIDO")
                        .setMessage("¿Está segur@ que desea enviar el pedido?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //JSON PRINCIPAL
                                final JSONObject jsonObject = new JSONObject();
                                try {

                                    jsonObject.put("id_departamento",itemCliente.getDepartamentoId());
                                    jsonObject.put("id_empleado", UsuarioBase.get().getUsuarioId());
                                    jsonObject.put("codigo_cliente",itemCliente.getCodigo());
                                    jsonObject.put("nombre_cliente",itemCliente.getNombre());
                                    String observaciones = binding.etObservacionesGeneral.getText().toString();
                                    jsonObject.put("observaciones",observaciones);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                //JSON DETALLE
                                JSONObject objJS = null;
                                JSONArray jsArray = new JSONArray();
                                for (RegistroProducto item : listaRegistro
                                    ) {
                                        objJS = new JSONObject();
                                    try {
                                        objJS.put("codigo_producto",item.getCodigo());
                                        objJS.put("nombre_producto",item.getNombre());
                                        objJS.put("cantidad",item.getCantidad());
                                        objJS.put("tipoprecio",item.getTipoPrecio());
                                        objJS.put("precio",item.getPrecio());
                                        objJS.put("subtotal",item.getTotal());
                                        objJS.put("observaciones",item.getObservaciones());


                                        objJS.put("total",item.getTotal());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    jsArray.put(objJS);
                                }

                                JSONObject objJSEnvio = new JSONObject();
                                try {
                                    objJSEnvio.put("detalle",jsArray);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                /* ++++++++++++++++++++++++++++++++++++++++ */
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                String URL = getString(R.string.URL_RegistroPedido);

                                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                        URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            Integer codigo = jsonObject.getInt("codigo");
                                                Toast.makeText(ConfirmacionPedidoActivity.this, "CODIGO " + codigo, Toast.LENGTH_SHORT).show();
                                        }
                                        catch (JSONException ex)
                                        {
                                            ex.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(ConfirmacionPedidoActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams()  {
                                        Map<String,String>parms=new HashMap<String, String>();
                                        parms.put("registro_principal",jsonObject.toString());
                                        parms.put("detalle_registro",objJSEnvio.toString());
                                        return parms;
                                    }
                                };

                                requestQueue.add(stringRequest);
                                /* ++++++++++++++++++++++++++++++++++++++ */
                            }
                        })
                        .setNegativeButton("NO",null)
                        .show();

                binding.etObservacionesGeneral.setText("");
                listaRegistro.clear();
                customAdapter.notifyDataSetChanged();
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