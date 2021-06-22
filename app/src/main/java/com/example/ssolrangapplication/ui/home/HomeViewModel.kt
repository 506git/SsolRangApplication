package com.example.ssolrangapplication.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ssolrangapplication.common.BaseViewModel

class HomeViewModel : ViewModel() {

//    private val test = ObservableField<String>("")
    private val _text = MutableLiveData<String>().apply {
        value = "Ttest"
    }
    val text: LiveData<String> = _text

}