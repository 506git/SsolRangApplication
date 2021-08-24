package com.example.ssolrangapplication.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ssolrangapplication.common.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class FirebaseRepository() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth = Firebase.auth.currentUser
    init {
        if (!database.child("userInf").child(auth!!.uid).key.equals(auth.uid)) {
            val update: MutableMap<String, Any> = HashMap()
            update["category/${auth?.uid!!}/city/name"] = "도시"
            update["category/${auth?.uid!!}/city/count"] = "0"
            update["category/${auth?.uid!!}/rainy/name"] = "비"
            update["category/${auth?.uid!!}/rainy/count"] = "0"
            update["category/${auth?.uid!!}/rest/name"] = "휴식"
            update["category/${auth?.uid!!}/rest/count"] = "0"
            update["category/${auth?.uid!!}/nature/name"] = "자연"
            update["category/${auth?.uid!!}/nature/count"] = "0"

            database.updateChildren(update)
            val uid: String? = Firebase.auth.currentUser?.uid

            val user = User(
                Firebase.auth.currentUser?.displayName, Firebase.auth.currentUser?.uid,
                Firebase.auth.currentUser?.photoUrl.toString()
            )

            database.child("userInf").child(uid.toString()).setValue(user)
        }
    }
    fun getAll(): LiveData<MutableList<CategoryVO>>{
        val mutableData = MutableLiveData<MutableList<CategoryVO>>()
        val list: MutableList<CategoryVO> = mutableListOf<CategoryVO>()
        database.child("category").child(auth?.uid!!).get().addOnSuccessListener { dataSnapshot ->

            Log.d("TESTIT", dataSnapshot.key.toString() + " - "+ dataSnapshot.value)
            for (its in dataSnapshot.children ) {
                val data = CategoryVO(its.child("name").value.toString(), its.child("count").value.toString(),
                    its.key
                )
                Log.d("TESTITd", data.toString())
//                val getData: CategoryVO? = its.getValue(CategoryVO::class.java)
                list.add(data)
//                mutableData.value = list
            }
            mutableData.value = list.sortedWith(compareBy { it.count }) as MutableList<CategoryVO>
        }.addOnCanceledListener {

        }

        return mutableData
    }

    fun menuIncrement(menuId : String){
        val update: MutableMap<String, Any> = HashMap()
        Log.d("TESTex",menuId)
        update["category/${auth?.uid!!}/${menuId}/count"] = ServerValue.increment(1)
        database.updateChildren(update)
    }

}
