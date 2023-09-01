package com.example.exoticshoesmovil

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtUser = findViewById(R.id.txtUsusario)
        txtPassword = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnVisible = findViewById(R.id.btnVisible)

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

    }

    private fun login(usuario: String,contraseña:String){
        val url = "http://127.0.0.1:8000/cliente/login/"

        val jsonObject = JSONObject()
        jsonObject.put("usuario", usuario)
        jsonObject.put("contraseña", contraseña)

        val  request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            object : Response.Listener<JSONObject>{
                override fun onResponse(response: JSONObject?) {
                    try {
                        val token = response?.getString("token")
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