package com.imran.hov.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.imran.hov.data.model.UsersEntity
import com.imran.hov.data.model.UsersListResponseClass

@Dao
interface UsersDao {
    @Insert(onConflict = REPLACE)
    suspend fun addUsers(userList: MutableList<UsersEntity>?)

    @Query("SELECT * FROM users")
    suspend fun getUsers(): MutableList<UsersEntity>
}