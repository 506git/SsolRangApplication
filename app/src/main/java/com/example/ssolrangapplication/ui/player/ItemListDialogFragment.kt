package com.example.ssolrangapplication.ui.player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ssolrangapplication.R
import com.example.ssolrangapplication.common.CommonFragment

// TODO: Customize parameter argument names

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    ItemListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class ItemListDialogFragment : CommonFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TEST!!","EX")
        return inflater.inflate(R.layout.bottom_sheet_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        // TODO: Customize parameters
        fun newInstance(): ItemListDialogFragment = ItemListDialogFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d("TEST!!","EX")
    }
}