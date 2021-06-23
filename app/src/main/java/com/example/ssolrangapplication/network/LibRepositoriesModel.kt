package com.example.ssolrangapplication.network

import com.google.gson.annotations.SerializedName

data class LibRepositoriesModel(
        @SerializedName("Result")
        val Result: LibResultModel,
        @SerializedName("Contents")
        val Contents: LibContentModel
)

data class LibResultModel(
        @SerializedName("DebugMessage")
        val DebugMessage: String,

        @SerializedName("ResultCode")
        val ResultCode: String,

        @SerializedName("ResultMessage")
        var ResultMessage: String
)

data class LibContentModel(
        @SerializedName("UserLibraryInfoList")
        val UserLibraryInfoList: List<LibListModel>
)

data class LibListModel(
        @SerializedName("LibraryName")
        val LibraryName: String,

        @SerializedName("LibraryCode")
        val LibraryCode: String
)