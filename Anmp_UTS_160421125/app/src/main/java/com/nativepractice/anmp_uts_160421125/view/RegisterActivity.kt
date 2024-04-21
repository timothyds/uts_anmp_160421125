package com.nativepractice.anmp_uts_160421125.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nativepractice.anmp_uts_160421125.databinding.ActivityRegisterBinding
import org.json.JSONException
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        queue = Volley.newRequestQueue(this)
        binding.btnCreate.setOnClickListener {
            val username = binding.txtUsernameR.text.toString().trim()
            val password = binding.txtPasswordR.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                Log.d("DEBUG", "username: $username")
                Log.d("DEBUG", "username: $password")
                registerUser(username, password)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun registerUser(username: String, password: String) {
        val url = "http://192.168.1.4/gundam/cobaregister.php"

//        val jsonObject = JSONObject().apply {
//            put("username", username)
//            put("password", password)
//        }
        val postParams: MutableMap<Any, Any> = HashMap()
        postParams["username"] = username
        postParams["password"] = password

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(postParams as Map<*, *>?),
            Response.Listener { response ->
                try {
                    val success = response.getBoolean("success")
                    val message = response.getString("message")
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                    if (success) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
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
}