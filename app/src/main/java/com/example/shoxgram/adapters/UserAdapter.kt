package com.example.shoxgram.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoxgram.databinding.FoydalanuvchiBinding
import com.example.shoxgram.models.User
import com.squareup.picasso.Picasso

class UserAdapter (var list: List<User>, var onItemClickListner: OnItemClickListner)
    : RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var foydalanuvchiBinding: FoydalanuvchiBinding) :
        RecyclerView.ViewHolder(foydalanuvchiBinding.root) {

        fun onBind(user: User) {
            Picasso.get().load(user.photoUrl).into(foydalanuvchiBinding.image1)
            foydalanuvchiBinding.namee.text = user.displayName

            foydalanuvchiBinding.root.setOnClickListener {
                onItemClickListner.onItemClick(user)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(FoydalanuvchiBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListner{
        fun onItemClick(user: User)
    }
}