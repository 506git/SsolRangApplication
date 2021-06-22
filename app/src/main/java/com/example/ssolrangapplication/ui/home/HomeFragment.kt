package com.example.ssolrangapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ssolrangapplication.R
import com.example.ssolrangapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel /*by viewModel()*/
    private var mBinding: FragmentHomeBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java).apply {
            }

        mBinding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,R.layout.fragment_home, container, false).apply {
            viewModel=homeViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return mBinding?.root
    }
}