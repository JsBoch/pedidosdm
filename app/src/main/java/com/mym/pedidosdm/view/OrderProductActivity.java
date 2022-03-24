package com.mym.pedidosdm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mym.pedidosdm.R;
import com.mym.pedidosdm.model.ClienteMYM;
import com.mym.pedidosdm.model.ProductList;
import com.mym.pedidosdm.model.RegistroProducto;
import com.mym.pedidosdm.model.UsuarioBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class OrderProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerProduct;
    Spinner spinnerPrecio;
    Spinner spinnerCliente;
    EditText etPrecioProducto;
    EditText etCantidadProducto;
    EditText etObservacionesProducto;
    TextView tvMontoTotal;
    Button bttnAgregarProducto;
    Button bttnConfirmarPedido;

    ArrayList<ProductList> productList = new ArrayList<ProductList>();
    ArrayList<ClienteMYM> clientList = new ArrayList<ClienteMYM>();
    ArrayList<RegistroProducto>  listaRegistro = new ArrayList<RegistroProducto>();
    ArrayAdapter<ProductList> productAdapter;
    ArrayAdapter<ClienteMYM> clientAdapter;
    RequestQueue requestQueue;
    RequestQueue requestQueueCliente;
    ProductList item;
    ClienteMYM itemCliente;

    Integer positionCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);

        /* **************** NAVIGATION BAR ************************ */
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("REGISTRO DE PEDIDOS");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.primaryTextColor));
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.primaryColor));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_mym_24);
        /* ************************************************************* */

        /* **************** REFERENCIA DE VISTAS *************** */
        requestQueue = Volley.newRequestQueue(this);
        requestQueueCliente = Volley.newRequestQueue(this);
        spinnerProduct = findViewById(R.id.spOrderProductView);
        spinnerPrecio = findViewById(R.id.spOrderProductPrecioView);
        spinnerPrecio.setOnItemSelectedListener(this);
        spinnerCliente = findViewById(R.id.spOrderProductClienteView);
        etPrecioProducto = findViewById(R.id.etOrderProductPrecioView);
        etCantidadProducto = findViewById(R.id.etOrderProductCantidadView);
        etObservacionesProducto = findViewById(R.id.etOrderProductObservacionesView);
        tvMontoTotal = findViewById(R.id.tvOrderProductTotalView);
        bttnAgregarProducto = findViewById(R.id.bttnOrderProductAgregarView);
        bttnConfirmarPedido = findViewById(R.id.bttnOrderProductConfirmarPedidoView);

        /* ********** LISTENERS ************************* */
        //CANTIDAD: calculo del total entre precio y cantidad
        etCantidadProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPrecioProducto.length() != 0 && etCantidadProducto.length() != 0)
                {
                    Double precio = Double.parseDouble(etPrecioProducto.getText().toString());
                    Integer cantidad = Integer.parseInt(etCantidadProducto.getText().toString());
                    Double total = precio * cantidad;
                    tvMontoTotal.setText(total.toString());
                }

            }
        });

        //BOTON AGREGAR: Agrega un item con todos los datos del registro
        bttnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPrecioProducto.length() == 0 || Double.parseDouble(etPrecioProducto.getText().toString()) == 0)
                {
                    Toast.makeText(OrderProductActivity.this, "Debe ingresar el precio del producto",
                            Toast.LENGTH_SHORT).show();
                }
                else if(etCantidadProducto.length() == 0 || Integer.parseInt(etCantidadProducto.getText().toString()) == 0)
                {
                    Toast.makeText(OrderProductActivity.this, "Debe ingresar la cantidad de producto",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //AGREGAR A CLASE REGISTROPRODUCTO
                    RegistroProducto itemProducto = new RegistroProducto(UsuarioBase.get().getUsuarioId(), itemCliente.getCodigo(),
                            item.getCodigo(),item.getNombre(),etObservacionesProducto.getText().toString().trim(),
                            etPrecioProducto.getTag().toString(),Double.parseDouble(etPrecioProducto.getText().toString()),
                            Integer.parseInt(etCantidadProducto.getText().toString()),
                            Double.parseDouble(tvMontoTotal.getText().toString()));
                    listaRegistro.add(itemProducto);
                    //se almacena esta posición para saber que cliente estaba seleccionado y volverlo a seleccionar
                    //si el usuario quiere agregar otro registro
                    Toast.makeText(OrderProductActivity.this, "Agregado ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTÓN CONFIRMAR: llama al activity de confirmación para validar el pedido
        bttnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionCliente = clientAdapter.getPosition(itemCliente);

                Intent intent = new Intent(getApplicationContext(),ConfirmacionPedidoActivity.class);
                intent.putExtra("clientePedido",itemCliente);
                intent.putExtra("listaProducto",listaRegistro);
                intent.putExtra("posicionCliente",positionCliente);
                startActivity(intent);
            }
        });

        //WS LISTA DE PRODUCTOS: obtiene el listado de productos de la db rmym
        String URL = getString(R.string.URL_ProductList);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int i = 0; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String codigo = jsonObject.optString("codigo");
                        String nombre = jsonObject.optString("nombre");
                        Double venta = jsonObject.optDouble("venta");
                        Double uno = jsonObject.optDouble("uno");
                        Double dos = jsonObject.optDouble("dos");
                        Double tres = jsonObject.optDouble("tres");
                        productList.add(new ProductList(codigo,nombre,venta,uno,dos,tres));
                        productAdapter = new ArrayAdapter<>(OrderProductActivity.this,
                                android.R.layout.simple_spinner_item,productList);
                        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerProduct.setAdapter(productAdapter);
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);


        //WS LISTADO CLIENTES: carga el listado de clientes de la db rmym
        String URLC = getString(R.string.URL_ClientList);
        JsonObjectRequest jsonObjectRequestCliente = new JsonObjectRequest(Request.Method.POST,
                URLC, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("clients");
                    for (int i = 0; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String codigo = jsonObject.optString("codigo");
                        String nombre = jsonObject.optString("nombre");
                        clientList.add(new ClienteMYM(codigo,nombre));
                        clientAdapter = new ArrayAdapter<>(OrderProductActivity.this,
                                android.R.layout.simple_spinner_item,clientList);
                        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCliente.setAdapter(clientAdapter);
                        spinnerCliente.setSelection(positionCliente);
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(OrderProductActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderProductActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueueCliente.add(jsonObjectRequestCliente);
        /* ***************************************************************************************** */

        spinnerProduct.setOnItemSelectedListener(this);
        spinnerCliente.setOnItemSelectedListener(this);


        //VALIDACIÓN PARA AGERGAR PRODUCTOS AL VENIR DE CONFIRMACIÓN DE PEDIDO
        if((ArrayList<RegistroProducto>) getIntent().getSerializableExtra("listaProducto") != null) {
            listaRegistro = (ArrayList<RegistroProducto>) getIntent().getSerializableExtra("listaProducto");
        }
        if((ClienteMYM) getIntent().getSerializableExtra("clientePedido") != null) {
            itemCliente = (ClienteMYM) getIntent().getSerializableExtra("clientePedido");
        }

        positionCliente = getIntent().getExtras().getInt("posicionCliente");
    }


    /*Estos métodos se generan al poner el spinnerProduct.setOnItemSelectedListener(this);*/
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spOrderProductView)
        {
            item = (ProductList) adapterView.getSelectedItem();
            ListadoPrecios();
        }
        else if(adapterView.getId() == R.id.spOrderProductPrecioView)
        {
            String precioLista = (String) adapterView.getSelectedItem();
            String[] precioSeleccionado =  precioLista.split(" - ");
            etPrecioProducto.setText(precioSeleccionado[1].toString());
            etPrecioProducto.setTag(precioSeleccionado[0].toString());
        }
        else if(adapterView.getId() == R.id.spOrderProductClienteView)
        {
            itemCliente = (ClienteMYM) adapterView.getSelectedItem();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    /* ********************************************* */

    //Método que carga la lista de precios
    private void ListadoPrecios()
    {
        if(item != null) {
            String[] Precio = {"VENTA - " + item.getVenta().toString(), "UNO - " + item.getUno().toString(),
            "DOS - " + item.getDos().toString(),"TRES - " + item.getTres().toString()};
            spinnerPrecio.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_dropdown_item,Precio));

        }
    }
}