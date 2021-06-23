package com.example.ssolrangapplication.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ssolrangapplication.common.BaseViewModel
import com.example.ssolrangapplication.network.NetworkRepository

class HomeViewModel(private val repository: NetworkRepository) : BaseViewModel() {

//    private val test = ObservableField<String>("")
    private val _text = MutableLiveData<String>().apply {
        value = "Ttest"
    }
    val text: LiveData<String> = _text

}