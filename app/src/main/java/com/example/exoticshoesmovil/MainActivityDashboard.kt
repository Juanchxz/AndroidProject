package com.example.exoticshoesmovil

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class MainActivityDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        val token = intent.getStringExtra("token")
        Toast.makeText(this, "token de sesion: $token", Toast.LENGTH_SHORT).show()
        val toolbar = findViewById<Toolbar>(R.id.dashboard_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Dashboard"

        val cardView = findViewById<CardView>(R.id.cardPedidos)

        cardView.setOnClickListener {
            // Intent para abrir la nueva actividad
            val intent = Intent(this, pedidos::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                // Código para cerrar sesión, y eliminae el token o datos de sesión
                val sharedPreferences = getSharedPreferences("tokenSesion", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.remove("token")
                editor.apply()
                // Luego, inicia la actividad de inicio de sesión
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Cierra la actividad actual
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }





}