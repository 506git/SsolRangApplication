package com.example.ssolrangapplication.ui.home

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ssolrangapplication.common.BaseViewModel
import com.example.ssolrangapplication.network.NetworkRepository

class HomeViewModel() : BaseViewModel() {

    //    private val test = ObservableField<String>("")
    private val repository = FirebaseRepository()
    private val category = repository.getAll()

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    fun getAll(): LiveData<MutableList<CategoryVO>> {
        return this.category
    }

    fun increment(menuId: String){
        repository.menuIncrement(menuId = menuId)
    }

}