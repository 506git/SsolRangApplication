package com.example.ssolrangapplication.ui.home

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class CategoryVO(
    var name : String? = "",
    var count : String? = "",
    var menuId : String?
)
@IgnoreExtraProperties
data class CategoryIdVO(
    var nature : List<CategoryVO>,
    var rain : List<CategoryVO>,
    var city : List<CategoryVO>,
    var rest : List<CategoryVO>
)