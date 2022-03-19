package com.mym.pedidosdm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mym.pedidosdm.R;
import com.mym.pedidosdm.model.ProductList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerProduct;
    ArrayList<ProductList> productList = new ArrayList<ProductList>();
    ArrayAdapter<ProductList> productAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);

        //Código utilizado para colocar el navigationbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("REGISTRO DE PEDIDOS");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.primaryTextColor));
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.primaryColor));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_mym_24);
        /* ************************************************************* */

        requestQueue = Volley.newRequestQueue(this);
        spinnerProduct = findViewById(R.id.spOrderProductView);

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
                        productList.add(new ProductList(codigo,nombre));
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

        spinnerProduct.setOnItemSelectedListener(this);
    }

 /*Estos métodos se generan al poner el spinnerProduct.setOnItemSelectedListener(this);*/
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spOrderProductView)
        {
            ProductList item = (ProductList) adapterView.getSelectedItem();
            Toast.makeText(this,"codigo: " + item.getCodigo() + " nombre: " + item.getNombre(),Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    /* ********************************************* */
}