package com.example.shoxgram.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoxgram.databinding.GroupItemBinding
import com.example.shoxgram.databinding.ItemFromBinding
import com.example.shoxgram.databinding.ItemToBinding
import com.example.shoxgram.models.Chat
import com.example.shoxgram.models.Group
import com.example.shoxgram.models.Message

class ChatAdapter (var list: List<Message>,var uid: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class FromVH(var itemFromBinding: ItemFromBinding) :
        RecyclerView.ViewHolder(itemFromBinding.root) {

        fun onBind(message: Message) {
            itemFromBinding.messageTv.text = message.message


        }
    }

    inner class ToVH(var itemToBinding: ItemToBinding) :
        RecyclerView.ViewHolder(itemToBinding.root) {

        fun onBind(message: Message) {
            itemToBinding.messageTv.text = message.message

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return FromVH(
                ItemFromBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ToVH(
                ItemToBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            val fromVH = holder as FromVH
            fromVH.onBind(list[position])
        } else {
            val toVH = holder as ToVH
            toVH.onBind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].fromUid == uid) {
            1
        } else {
            2
        }
    }

    override fun getItemCount(): Int = list.size
}