package com.example.ssolrangapplication.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    //imageSearch
    @GET("requestService.jsp?serviceName=MB_01_01_01_SERVICE")
    suspend fun getRepositories(
            @Query("userId")
            userId: String,
            @Query("encryptFromId")
            encryptFromId: String,
            @Query("deviceType")
            deviceType: String
    ) : Response<LibRepositoriesModel>
}