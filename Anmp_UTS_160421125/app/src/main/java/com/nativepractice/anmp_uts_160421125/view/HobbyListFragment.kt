package com.nativepractice.anmp_uts_160421125.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativepractice.anmp_uts_160421125.R
import com.nativepractice.anmp_uts_160421125.databinding.FragmentHobbyListBinding
import com.nativepractice.anmp_uts_160421125.viewmodel.ListViewModel

class HobbyListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val gundamListAdapter = GundamListAdapter(arrayListOf())
    private lateinit var binding: FragmentHobbyListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHobbyListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = gundamListAdapter
        observeViewModel()

    }
    fun observeViewModel() {
        viewModel.hobbyLD.observe(viewLifecycleOwner, Observer {
            gundamListAdapter.updateHobbyList(it)
        })
        viewModel.hobbyLoadErrorLD.observe(viewLifecycleOwner, Observer {if(it == true) {
            binding.txtError?.visibility = View.VISIBLE
        } else {
            binding.txtError?.visibility = View.GONE
        }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {if(it == true) {
            binding.recyclerView.visibility = View.GONE
            binding.progressImage.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.progressImage.visibility = View.GONE
        }
        })


    }
}