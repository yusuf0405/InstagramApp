package com.example.instagramapp.home_screen.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramapp.app.network.models.PostsDto
import com.example.instagramapp.home_screen.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
) : ViewModel() {

    private val _postList: MutableLiveData<Response<PostsDto>> = MutableLiveData()
    val postList: LiveData<Response<PostsDto>> = _postList

    fun getPostList() = viewModelScope.launch { _postList.value = getPostsUseCase.execute() }


}