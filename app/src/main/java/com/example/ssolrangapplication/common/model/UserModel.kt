package com.example.ssolrangapplication.common.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("profileUrl")
    val profile: String
) : Serializable