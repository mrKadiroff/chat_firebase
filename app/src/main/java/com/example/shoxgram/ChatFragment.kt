package com.example.shoxgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoxgram.adapters.ChatAdapter
import com.example.shoxgram.adapters.MessageAdapter
import com.example.shoxgram.databinding.FragmentChatBinding
import com.example.shoxgram.models.Chat
import com.example.shoxgram.models.Group
import com.example.shoxgram.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var binding: FragmentChatBinding
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    private val TAG = "ChatFragment"
    private var key: String? = null
    var list = ArrayList<Chat>()
    lateinit var chatAdapter: ChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(layoutInflater,container,false)
        val groupcha = arguments?.getSerializable("chat") as Group

        binding.namee.text = groupcha.gr_name

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("groups")


        binding.sendBtn.setOnClickListener {
            val m = binding.sms.text.toString().trim()
            val simpleDateFormat = SimpleDateFormat("HH.mm")
            val date = simpleDateFormat.format(Date())
            val message = Message(m, date, firebaseAuth.currentUser?.uid, firebaseAuth.currentUser?.displayName)

            if (m.isNotEmpty()) {
                for (i in 0 until groupcha?.users!!.size) {
                    val key = reference.push().key
                    reference.child("${groupcha?.gr_name!!}/users/$i/messages/$key")
                        .setValue(message)
                }
            }
            binding.sms.setText("")
        }


        for (i in 0 until groupcha?.users!!.size) {

//            if (firebaseAuth.currentUser?.uid == groupcha.users!![i].uid) {
                reference.child("${groupcha?.gr_name}/users/$i/messages")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val list = ArrayList<Message>()
                            val children = snapshot.children
                            for (child in children) {
                                val value = child.getValue(Message::class.java)
                                if (value != null) {
                                    list.add(value)
                                }
                            }

                            chatAdapter = ChatAdapter(list,groupcha?.users!![i].uid!!)
                binding.chatmessageRv.adapter = chatAdapter
                            binding.chatmessageRv.scrollToPosition(list.size -1)

                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
//            }
        }

































//        binding.sendBtn.setOnClickListener {
//            val m = binding.sms.text.toString()
//            key = reference.push().key
//            val chat = Chat(m,firebaseAuth.currentUser!!.uid,groupcha.gr_name)
//
//
//            reference.child("${groupcha.gr_name}/$key")
//                .setValue(chat)
//
////            reference.child("${usercha.uid}/messages/${firebaseAuth.currentUser!!.uid}/$key")
////                .setValue(message)
//            binding.sms.setText("")
//
//        }
//
//
//        reference.child("${groupcha.gr_name}/$key")
//            .addValueEventListener(object :ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    list.clear()
//                val children = snapshot.children
//                for (child in children) {
//                    val value = child.getValue(Chat::class.java)
//                    Log.d(TAG, "onDataChange: ${value}")
//                    if (value!=null){
//                        list.add(value)
//                    }
//                }
//
//                chatAdapter = ChatAdapter(list)
//                binding.chatmessageRv.adapter = chatAdapter
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//
//            })



//        reference.addValueEventListener(object:ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                list.clear()
//                val children = snapshot.children
//                for (child in children) {
//                    val value = child.getValue(Chat::class.java)
//                    if (value!=null){
//                        list.add(value)
//                    }
//                }
//
//                chatAdapter = ChatAdapter(list)
//                binding.chatmessageRv.adapter = chatAdapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}