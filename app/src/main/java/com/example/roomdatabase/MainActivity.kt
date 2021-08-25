package com.example.roomdatabase

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.database.SubscriberDatabase
import com.example.roomdatabase.database.SubscriberEntity
import com.example.roomdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Global Variables
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: SubscriberViewModel
    lateinit var adapter: SubscriberAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getDatabaseInstance(application).getSubscriberDao
        val repo = SubscriberRepository(dao)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandledOrReturnNull()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = SubscriberAdapter { selectedItem: SubscriberEntity ->
            itemClickListener(
                selectedItem
            )
        }
        binding.recyclerview.adapter = adapter
        displayAllSubscriber()
    }

    private fun displayAllSubscriber() {
        viewModel.allSubscriber.observe(this, {
            adapter.setNewList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun itemClickListener(subscriberEntity: SubscriberEntity) {
        viewModel.initUpdateAndDelete(subscriberEntity)
    }
}