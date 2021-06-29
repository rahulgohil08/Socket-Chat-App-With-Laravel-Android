package com.theworld.socketApp.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theworld.socketApp.data.message.Message
import com.theworld.socketApp.data.user.User
import com.theworld.socketApp.databinding.LayoutUsersBinding

class UserAdapter(private val userInterface: UserInterface) :
    ListAdapter<User, UserAdapter.CustomerViewHolder>(DiffCallback()) {


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

                    userInterface.onClick(getItem(position))

                }
            }

        }


        fun bind(data: User) {

            binding.apply {
                name.text = "${data.name} (${data.mobileNo})"
            }
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(old: User, aNew: User) =
            old.id == aNew.id

        override fun areContentsTheSame(old: User, aNew: User) =
            old == aNew
    }


    interface UserInterface {
        fun onClick(user: User)
    }

}