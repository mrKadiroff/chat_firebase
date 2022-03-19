package com.example.shoxgram.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoxgram.databinding.FoydalanuvchiBinding
import com.example.shoxgram.models.Message
import com.example.shoxgram.models.Recently
import com.example.shoxgram.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.lang.Exception

class UserAdapter (var list: List<User>, val database: FirebaseDatabase, val currentUserUid: String,
                   var onItemClickListner: OnItemClickListner)
    : RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var foydalanuvchiBinding: FoydalanuvchiBinding) :
        RecyclerView.ViewHolder(foydalanuvchiBinding.root) {





        fun onBind(user: User) {
            Picasso.get().load(user.photoUrl).into(foydalanuvchiBinding.image1)
            foydalanuvchiBinding.namee.text = user.displayName


            if (user.online == true){
                foydalanuvchiBinding.online.visibility = View.VISIBLE
            }else{
                foydalanuvchiBinding.online.visibility = View.GONE
            }

            foydalanuvchiBinding.root.setOnClickListener {
                onItemClickListner.onItemClick(user)
            }

            for (i in 0 until list.size) {
                val reference = database.getReference("users")
                reference.child("${currentUserUid}/messages/${user.uid}")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val messagesss = arrayListOf<Message>()
                            val messages = snapshot.children
                            try {
                                val last = messages.last().getValue(Message::class.java)
                               foydalanuvchiBinding.messagee.text = last!!.message
                                foydalanuvchiBinding.timee.text = last!!.date

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }











//                        for (child in children) {
//                            val value = child.getValue(Message::class.java)
////                            Log.d(TAG, "onDataChange: ${value!!.message!!.last()}")
//                        }


                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
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