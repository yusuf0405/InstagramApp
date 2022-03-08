package com.example.instagramapp.sign_screen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramapp.login_screen.domain.models.User
import com.example.instagramapp.sign_screen.domain.usecase.SignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val signUseCase: SignUseCase,
) : ViewModel() {

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> = _exception

    private val _state: MutableLiveData<Response<User>> = MutableLiveData()
    val state: LiveData<Response<User>> = _state

    fun sign(user: User) {
        try {
            viewModelScope.launch { _state.value = signUseCase.execute(user = user) }
        } catch (e: HttpException) {
            _exception.value = e
        }


    }
}