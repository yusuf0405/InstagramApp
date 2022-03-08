package com.example.instagramapp.post_add_screen.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramapp.app.network.models.PostDto
import com.example.instagramapp.post_add_screen.domain.usecase.CreateNewPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val createNewPostUseCase: CreateNewPostUseCase,
) : ViewModel() {

    private val _postResponse: MutableLiveData<Response<PostDto>?> = MutableLiveData()
    val postResponse: MutableLiveData<Response<PostDto>?> = _postResponse

    fun createNewPost(postDto: PostDto) =
        viewModelScope.launch {
            _postResponse.value = createNewPostUseCase.execute(postDto = postDto)
        }


    fun finish() {
        _postResponse.value = null
    }


}