package com.example.ssolrangapplication.network

import com.example.ssolrangapplication.network.model.ListData
import com.google.gson.JsonParser
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import java.lang.Exception

class NetworkRepository(private val service: NetworkService) {

    private fun parseErrorResult(body: ResponseBody): NetworkResult.Error{
        try{
            val element = JsonParser().parse(body.string()).asJsonObject
            val code = if (element.has("code")) element.get("code").asString else null
            val message = if (element.has("message")) element.get("message").asString else null
            return NetworkResult.Error(code, message)
        } catch (e: Exception){

        }
        return NetworkResult.Error(null,null)
    }

    private suspend fun <T : Any> callResponse(call: suspend () -> retrofit2.Response<T>): NetworkResult<T> {
        val response = call.invoke()
        if (response.isSuccessful){
            return NetworkResult.Success(response.body())
        }else{
            response.errorBody()?.let{
                error -> return parseErrorResult(error)
            } ?: run{
                return NetworkResult.Error(null,null)
            }
        }
    }

    private suspend fun <T:Any> callArray(call: suspend () -> retrofit2.Response<List<T>>): List<T>{
        val response = call.invoke()
        return response.body() ?: emptyList()
    }

    private suspend fun <T:Any> callList(call:suspend () -> retrofit2.Response<ListData<T>>) : ListData<T> {
        val response = call.invoke()
        return response.body() ?: ListData()
    }


}