package com.example.ssolrangapplication

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ssolrangapplication.common.model.UserModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val _title = MutableLiveData<String>().apply {
        value = application.getString(R.string.title_home)
    }


    private val _user = MutableLiveData<UserModel>().apply {

    }

    private val _bottomState = MutableLiveData<Int>().apply{
        value = BottomSheetBehavior.STATE_HIDDEN
    }

    val title:LiveData<String> = _title

    val user:LiveData<UserModel> = _user

    val bottomState:LiveData<Int> = _bottomState
    fun titleChange(menu: String){
        _title.value = menu
    }

    fun setState(bottomState: Int){
        _bottomState.value = bottomState
    }

    fun setUser(name: String, id: String, photoUrl: String){
        _user.value = UserModel(name,id,photoUrl)
    }
}