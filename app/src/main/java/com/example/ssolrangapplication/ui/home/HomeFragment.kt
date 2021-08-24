package com.example.ssolrangapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ssolrangapplication.R
import com.example.ssolrangapplication.common.model.User
import com.example.ssolrangapplication.databinding.FragmentHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel /*by viewModel()*/
    private var mBinding: FragmentHomeBinding?= null
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProvider(this,).get(HomeViewModel::class.java).apply {
            }

        mBinding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,R.layout.fragment_home, container, false).apply {
            viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val categoryList = mBinding?.categoryList
        val lm = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        val adapter = CategoryAdapter { category ->
            Toast.makeText(activity, category.name, Toast.LENGTH_LONG).show()
            homeViewModel.increment(category.menuId!!)
        }
        categoryList?.apply {
            setAdapter(adapter)
            layoutManager = lm
            setHasFixedSize(true)
        }

        homeViewModel.getAll().observe(viewLifecycleOwner, Observer<List<CategoryVO>>{
            category -> adapter.setCategory(category!!)
        })
//
//        database = FirebaseDatabase.getInstance().reference
//        val uid : String? = Firebase.auth.currentUser?.uid
//
//        val user = User(Firebase.auth.currentUser?.displayName, Firebase.auth.currentUser?.uid,
//            Firebase.auth.currentUser?.photoUrl.toString()
//        )
//
//        database.child("userInf").child(uid.toString()).setValue(user)
//
//        val update: MutableMap<String, Any> = HashMap()
//        update["category/$uid/city"] = ServerValue.increment(1)
//        update["category/$uid/nature"] = ServerValue.increment(0)
//        update["category/$uid/rest"] = ServerValue.increment(0)
//        update["category/$uid/rain"] = ServerValue.increment(0)
//        FirebaseDatabase.getInstance().reference.updateChildren(update)

        return mBinding?.root
    }
}