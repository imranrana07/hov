package com.imran.hov.data.api


import com.imran.hov.data.model.UsersEntity
import com.imran.hov.data.model.UsersListResponseClass
import retrofit2.Response
import retrofit2.http.GET

interface ApiCall {
    @GET("users")
    suspend fun getPhotos():Response<MutableList<UsersEntity>>
}