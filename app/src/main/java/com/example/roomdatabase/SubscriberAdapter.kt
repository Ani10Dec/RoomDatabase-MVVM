package com.example.roomdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.database.SubscriberEntity
import com.example.roomdatabase.databinding.ItemSubscriberBinding

class SubscriberAdapter(
    private val clickListener: (SubscriberEntity) -> Unit
) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val subscriberList = ArrayList<SubscriberEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemSubscriberBinding>(
                layoutInflater,
                R.layout.item_subscriber,
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriberList[position], clickListener)

    }

    fun setNewList(subscriberEntity: List<SubscriberEntity>) {
        subscriberList.clear()
        subscriberList.addAll(subscriberEntity)
    }

    override fun getItemCount(): Int {
        return subscriberList.size
    }

}

class MyViewHolder(private val binding: ItemSubscriberBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriberEntity: SubscriberEntity, clickListener: (SubscriberEntity) -> Unit) {
        binding.tvName.text = subscriberEntity.name
        binding.tvEmail.text = subscriberEntity.email
        binding.listItem.setOnClickListener {
            clickListener(subscriberEntity)
        }
    }

}