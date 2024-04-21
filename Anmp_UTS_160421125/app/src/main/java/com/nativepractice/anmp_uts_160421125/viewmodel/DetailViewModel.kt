package com.nativepractice.anmp_uts_160421125.viewmodel

import android.content.Context
import android.graphics.ColorSpace.Model
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.nativepractice.anmp_uts_160421125.model.Hobby

class DetailViewModel: ViewModel() {
    val hobbyLD = MutableLiveData<Hobby>()
    fun fetch(hobbyId: String, context: Context) {
        val url = "http://10.0.2.2/gundam/detail.php?id=$hobbyId"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                    response ->
                val hobby = Gson().fromJson<Hobby>(response, Hobby::class.java)
                hobbyLD.postValue(hobby)
            },
            {
                    error->
                error.printStackTrace()
            })
        Volley.newRequestQueue(context).add(stringRequest)
    }
}