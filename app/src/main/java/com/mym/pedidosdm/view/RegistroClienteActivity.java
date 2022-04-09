package com.mym.pedidosdm.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mym.pedidosdm.R;
import com.mym.pedidosdm.databinding.ActivityRegistroClienteBinding;
import com.mym.pedidosdm.model.DepartamentoPais;
import com.mym.pedidosdm.model.MunicipioDepartamento;
import com.mym.pedidosdm.model.ProductList;
import com.mym.pedidosdm.model.UsuarioBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RegistroClienteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityRegistroClienteBinding binding;
    ArrayList<DepartamentoPais> listaDepartamento = new ArrayList<>();
    ArrayList<MunicipioDepartamento> listaMunicipio = new ArrayList<>();
    ArrayList<MunicipioDepartamento> listaMunicipioDepartamento = new ArrayList<>();
    ArrayAdapter<DepartamentoPais> adapterDepartamento;
    ArrayAdapter<MunicipioDepartamento> adapterMunicipio;
    RequestQueue queueDepartamento;
    RequestQueue queueMunicipio;

    DepartamentoPais itemDepartamento;
    MunicipioDepartamento itemMunicipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroClienteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /* **************** NAVIGATION BAR ************************ */
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("REGISTRO DE CLIENTES");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.primaryTextColor));
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.primaryColor));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_mym_24);
        /* ************************************************************* */

        //CAMBIO A MAYUSCULAS
        binding.etNombre.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.etRazonSocial.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.etDireccion.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.etRegion.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.etTransporte.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        /*
        * Se carga el listado de departamentos desde el WS
        *
        * */
        queueDepartamento = Volley.newRequestQueue(this);
        String urlDepartamento = getString(R.string.URL_ListaDepartamento);
        JsonObjectRequest jsonRequestDepartamento = new JsonObjectRequest(Request.Method.POST,
                urlDepartamento, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayDepartamento = response.getJSONArray("departamentos");
                    int departamentoId;
                    String departamentoNombre;
                    String codigoPostal;

                    for (int i = 0; i < jsonArrayDepartamento.length(); i++) {
                        JSONObject jsonObject = jsonArrayDepartamento.getJSONObject(i);
                        departamentoId = jsonObject.optInt("iddepartamento");
                        departamentoNombre = jsonObject.optString("nombre");
                        codigoPostal = jsonObject.optString("codigo_postal");
                        listaDepartamento.add(new DepartamentoPais(departamentoId, departamentoNombre,codigoPostal));
                    }
                    adapterDepartamento = new ArrayAdapter<>(RegistroClienteActivity.this,
                            android.R.layout.simple_spinner_item, listaDepartamento);
                    adapterDepartamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spDepartamento.setAdapter(adapterDepartamento);
                } catch (JSONException jex) {
                    Toast.makeText(RegistroClienteActivity.this, jex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistroClienteActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        queueDepartamento.add(jsonRequestDepartamento);

        /*
            Se carga el listado de municipios
        * */
        queueMunicipio = Volley.newRequestQueue(this);
        String urlMunicipio = getString(R.string.URL_ListaMunicipio);
        JsonObjectRequest jsonMunicipioRequest = new JsonObjectRequest(Request.Method.POST, urlMunicipio,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                        JSONArray jsonArrayMunicipio = response.getJSONArray("municipios");
                        int municipioID;
                        int departamentoID;
                        String municipioNombre;
                    for (int i = 0; i < jsonArrayMunicipio.length(); i++) {
                        JSONObject jsonObject = jsonArrayMunicipio.getJSONObject(i);
                        municipioID = jsonObject.optInt("id_municipio");
                        departamentoID = jsonObject.optInt("id_departamento");
                        municipioNombre = jsonObject.optString("nombre");
                        listaMunicipio.add(new MunicipioDepartamento(municipioID,departamentoID,municipioNombre));
                    }
                }catch (JSONException jex){
                    Toast.makeText(RegistroClienteActivity.this, jex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistroClienteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queueMunicipio.add(jsonMunicipioRequest);

        binding.spDepartamento.setOnItemSelectedListener(this);
        binding.spMunicipio.setOnItemSelectedListener(this);

        //GUARDAR REGISTRO
        binding.bttnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(RegistroClienteActivity.this)
                        .setIcon(R.drawable.ic_dialog_question_24)
                        .setTitle("GUARDAR REGISTRO")
                        .setMessage("¿Está segur@ que desea eliminar el item?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Guardar();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });

        //RAZON SOCIAL AUTOMÁTICA
        binding.etNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(binding.etNombre.length() > 0)
                {
                    binding.etRazonSocial.setText(binding.etNombre.getText().toString());
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == binding.spDepartamento.getId())
        {
            itemDepartamento = (DepartamentoPais) adapterView.getSelectedItem();
            ListarMunicipios(itemDepartamento.getIddepartamento());
        }
        else if(adapterView.getId() == binding.spMunicipio.getId())
        {
            itemMunicipio = (MunicipioDepartamento) adapterView.getSelectedItem();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void ListarMunicipios(int departamentoID){
        if (listaMunicipio.size() > 0)
        {
            listaMunicipioDepartamento.clear();
            int idMunicipio;
            int idDepartamento;
            String nombreDepartamento;

            for (int i = 0; i < listaMunicipio.size(); i++) {
                if(listaMunicipio.get(i).getId_departamento() == departamentoID)
                {
                    idMunicipio = listaMunicipio.get(i).getId_municipio();
                    idDepartamento = listaMunicipio.get(i).getId_departamento();
                    nombreDepartamento = listaMunicipio.get(i).getNombre();
                    listaMunicipioDepartamento.add(new MunicipioDepartamento(idMunicipio,idDepartamento,nombreDepartamento));
                }
            }

            if(listaMunicipioDepartamento.size() > 0)
            {
                adapterMunicipio = new ArrayAdapter<>(RegistroClienteActivity.this,
                        android.R.layout.simple_spinner_item, listaMunicipioDepartamento);
                adapterMunicipio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spMunicipio.setAdapter(adapterMunicipio);
            }
        }
    }

    private boolean ValidarControles()
    {
        boolean grabar = true;
        if (binding.etNit.length() == 0 && binding.etCUI.length() == 0)
        {
            binding.etNit.setError("Debe ingresar NIT o CUI");
            binding.etCUI.setError("Debe ingresar CUI o NIT");
            grabar = false;
        }
        if (binding.etNombre.length() == 0)
        {
            binding.etNombre.setError("Debe ingresar el nombre");
            grabar = false;
        }
        if(binding.etRazonSocial.length() == 0)
        {
            binding.etRazonSocial.setError("Debe ingresar la razón social");
            grabar = false;
        }
        if (binding.etDireccion.length() == 0)
        {
            binding.etDireccion.setError("Debe ingresar la dirección");
            grabar = false;
        }
        if (binding.etTelefono.length() == 0)
        {
            binding.etTelefono.setError("Debe ingresar # de teléfono");
            grabar = false;
        }
        if(binding.etTransporte.length() == 0) {
            binding.etTransporte.setError("Debe especificar transporte");
            grabar = false;
        }

        return grabar;
    }

    private void Guardar()
    {
        if(ValidarControles())
        {
            final JSONObject jsonCliente = new JSONObject();
            try {
                String nit = "CF";
                if (binding.etNit.length() > 0)
                {
                    nit = binding.etNit.getText().toString();
                }
                jsonCliente.put("nit",nit);
                jsonCliente.put("cui",binding.etCUI.getText().toString());
                jsonCliente.put("nombre",binding.etNombre.getText().toString());
                jsonCliente.put("direccion",binding.etDireccion.getText().toString());
                jsonCliente.put("telefono",binding.etTelefono.getText().toString());
                jsonCliente.put("email",binding.etCorreo.getText().toString());
                jsonCliente.put("comentario",binding.etObservaciones.getText().toString());
                jsonCliente.put("iddepartamento",itemDepartamento.getIddepartamento());
                jsonCliente.put("razonsocial",binding.etRazonSocial.getText().toString());
                jsonCliente.put("id_empleado", UsuarioBase.get().getUsuarioId());
                jsonCliente.put("id_municipio",itemMunicipio.getId_municipio());
                jsonCliente.put("region",binding.etRegion.getText().toString());
                jsonCliente.put("transporte",binding.etTransporte.getText().toString());
                jsonCliente.put("codigo_postal",itemDepartamento.getCodigo_postal());

                Toast.makeText(this, "municipio " + itemMunicipio.getId_municipio(), Toast.LENGTH_SHORT).show();

                RequestQueue queueCliente = Volley.newRequestQueue(RegistroClienteActivity.this);
                String URLRegistroCliente = getString(R.string.URL_RegistroCliente);
                StringRequest requestCliente = new StringRequest(Request.Method.POST, URLRegistroCliente, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            Integer codigo = jsonRespuesta.getInt("codigo");
                            if (codigo == 1) {
                                binding.etNit.setText("");
                                binding.etCUI.setText("");
                                binding.etNombre.setText("");
                                binding.etRazonSocial.setText("");
                                binding.etDireccion.setText("");
                                binding.etTelefono.setText("");
                                binding.etCorreo.setText("");
                                binding.etRegion.setText("");
                                binding .etTransporte.setText("");
                                binding.etObservaciones.setText("");
                            } else {
                                //Crear activity de error
                            }
                        } catch (JSONException jex) {
                            Toast.makeText(RegistroClienteActivity.this, jex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistroClienteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>parms=new HashMap<String, String>();
                        parms.put("registro_cliente",jsonCliente.toString());
                        return parms;
                    }
                };

                queueCliente.add(requestCliente);

            }catch (JSONException jex)
            {
                Toast.makeText(this, jex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            catch(Exception ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}