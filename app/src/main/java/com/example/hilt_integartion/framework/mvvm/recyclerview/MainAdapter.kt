package com.example.hilt_integartion.framework.mvvm.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hilt_integartion.R
import com.example.hilt_integartion.framework.mvvm.data.model.User


class MainAdapter(
    private val context: Context,
    private val users: ArrayList<User>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
            val userName: TextView = itemView.findViewById(R.id.user_name)
            val userEmail: TextView = itemView.findViewById(R.id.user_email)

            userName.text = user.name
            userEmail.text = user.email

            Glide.with(imageView.context)
                .load(user.avatar)
                .into(imageView)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addData(list: List<User>) {
        users.addAll(list)
    }

}