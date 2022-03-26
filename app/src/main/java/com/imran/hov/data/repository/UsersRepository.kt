package com.imran.hov.data.repository

import com.imran.hov.data.model.UsersListResponseClass
import com.imran.hov.data.source.remote.UsersDataSource
import javax.inject.Inject

class UsersRepository @Inject constructor(private val usersDataSource: UsersDataSource) {

    suspend fun getUsers(): MutableList<UsersListResponseClass>{
        return  usersDataSource.getUsers()
    }
}