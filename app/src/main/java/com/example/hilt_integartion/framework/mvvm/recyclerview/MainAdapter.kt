package com.example.hilt_integartion.framework.mvvm.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hilt_integartion.databinding.ItemListBinding
import com.example.hilt_integartion.framework.mvvm.data.model.User


class MainAdapter(
    private val context: Context,
    private val users: ArrayList<User>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.userName.text = user.name
            binding.userEmail.text = user.email
            Glide.with(binding.imageView.context)
                .load(user.avatar)
                .into(binding.imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addData(list: List<User>) {
        users.addAll(list)
    }

}