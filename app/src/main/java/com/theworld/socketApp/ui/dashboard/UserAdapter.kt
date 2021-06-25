package com.theworld.socketApp.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theworld.socketApp.data.message.Message
import com.theworld.socketApp.databinding.LayoutUsersBinding

class UserAdapter() :
    ListAdapter<Message, UserAdapter.CustomerViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding =
            LayoutUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class CustomerViewHolder(private val binding: LayoutUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

//                    notificationInterface.onClick(getItem(position))

                }
            }

        }


        fun bind(data: Message) {

            binding.apply {
                name.text = data.message
            }
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(old: Message, aNew: Message) =
            old.id == aNew.id

        override fun areContentsTheSame(old: Message, aNew: Message) =
            old == aNew
    }


}