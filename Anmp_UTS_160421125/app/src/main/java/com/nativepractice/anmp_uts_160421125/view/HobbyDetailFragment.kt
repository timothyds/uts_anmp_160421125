package com.nativepractice.anmp_uts_160421125.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nativepractice.anmp_uts_160421125.R
import com.nativepractice.anmp_uts_160421125.databinding.FragmentHobbyDetailBinding
import com.nativepractice.anmp_uts_160421125.databinding.HobbyListItemBinding
import com.nativepractice.anmp_uts_160421125.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HobbyDetailFragment : Fragment() {
    private lateinit var viewModel:DetailViewModel
    private lateinit var binding: FragmentHobbyDetailBinding
    private var currentPage = 0
    private var totalPages = 0
    private lateinit var pages: List<String>
    fun observeHobby() {
        viewModel.hobbyLD.observe(viewLifecycleOwner, Observer {
            var hobby = it
            binding.txtTitleDetail.setText(viewModel.hobbyLD.value?.title)
            binding.txtUsernameDetail.setText(viewModel.hobbyLD.value?.username)
//            binding.txtDetail.setText(viewModel.hobbyLD.value?.detail)
            Picasso.get().load(hobby.photoUrl).into(binding.imgHobbyDetail)
            pages = hobby.detail?.split("\n") ?: emptyList()
            totalPages = pages.size

            // Menampilkan page pertama
            showPage(currentPage)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHobbyDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val hobbyId = requireArguments().getString("hobbyId")
        viewModel.fetch(hobbyId!!, requireContext())
        observeHobby()
        binding.btnBack.setOnClickListener {
            if(currentPage>0){
                currentPage--
                showPage(currentPage)
            }
        }
        binding.btnNext.setOnClickListener {
            if(currentPage < totalPages -1 ){
                currentPage++
                showPage(currentPage)
            }
        }
        return binding.root

    }
    private fun showPage(pageIndex: Int) {
        if (pageIndex >= 0 && pageIndex < totalPages) {
            binding.txtDetail.text = pages[pageIndex]
        }
    }
}