package com.example.ssolrangapplication.common.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.ssolrangapplication.R
import kotlinx.android.synthetic.main.bottom_sheet_player.view.*

class CustomFragmentManger(_activity: FragmentActivity) {
    init {
        activity = _activity
        manager = activity.supportFragmentManager
    }
    companion object{
        lateinit var activity : FragmentActivity
        lateinit var manager : FragmentManager
    }

    fun remove() {
        if (manager.backStackEntryCount > 0) {
            manager.popBackStack()
        }
    }

    fun addFragment(fragment: Fragment, tag : String) {
        val transaction = manager.beginTransaction()
        transaction.add(R.id.fragment_container,fragment, tag).commitAllowingStateLoss()
        manager.executePendingTransactions()
    }

    fun addFragmentOnMain(fragment: Fragment, tag : String){
        val transaction = manager.beginTransaction()
        for (i in manager.backStackEntryCount downTo 1){
            manager.popBackStack()
        }
        transaction.add(R.id.fragment_container,fragment,tag).commitAllowingStateLoss()
        manager.executePendingTransactions()

    }
}