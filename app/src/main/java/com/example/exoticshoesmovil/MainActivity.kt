package com.example.exoticshoesmovil

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var txtUser:EditText
    private lateinit var txtPassword:EditText
    private lateinit var btnVisible:ToggleButton
    private lateinit var btnLogin :Button
    private lateinit var cRemember:CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token != null) {
            // El token está presente, navega a la actividad de dashboard
            val intent = Intent(this, MainActivityDashboard::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual para que el usuario no pueda volver atrás
        }

        txtUser = findViewById(R.id.txtUsusario)
        txtPassword = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnVisible = findViewById(R.id.btnVisible)
        cRemember = findViewById(R.id.checkBoxRememberMe)

        btnVisible.setOnClickListener{
            if (btnVisible.isChecked){
                txtPassword.inputType=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }else{
                txtPassword.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            txtPassword.setSelection(txtPassword.text.length)
        }

        val loginButton = findViewById<Button>(R.id.btnLogin)
        loginButton.setOnClickListener{
            val usuario = txtUser.text.toString()
            val contraseña = txtPassword.text.toString()

            login(usuario,contraseña)
        }

        val rememberMeChecked = sharedPreferences.getBoolean("remember_me", false)
        cRemember.isChecked = rememberMeChecked

    }

    private fun login(usuario: String,contraseña:String){
        val url = "https://exoticshoes.pythonanywhere.com/inicioSesion/"

        val jsonObject = JSONObject()
        jsonObject.put("username", usuario)
        jsonObject.put("password", contraseña)

        val  request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            object : Response.Listener<JSONObject>{
                override fun onResponse(response: JSONObject?) {
                    try {
                        val token = response?.getString("token")
                        val mensaje = response?.getString("message")
                        if (token != null) {
                            val intent = Intent(this@MainActivity, MainActivityDashboard::class.java)
                            // Puedes pasar datos extras si los necesitas
                            intent.putExtra("token", token)
                            val sharedPreferences = getSharedPreferences("tokenSesion", Context.MODE_PRIVATE)
                            val rememberMeChecked = cRemember.isChecked
                            val editor = sharedPreferences.edit()
                            editor.putString("token", token)
                            editor.putBoolean("remember_me", rememberMeChecked)
                            editor.apply()
                            startActivity(intent)
                            finish() // Esto cerrará la actividad actual para que el usuario no pueda volver atrás con el botón de retroceso
                        }
                        Toast.makeText(this@MainActivity, "Mensaje: $mensaje", Toast.LENGTH_LONG).show()
                    } catch (e:JSONException){
                        e.printStackTrace()
                        Toast.makeText(this@MainActivity, "Error de incio de sesion", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e("MainActivity", "Error Inicio de Sesión")
                    Toast.makeText(this@MainActivity, "Error inicio de Sesion", Toast.LENGTH_SHORT).show()
                }
            })

        Volley.newRequestQueue(this).add(request)
    }
}