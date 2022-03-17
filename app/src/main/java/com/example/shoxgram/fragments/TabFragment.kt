package com.example.shoxgram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoxgram.R
import com.example.shoxgram.adapters.UserAdapter
import com.example.shoxgram.databinding.FragmentTabBinding
import com.example.shoxgram.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.StringBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [TabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var categoryID: Int? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryID = it.getInt(ARG_PARAM1)

        }
    }

    lateinit var binding: FragmentTabBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    private val TAG = "TabFragment"

    lateinit var userAdapter: UserAdapter
    var list = ArrayList<User>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTabBinding.inflate(layoutInflater,container,false)

        if (categoryID == 1) {
            //chat fragment
            firebaseAuth = FirebaseAuth.getInstance()
            val currentUser = firebaseAuth.currentUser
            firebaseDatabase = FirebaseDatabase.getInstance()
            reference = firebaseDatabase.getReference("users")

            val email = currentUser?.email
            val displayName = currentUser?.displayName
            val phoneNumber = currentUser?.phoneNumber
            val photoUrl = currentUser?.photoUrl
            val uid = currentUser?.uid

            val user = com.example.shoxgram.models.User(email,displayName,phoneNumber,photoUrl.toString(),uid!!)


            reference.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    val filterList = arrayListOf<User>()
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(User::class.java)
                        if (value != null && uid != value.uid){
                            list.add(value)
                        }
                        if (value!=null && value.uid == uid){
                            filterList.add(value)
                        }

                    }


                    if (filterList.isEmpty()) {
                        reference.child(uid).setValue(user)
                    }

                    userAdapter = UserAdapter(list, object :UserAdapter.OnItemClickListner{
                        override fun onItemClick(user: User) {
                            var bundle = Bundle()
                            bundle.putSerializable("key",user)
                            findNavController().navigate(R.id.profileFragment,bundle)
//                            val intent = Intent(this@RealActivity,MessageActivity::class.java)
//                            intent.putExtra("user", user)
//                            startActivity(intent)
                        }

                    })
                    binding.userRv.adapter = userAdapter
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TabFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(categoryID: Int) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, categoryID)

                }
            }
    }
}