package com.nativepractice.anmp_uts_160421125.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nativepractice.anmp_uts_160421125.R
import com.nativepractice.anmp_uts_160421125.databinding.FragmentProfileBinding
import org.json.JSONException
import org.json.JSONObject

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val currUser = sharedPreferences.getString("username", "")
        val currPass = sharedPreferences.getString("password","")
        binding.txtUsernameP.setText(currUser)
        binding.txtPasswordP.setText(currPass)
        binding.btnUbah.setOnClickListener {
            val currentUsername = currUser?:""
            val currentPassword = currPass?:""
            val newUsername = binding.txtUsernameP.text.toString().trim()
            val newPassword = binding.txtPasswordP.text.toString().trim()
            Log.d("DEBUG", "newUsername: $newUsername")
            Log.d("DEBUG", "newPassword: $newPassword")
            Log.d("DEBUG", "currentUsername: $currentUsername")
            Log.d("DEBUG", "currentPassword: $currentPassword")

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(context, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                updateData(currentUsername, currentPassword, newUsername, newPassword)
            }
        }
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }
    private fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
    private fun updateData(currentUsername: String,
                           currentPassword: String,
                           newUsername: String,
                           newPassword: String) {
        val url = "http://192.168.1.4/gundam/updateProfil.php"

        val jsonObject =JSONObject().apply {
            put("username", currentUsername)
            put("password", currentPassword)
            put("new_username", newUsername)
            put("new_password", newPassword)
        }


        val requestQueue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                try {
                    val success = response.getBoolean("success")
                    val message = response.getString("message")
                    if (success) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "JSON Error: " + e.message, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                error.printStackTrace()
                Toast.makeText(requireContext(), "Volley Error: " + error.message, Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(jsonObjectRequest)
    }

}