package com.example.ssolrangapplication.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.ssolrangapplication.R

class ProgressFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var msg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            msg = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.fragment_progress, null)
        arguments?.let {
            view.findViewById<TextView>(R.id.msg).text = it.getString(ARG_PARAM1)
        }

        val builder = AlertDialog.Builder(requireContext()).setView(view)
        val dialog: Dialog? = builder.create()
        dialog?.setCanceledOnTouchOutside(false)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog ?: throw IllegalAccessException("Activity cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        private const val ARG_PARAM1 = "msg"

    }
}