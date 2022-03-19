package com.example.shoxgram.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoxgram.databinding.FoydalanuvchiBinding
import com.example.shoxgram.databinding.GroupItemBinding
import com.example.shoxgram.models.Group
import com.example.shoxgram.models.User
import com.squareup.picasso.Picasso

class GroupAdapter (var list: List<Group>, var onItemClickListner: OnItemClickListner)
    : RecyclerView.Adapter<GroupAdapter.Vh>() {

    inner class Vh(var groupItemBinding: GroupItemBinding) :
        RecyclerView.ViewHolder(groupItemBinding.root) {

        fun onBind(group: Group) {
            groupItemBinding.groupname.text = group.gr_name
            groupItemBinding.root.setOnClickListener {
                onItemClickListner.onItemClick(group)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(GroupItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListner{
        fun onItemClick(group: Group)
    }
}