package com.mym.pedidosdm.view

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mym.pedidosdm.R
import com.mym.pedidosdm.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //esta variable referencia al layout xml a través de binding
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var drawer:DrawerLayout
    private lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Se inicializa el binding para interactuar con las vistas del layout
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        toolbar.setTitle(R.string.title_app_mym);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.primaryTextColor))
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.primaryColor))
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,
        R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //colocal el icono de hamburguesa en el menú
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic__menu_24)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_item_cliente -> {
                startActivity(Intent(this,RegistroCliente::class.java))
            }
            R.id.nav_item_registroPedido -> {
                Toast.makeText(this, "Registro Pedido", Toast.LENGTH_SHORT).show()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
