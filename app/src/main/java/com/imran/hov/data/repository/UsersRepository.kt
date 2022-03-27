package com.imran.hov.data.repository

import androidx.room.withTransaction
import com.imran.hov.data.model.UsersEntity
import com.imran.hov.data.model.UsersListResponseClass
import com.imran.hov.data.source.local.database.AppDatabase
import com.imran.hov.data.source.local.database.UsersDao
import com.imran.hov.data.source.remote.UsersDataSource
import com.imran.hov.utils.isInternetAvailable
import com.imran.hov.utils.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersDataSource: UsersDataSource,
    private val db: AppDatabase
) {

//    suspend fun getUsers(): MutableList<UsersListResponseClass>{
//        val list = if (isInternetAvailable()){
//            usersDataSource.getUsers()
//        }else if (usersDao.getUsers().size >0 ){
//            usersDao.getUsers() as MutableList<UsersListResponseClass>
//        }
//        return  list
//    }
    private val usersDao = db.userDao()

     fun getUsers() = networkBoundResource(
        query = {
            usersDao.getUsers()
        },
        fetch = {
            delay(2000)
            usersDataSource.getUsers()
        },
        saveFetchResult = { users ->
            db.withTransaction {
                usersDao.deleteAllUsers()
                usersDao.addUsers(users)
            }
        }
    )
}