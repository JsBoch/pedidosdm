package com.mym.pedidosdm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mym.pedidosdm.controller.MYMOrdersApplication.Companion.prefs
import com.mym.pedidosdm.databinding.ActivityMainBinding
import com.mym.pedidosdm.model.UsuarioBase
import com.mym.pedidosdm.view.MainMenuActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    //esta variable referencia al layout xml a través de binding
    private lateinit var binding: ActivityMainBinding
    /*
    Estas variables se utilizan para manejar las referencias de usuario y password
    tanto para enviarlas como parametros al webservice de validación, como para guardar
    los datos en el SharedPreferences
    * */
    var user:String = ""
    var password:String = ""

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Se inicializa el binding para intefactuar con las vistas del layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*
        * se leen las preferencias del app almacenadas, que en este caso es el nombre de usuario
        * para que este no se tenga que escribir cada vez que se inicie sesión.
        * */
        getPreferences()

        binding.btMainAceptar.setOnClickListener {
            user = binding.etMainUsuario.text.toString()
            password = binding.etMainClave.text.toString()

            if(!user.isEmpty() && !password.isEmpty()) {
                validarUsuario(getString(R.string.URL_Login))
            }
            else
            {
                Toast.makeText(this, "Debe ingresar usuario y clave", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validarUsuario(URL:String)
    {
        /*
        * Administra los subprocesos de trabajo para ejecutar las operaciones de red, leer la caché,
        * escribir en ella y analizar las respuestas. Configura un requestqueue automáticamente
        * con valores predeterminados e inicia la cola.
        * */
        val queue = Volley.newRequestQueue(this)

        val stringRequest: StringRequest = object: StringRequest(
            Request.Method.POST,URL,
            Response.Listener<String>{
                    response ->
                if(!response.isEmpty()) {
                    val jsonObject = JSONObject(response);
                    val userID = jsonObject.get("id").toString()
                    val userName = jsonObject.get("nombre").toString()
                    if(userID.toInt() > 0) {
                        UsuarioBase.get().usuarioId =  userID.toInt()
                        UsuarioBase.get().usuarioNombre =  userName
                        savePreferences()
                        startActivity(Intent(this,MainMenuActivity::class.java))

                        finish()
                    }
                    else
                    {
                        Toast.makeText(this, "Usuario o clave incorrecto", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this, "Usuario o clave incorrecto", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            })
        {
            override fun getParams():Map<String,String>{
                val params: MutableMap<String,String> = HashMap()
                params["user_name"] = user
                params["user_password"] = password
                return params
            }
        }
        /*
         * se envía la solicitud (Request)
         * esta se puede procesar en la caché o en la red
         */
        queue.add(stringRequest)

    }

    private fun savePreferences(){
        prefs.saveName(user)
    }

    private fun getPreferences(){
        if(prefs.getName().isNotEmpty()) {
            binding.etMainUsuario.setText(prefs.getName())
        }
    }
}