package com.example.ssolrangapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssolrangapplication.common.CommonFragment
import com.example.ssolrangapplication.R
import com.example.ssolrangapplication.databinding.FragmentHomeBinding
import com.google.firebase.database.DatabaseReference

class HomeFragment : CommonFragment() {

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
                showProgress()
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

        homeViewModel.getAll().observe(viewLifecycleOwner, Observer<List<CategoryVO>>{ category ->
            adapter.setCategory(category!!)
            hideProgress()
        })

        return mBinding?.root
    }
}