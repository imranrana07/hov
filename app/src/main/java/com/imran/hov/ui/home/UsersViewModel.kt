package com.imran.hov.ui.home

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.imran.hov.data.model.UsersListResponseClass
import com.imran.hov.data.repository.UsersRepository
import com.imran.hov.utils.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val usersRepository: UsersRepository):ViewModel() {
//    val error = MutableLiveData<String>()
//    val progressBar = MutableLiveData<Int>()
//    val users = MutableLiveData<MutableList<UsersListResponseClass>>()

    val  users = usersRepository.getUsers().asLiveData()
}