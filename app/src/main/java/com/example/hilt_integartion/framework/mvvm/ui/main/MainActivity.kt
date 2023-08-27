package com.example.hilt_integartion.framework.mvvm.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hilt_integartion.R
import com.example.hilt_integartion.databinding.ActivityMainBinding
import com.example.hilt_integartion.framework.mvvm.data.model.User
import com.example.hilt_integartion.framework.mvvm.recyclerview.MainAdapter
import com.example.hilt_integartion.framework.mvvm.recyclerview.MainViewModel
import com.example.hilt_integartion.framework.mvvm.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val user = ArrayList<User>()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        mainBinding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = MainAdapter(this, user)
        mainBinding.recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this) {

            when (it.status) {

                Status.SUCCESS -> {
                    mainBinding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    mainBinding.recyclerView.visibility = View.VISIBLE
                }

                Status.LOADING -> {
                    mainBinding.progressBar.visibility = View.VISIBLE
                    mainBinding.recyclerView.visibility = View.GONE
                }

                Status.ERROR -> {
                    mainBinding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

}