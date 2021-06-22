package com.example.ssolrangapplication.ui.dashboard

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
import com.example.ssolrangapplication.databinding.FragmentDashboardBinding
import org.koin.android.ext.android.bind

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentDashboardBinding>(inflater,R.layout.fragment_dashboard, container, false).apply{
            viewModel = dashboardViewModel
            lifecycleOwner = viewLifecycleOwner
        }



//        val root = inflater.inflate()
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return binding.root
    }
}