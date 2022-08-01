package com.example.room_db_rv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.room_db_rv.db.RoomAppDb
import com.example.room_db_rv.db.UserEntity

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {
//    lateinit var allUsers: MutableLiveData<List<UserEntity>>

    var allUsers: MutableLiveData<List<UserEntity>> = MutableLiveData()

    init {
        getAllUser()
    }

    fun getAllUsersObservers(): MutableLiveData<List<UserEntity>> {
        return allUsers
    }

    fun getAllUser() {
        val userDao = RoomAppDb.getAppDatabase((getApplication()))?.userDao()
        val list = userDao?.getAllUserInfo()
        allUsers.postValue(list)

    }

    fun insertUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDatabase((getApplication()))?.userDao()
        userDao?.insertUser(entity)
        getAllUser()
    }


    fun deleteUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDatabase((getApplication()))?.userDao()
        userDao?.deleteUser(entity)
        getAllUser()
    }


    fun updateUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDatabase((getApplication()))?.userDao()
        userDao?.updateUser(entity)
        getAllUser()
    }
}