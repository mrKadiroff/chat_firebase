package com.example.shoxgram.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoxgram.databinding.GroupItemBinding
import com.example.shoxgram.databinding.ItemChatBinding
import com.example.shoxgram.databinding.ItemFromBinding
import com.example.shoxgram.databinding.ItemToBinding
import com.example.shoxgram.models.Chat
import com.example.shoxgram.models.Group
import com.example.shoxgram.models.Message
import com.squareup.picasso.Picasso

class ChatAdapter (var list: List<Message>,var uid: String) :
    RecyclerView.Adapter<ChatAdapter.Vh>() {

    inner class Vh(var itemChatBinding: ItemChatBinding) :
        RecyclerView.ViewHolder(itemChatBinding.root) {

        fun onBind(message: Message) {
            itemChatBinding.messageTv.text = message.message
            itemChatBinding.nameTv.text = message.toUid
            Picasso.get().load(message.rasm).into(itemChatBinding.image1)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {

            return Vh(ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])

    }



    override fun getItemCount(): Int = list.size
}