package com.mym.pedidosdm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mym.pedidosdm.R
import com.mym.pedidosdm.databinding.ActivityMainBinding
import com.mym.pedidosdm.databinding.ActivityMainMenuBinding
import com.mym.pedidosdm.databinding.ActivityRegistroClienteBinding

class RegistroCliente : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Código utilizado para colocar el navigationbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        toolbar.setTitle("REGISTRO DE CLIENTES");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.primaryTextColor))
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.primaryColor))
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_mym_24)
        /* *************************************************************** */
    }
}