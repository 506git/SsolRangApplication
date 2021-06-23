package com.example.ssolrangapplication.network.model

data class ListData<T>(var items:List<out T> = listOf(), val rowCount: Int = 0)