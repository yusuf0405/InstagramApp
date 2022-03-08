package com.example.instagramapp.login_screen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramapp.login_screen.domain.usecase.LoginUseCase
import com.parse.ParseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _state: MutableLiveData<Response<ParseUser>> = MutableLiveData()
    val state: LiveData<Response<ParseUser>> = _state

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> = _exception


    fun login(
        email: String,
        password: String,
    ) = viewModelScope.launch {
        try {
            _state.value = loginUseCase.execute(email = email, password = password)
        } catch (e: Exception) {
            _exception.value = e
        }

    }
}
