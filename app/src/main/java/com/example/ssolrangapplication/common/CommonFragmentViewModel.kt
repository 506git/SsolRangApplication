package com.example.ssolrangapplication.common

import android.app.Application
import android.opengl.Visibility
import android.view.View
import android.view.View.GONE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ssolrangapplication.R

class CommonFragmentViewModel (application: Application): AndroidViewModel(application) {

    private val _visible = MutableLiveData<Boolean>().apply {
        value = false
    }

    val visible: LiveData<Boolean> = _visible


    fun progress(visible: Boolean){
        _visible.value = visible
    }
}