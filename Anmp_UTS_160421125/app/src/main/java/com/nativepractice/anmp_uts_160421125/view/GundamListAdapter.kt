package com.nativepractice.anmp_uts_160421125.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nativepractice.anmp_uts_160421125.databinding.HobbyListItemBinding
import com.nativepractice.anmp_uts_160421125.model.Hobby
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class GundamListAdapter (val hobbyList:ArrayList<Hobby>) :RecyclerView.Adapter<GundamListAdapter.HobbyViewHolder>() {
    class HobbyViewHolder(var binding: HobbyListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val binding = HobbyListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HobbyViewHolder(binding)
    }

    fun updateHobbyList(newHobbyList: ArrayList<Hobby>) {
        hobbyList.clear()
        hobbyList.addAll(newHobbyList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return hobbyList.size
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        holder.binding.txtTitle.text = hobbyList[position].title
        holder.binding.txtUsername.text = hobbyList[position].username
        holder.binding.txtDesc.text = hobbyList[position].desc
        val picasso = Picasso.Builder(holder.itemView.context)
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(hobbyList[position].photoUrl).into(holder.binding.imgHobby)
        picasso.build().load(hobbyList[position].photoUrl)
            .into(holder.binding.imgHobby, object : Callback {
                override fun onSuccess() {
                    holder.binding.progressImage.visibility = View.INVISIBLE
                    holder.binding.imgHobby.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString())
                }
            })
        holder.binding.btnRead.setOnClickListener {
            val hobbyId = hobbyList[position].id
            val action = HobbyListFragmentDirections.actionHobbyDetail(hobbyId.toString())
            Navigation.findNavController(it).navigate(action)
        }
    }
}