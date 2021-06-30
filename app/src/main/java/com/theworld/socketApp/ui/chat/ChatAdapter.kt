package com.theworld.socketApp.ui.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrsports.cricketstreaming.utils.Constants
import com.hrsports.cricketstreaming.utils.SharedPrefManager
import com.hrsports.cricketstreaming.utils.getUserId
import com.theworld.socketApp.data.message.Message
import com.theworld.socketApp.databinding.ChatItemLeftBinding
import com.theworld.socketApp.databinding.ChatItemRightBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ChatAdapter(private val context: Context) :
    ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        private const val TAG = "ChatAdapter"
        private const val left_side = 1
        private const val right_side = 0
    }


    override fun getItemViewType(position: Int): Int {

        return if (getItem(position).senderId == context.getUserId()) {
            right_side
        } else {
            left_side
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        Log.d(TAG, "onCreateViewHolder: ${context.getUserId()}")

        return if (viewType == left_side) {
            val binding =
                ChatItemLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            LeftViewHolder(binding)

        } else {

            val binding =
                ChatItemRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            RightViewHolder(binding)
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentItem = getItem(position)

        if (getItemViewType(position) == left_side) {
            val leftViewHolder = holder as LeftViewHolder
            leftViewHolder.bind(currentItem)
        } else {

            val rightViewHolder = holder as RightViewHolder
            rightViewHolder.bind(currentItem)
        }

    }


    /*----------------------------- Left View Holder -------------------------------*/

    inner class LeftViewHolder(private val binding: ChatItemLeftBinding) :
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
                tvShowMsg.text = data.message
                tvTime.text = data.dateFormatted
            }
        }


    }


    /*----------------------------- Right View Holder -------------------------------*/

    inner class RightViewHolder(private val binding: ChatItemRightBinding) :
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
                tvShowMsg.text = data.message
                tvTime.text = data.dateFormatted

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