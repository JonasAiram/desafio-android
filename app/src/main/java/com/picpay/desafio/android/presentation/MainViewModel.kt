package com.picpay.desafio.android.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.entities.User
import com.picpay.desafio.android.domain.useCase.ListUsers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val ListUsers: ListUsers
) : ViewModel() {

    private val _users = MutableLiveData<State>()
    val users: LiveData<State> = _users

    fun listUsers() {
        viewModelScope.launch {
            ListUsers.getUsers()
                .onStart { _users.postValue(State.Loading) }
                .catch { _users.postValue(State.Error(it)) }
                .collect { _users.postValue(State.Success(it)) }
        }
    }


    sealed class State {
        object Loading : State()
        data class Success(val list: List<User>) : State()
        data class Error(val error: Throwable) : State()
    }

}
