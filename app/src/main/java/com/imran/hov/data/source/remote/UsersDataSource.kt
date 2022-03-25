package com.imran.hov.data.source.remote

import com.imran.hov.data.api.ApiCall
import com.imran.hov.data.api.ApiResponse
import com.imran.hov.data.model.UsersListDataClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersDataSource @Inject constructor(private val apiCall: ApiCall) {

    suspend fun getUsers(): MutableList<UsersListDataClass>{
        return withContext(Dispatchers.IO){ApiResponse.getResult { apiCall.getPhotos() }}
    }

}