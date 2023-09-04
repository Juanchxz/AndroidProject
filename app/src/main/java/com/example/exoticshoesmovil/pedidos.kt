package com.example.exoticshoesmovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar

class pedidos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        val toolbar = findViewById<Toolbar>(R.id.pedidos_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = "Atras"

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed() // Simula el comportamiento del botón de retroceso estándar
        }

    }
    override fun onBackPressed() {
        // Puedes realizar acciones específicas aquí antes de regresar a la actividad anterior
        super.onBackPressed() // volver a la actividad anterior
    }
}