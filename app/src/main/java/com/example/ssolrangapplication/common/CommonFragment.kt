package com.example.ssolrangapplication.common

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class CommonFragment : Fragment() {

    init {

    }

    companion object {
        private lateinit var fragmentViewModel: CommonFragmentViewModel
        var progressFragment = ProgressFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentViewModel = ViewModelProvider(this).get(CommonFragmentViewModel::class.java)

        fragmentViewModel.visible.observe(this, Observer {
            if (it){
                try {
                    progressFragment.show(requireActivity().supportFragmentManager, "progress")
                } catch (e: WindowManager.BadTokenException) {
                    Log.e("error", e.message.toString())
                }
            } else {
                if (progressFragment.isAdded) {
                    try {
                        progressFragment.dismissAllowingStateLoss()
                    } catch (e: WindowManager.BadTokenException) {
                        // TODO: handle exception
                        Log.e("error", e.message.toString())
                    } catch (e: IllegalArgumentException) {
                        // TODO: handle exception
                        Log.e("error", e.message.toString())
                    }
                }
            }
        })
    }

    fun showProgress() {
        fragmentViewModel.progress(true)
    }

    fun hideProgress() {
        fragmentViewModel.progress(false)
    }

}