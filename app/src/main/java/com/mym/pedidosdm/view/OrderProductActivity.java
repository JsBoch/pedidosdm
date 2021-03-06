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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mym.pedidosdm.R;
import com.mym.pedidosdm.databinding.ActivityOrderProductBinding;
import com.mym.pedidosdm.model.AdaptadorCliente;
import com.mym.pedidosdm.model.AdaptadorProducto;
import com.mym.pedidosdm.model.ClienteBase;
import com.mym.pedidosdm.model.ClienteMYM;
import com.mym.pedidosdm.model.ProductList;
import com.mym.pedidosdm.model.Producto;
import com.mym.pedidosdm.model.ProductoBase;
import com.mym.pedidosdm.model.RegistroProducto;
import com.mym.pedidosdm.model.UsuarioBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityOrderProductBinding binding;

    //ArrayList<ProductList> productList = new ArrayList<>();
    //ArrayList<ClienteMYM> clientList = new ArrayList<>();
    ArrayList<RegistroProducto> listaRegistro = new ArrayList<>();
    //ArrayAdapter<ProductList> productAdapter;
    //ArrayAdapter<ClienteMYM> clientAdapter;
    RequestQueue requestQueue;
    RequestQueue requestQueueCliente;
    //ProductList item;
    //ClienteMYM itemCliente;

    //Integer positionCliente;

    //nuevo para el autocompletetextview
    private AdaptadorProducto adaptadorProducto;
    //nuevo para el AutoCompleteTextView de cliente
    private AdaptadorCliente adaptadorCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //Nuevo para el AutoCompleteTextView
        adaptadorProducto = new AdaptadorProducto(this);
        binding.atvProducto.setAdapter(adaptadorProducto);
        binding.atvProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                makeRequest(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.atvProducto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b)
                {
                    ListadoPrecios();
                }


            }
        });

        //nuevo para el AutoCompleteTextView de Cliente
        adaptadorCliente = new AdaptadorCliente(this);
        binding.atvCliente.setAdapter(adaptadorCliente);
        binding.atvCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                makeRequestClient(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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

        binding.spPrecio.setOnItemSelectedListener(this);

        /* ********** LISTENERS ************************* */
        //CANTIDAD: calculo del total entre precio y cantidad
        binding.etCantidadProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.etPrecioProducto.length() != 0 && binding.etCantidadProducto.length() != 0)
                {
                    Double precio = Double.parseDouble(binding.etPrecioProducto.getText().toString());
                    Integer cantidad = Integer.parseInt(binding.etCantidadProducto.getText().toString());
                    Double total = precio * cantidad;
                    binding.tvTotalProducto.setText(total.toString());
                }

            }
        });

        //BOTON AGREGAR: Agrega un item con todos los datos del registro
        binding.bttnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etPrecioProducto.length() == 0 || Double.parseDouble(binding.etPrecioProducto.getText().toString()) == 0)
                {
                    Toast.makeText(OrderProductActivity.this, "Debe ingresar el precio del producto",
                            Toast.LENGTH_SHORT).show();
                }
                else if(binding.etCantidadProducto.length() == 0 || Integer.parseInt(binding.etCantidadProducto.getText().toString()) == 0)
                {
                    Toast.makeText(OrderProductActivity.this, "Debe ingresar la cantidad de producto",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //AGREGAR A CLASE REGISTROPRODUCTO
                    Producto prd = ProductoBase.get().getProducto();
                     /*RegistroProducto itemProducto = new RegistroProducto(
                            item.getCodigo(),item.getNombre(),binding.etObservaciones.getText().toString().trim(),
                            binding.etPrecioProducto.getTag().toString(),Double.parseDouble(binding.etPrecioProducto.getText().toString()),
                            Integer.parseInt(binding.etCantidadProducto.getText().toString()),
                            Double.parseDouble(binding.tvTotalProducto.getText().toString()));*/

                    //Modificaci??n para utilizar el autocompleteTextView
                            RegistroProducto itemProducto = new RegistroProducto(
                            prd.getCodigo(),prd.getNombre(),binding.etObservaciones.getText().toString().trim(),
                            binding.etPrecioProducto.getTag().toString(),Double.parseDouble(binding.etPrecioProducto.getText().toString()),
                            Integer.parseInt(binding.etCantidadProducto.getText().toString()),
                            Double.parseDouble(binding.tvTotalProducto.getText().toString()));
                    listaRegistro.add(itemProducto);

                    //limpieza de los views
                    binding.etPrecioProducto.setText("");
                    binding.etCantidadProducto.setText("");
                    binding.etObservaciones.setText("");
                    binding.tvTotalProducto.setText("");
                    binding.atvProducto.setText("");

                    Toast.makeText(OrderProductActivity.this, "Agregado ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOT??N CONFIRMAR: llama al activity de confirmaci??n para validar el pedido
        binding.bttnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listaRegistro.size() > 0) {
                    //positionCliente = clientAdapter.getPosition(itemCliente);

                    Intent intent = new Intent(getApplicationContext(), ConfirmacionPedidoActivity.class);
                    //intent.putExtra("clientePedido", itemCliente);
                    intent.putExtra("listaProducto", listaRegistro);
                    //intent.putExtra("posicionCliente", positionCliente);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(OrderProductActivity.this, "Debe agregar productos para guardar.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //WS LISTA DE PRODUCTOS: obtiene el listado de productos de la db rmym
        /*String URL = getString(R.string.URL_ProductList);
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
                        binding.spProducto.setAdapter(productAdapter);
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

        requestQueue.add(jsonObjectRequest);*/


        //WS LISTADO CLIENTES: carga el listado de clientes de la db rmym
        /*String URLC = getString(R.string.URL_ClientList);
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
                        int departamentoId = jsonObject.optInt("iddepartamento");
                        int municipioId = jsonObject.optInt("idmunicipio");
                        clientList.add(new ClienteMYM(codigo,nombre,departamentoId,municipioId));
                        clientAdapter = new ArrayAdapter<>(OrderProductActivity.this,
                                android.R.layout.simple_spinner_item,clientList);
                        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spCliente.setAdapter(clientAdapter);
                        binding.spCliente.setSelection(positionCliente);
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

        requestQueueCliente.add(jsonObjectRequestCliente);*/
        /* ***************************************************************************************** */

        //binding.spProducto.setOnItemSelectedListener(this);
        //binding.spCliente.setOnItemSelectedListener(this);

        //VALIDACI??N PARA AGREGAR PRODUCTOS AL VENIR DE CONFIRMACI??N DE PEDIDO
        if(getIntent().getSerializableExtra("listaProducto") != null) {
            //listaRegistro = (ArrayList<RegistroProducto>)getIntent().getSerializableExtra("listaProducto");
            listaRegistro = getIntent().getParcelableArrayListExtra("listaProducto");
        }
        /*if((ClienteMYM) getIntent().getSerializableExtra("clientePedido") != null) {
            //itemCliente = (ClienteMYM) getIntent().getSerializableExtra("clientePedido");
            itemCliente = getIntent().getParcelableExtra("clientePedido");
        }*/

        //positionCliente = getIntent().getExtras().getInt("posicionCliente");

        if (ClienteBase.get().getCliente() != null)
        {
            binding.atvCliente.setText(ClienteBase.get().getCliente().getNombre());
        }
    }


    /*Estos m??todos se generan al poner el spinnerProduct.setOnItemSelectedListener(this);*/
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        /*if(adapterView.getId() == binding.spProducto.getId()) // R.id.spProducto
        {
            item = (ProductList) adapterView.getSelectedItem();
            ListadoPrecios();
        }
        else*/
        if(adapterView.getId() == binding.spPrecio.getId()) //R.id.spOrderProductPrecioView*/
        {
            String precioLista = (String) adapterView.getSelectedItem();
            String[] precioSeleccionado =  precioLista.split(" - ");
            binding.etPrecioProducto.setText(precioSeleccionado[1].toString());
            binding.etPrecioProducto.setTag(precioSeleccionado[0].toString());
        }
        /*else if(adapterView.getId() == binding.spCliente.getId()) //R.id.spOrderProductClienteView
        {
            itemCliente = (ClienteMYM) adapterView.getSelectedItem();
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    /* ********************************************* */

    //M??todo que carga la lista de precios
    private void ListadoPrecios()
    {
        Producto prd = ProductoBase.get().getProducto();
        /*if(item != null) {
            String[] Precio = {"VENTA - " + item.getVenta().toString(), "UNO - " + item.getUno().toString(),
            "DOS - " + item.getDos().toString(),"TRES - " + item.getTres().toString()};
            binding.spPrecio.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_dropdown_item,Precio));

        }*/
        if(prd != null) {
            String[] Precio = {"VENTA - " + prd.getVenta().toString(), "UNO - " + prd.getUno().toString(),
                    "DOS - " + prd.getDos().toString(),"TRES - " + prd.getTres().toString()};
            binding.spPrecio.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_dropdown_item,Precio));

        }
    }

    private void makeRequest(String text)
    {
        String URL = getString(R.string.URL_ProductoAutoComplete) + text;
        adaptadorProducto.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int i = 0; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Producto producto = new Producto(jsonObject);
                        adaptadorProducto.add(producto);
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
    }

    private void makeRequestClient(String text)
    {
        String URLC = getString(R.string.URL_ClientList)+text;
        adaptadorCliente.clear();
        JsonObjectRequest jsonObjectRequestCliente = new JsonObjectRequest(Request.Method.GET,
                URLC, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("clients");
                    for (int i = 0; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ClienteMYM cliente = new ClienteMYM(jsonObject);
                        adaptadorCliente.add(cliente);
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
    }
}