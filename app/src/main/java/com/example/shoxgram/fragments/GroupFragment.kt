package com.example.shoxgram.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoxgram.R
import com.example.shoxgram.adapters.GroupAdapter
import com.example.shoxgram.databinding.FragmentGroupBinding
import com.example.shoxgram.databinding.FragmentTabBinding
import com.example.shoxgram.databinding.MyDialogBinding
import com.example.shoxgram.models.Group
import com.example.shoxgram.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var token: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = it.getString(ARG_PARAM1)

        }
    }

    lateinit var binding: FragmentGroupBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var referenceChat: DatabaseReference
    private val TAG = "GroupFragment"

    lateinit var groupAdapter: GroupAdapter
    var list = ArrayList<Group>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupBinding.inflate(layoutInflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("groups")
        referenceChat = firebaseDatabase.getReference("users")



        val email = currentUser?.email
        val displayName = currentUser?.displayName
        val phoneNumber = currentUser?.phoneNumber
        val photoUrl = currentUser?.photoUrl
        val uid = currentUser?.uid
        val user = User(email, displayName, phoneNumber, photoUrl.toString(), uid,false,token)



        binding.addGroup.setOnClickListener {
            val alertDialog = AlertDialog.Builder(binding.root.context)
            val dialog = alertDialog.create()
            val dialogView =
                MyDialogBinding.inflate(LayoutInflater.from(binding.root.context), null, false)

            dialogView.saveText.setOnClickListener {
                val uid = currentUser?.uid
                val gr_name = dialogView.sarlavha.text.toString()

                buttonclick(gr_name)

                dialog.dismiss()
            }

            dialog.setView(dialogView.root)
            dialog.show()

        }

        reference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(Group::class.java)
                    if (value!=null){
                        list.add(value)
                    }
                }

                groupAdapter = GroupAdapter(list,object :GroupAdapter.OnItemClickListner{
                    override fun onItemClick(group: Group) {
                        var bundle = Bundle()
                        bundle.putSerializable("chat",group)
                        findNavController().navigate(R.id.chatFragment,bundle)
                    }


                })
                binding.smsRv.adapter = groupAdapter
                groupAdapter.notifyItemInserted(list.size)
                groupAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



        return binding.root
    }

    private fun buttonclick(grname:String) {
        val listUser = ArrayList<User>()
        referenceChat.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listUser.clear()
                val children = snapshot.children

                for (child in children) {
                    val value = child.getValue(User::class.java)
                    if (value != null) {
                        listUser.add(value)
                    }
                }

                val group = Group(grname, listUser)

                reference.child(grname).setValue(group)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(token: String) =
            GroupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, token)

                }
            }
    }
}