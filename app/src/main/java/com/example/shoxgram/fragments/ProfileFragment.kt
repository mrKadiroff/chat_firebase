package com.example.shoxgram.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shoxgram.R
import com.example.shoxgram.Retrofit.*
import com.example.shoxgram.adapters.MessageAdapter
import com.example.shoxgram.databinding.FragmentProfileBinding
import com.example.shoxgram.databinding.FragmentTabBinding
import com.example.shoxgram.models.Message
import com.example.shoxgram.models.Recently
import com.example.shoxgram.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
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

    lateinit var binding: FragmentProfileBinding
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var messageAdapter: MessageAdapter

    lateinit var apiService: ApiService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")


        apiService =
            ApiClient.getRetrofit("https://fcm.googleapis.com/").create(ApiService::class.java)



        val usercha = arguments?.getSerializable("key") as User


        binding.back.setOnClickListener{
            findNavController().popBackStack()
        }

        Picasso.get().load(usercha.photoUrl).into(binding.image1)
        binding.namee.text = usercha.displayName
        if (usercha.online == true){
            binding.messagee.text = "Online"

        }else{
            binding.messagee.text = "Offline"
            binding.messagee.setTextColor(R.color.black!!)
        }

        binding.sendBtn.setOnClickListener {
            val m = binding.sms.text.toString()
            val simpleDateFormat = SimpleDateFormat("HH:mm")
            val date = simpleDateFormat.format(Date())
            val message = Message(m,date, firebaseAuth.currentUser!!.uid, usercha.uid,usercha.photoUrl)

            val key = reference.push().key
            reference.child("${firebaseAuth.currentUser!!.uid}/messages/${usercha.uid}/$key")
                .setValue(message)

            reference.child("${usercha.uid}/messages/${firebaseAuth.currentUser!!.uid}/$key")
                .setValue(message)
            binding.sms.setText("")


            apiService.sendNotification(
                Sender(
                    Data(
                        firebaseAuth.currentUser!!.uid,
                        R.drawable.ic_launcher_foreground,
                        m,
                        "New Message",
                        usercha.uid ?:""
                    ),
                    usercha.token ?:""
                )
            )
                .enqueue(object : Callback<MyResponce> {
                    override fun onResponse(
                        call: Call<MyResponce>,
                        response: Response<MyResponce>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(binding.root.context, "Success", Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<MyResponce>, t: Throwable) {

                    }

                })




        }

        reference.child("${firebaseAuth.currentUser!!.uid}/messages/${usercha.uid}")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<Message>()
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(Message::class.java)
                        if (value != null) {
                            list.add(value)
                        }
                    }
                    messageAdapter = MessageAdapter(list,firebaseAuth.currentUser!!.uid)
                    binding.messageRv.adapter = messageAdapter
                    binding.messageRv.scrollToPosition(list.size -1)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}