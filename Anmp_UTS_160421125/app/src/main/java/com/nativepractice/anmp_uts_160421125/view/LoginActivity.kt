package com.nativepractice.anmp_uts_160421125.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nativepractice.anmp_uts_160421125.R
import com.nativepractice.anmp_uts_160421125.databinding.ActivityLoginBinding
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtCreate.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        queue = Volley.newRequestQueue(this@LoginActivity)
        binding.btnSignIn.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            if(username.isEmpty() && password.isEmpty()){
                Toast.makeText(this,"Username dan password tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else{
                login(username,password)
            }
        }

    }
    private fun login(username: String, password: String) {
        val url = "http://192.168.1.4/gundam/login2.php?username=$username&password=$password"

        val jsonObject = JSONObject().apply {
            put("username", username)
            put("password", password)
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                try {
                    val success = response.getBoolean("success")
                    if (success) {

                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        val loggedInUsername = response.getString("username")
                        val loggedInPassword = response.getString("password")
                        saveLoginInformation(loggedInUsername,loggedInPassword)
                        finish()
                    } else {
                        val message = response.getString("message")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "JSON Error: " + e.message, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Log.e("VolleyError", "Volley Error: ${error.message}")
                Toast.makeText(this, "Volley Error: " + error.message, Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonObjectRequest)
    }
    private fun saveLoginInformation(username: String,password: String) {
        val sharedPref = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("username", username)
            putString("password",password)
            apply()
        }
    }
}