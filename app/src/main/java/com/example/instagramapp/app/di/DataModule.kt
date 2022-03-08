package com.example.instagramapp.app.di

import com.example.instagramapp.app.network.api.PostApi
import com.example.instagramapp.app.network.api.UserApi
import com.example.instagramapp.home_screen.data.HomeRepositoryImpl
import com.example.instagramapp.home_screen.domain.repository.HomeRepository
import com.example.instagramapp.login_screen.data.LoginRepositoryImpl
import com.example.instagramapp.login_screen.domain.repository.LoginRepository
import com.example.instagramapp.post_add_screen.data.AddPostRepositoryImpl
import com.example.instagramapp.post_add_screen.domain.repository.AddPostRepository
import com.example.instagramapp.sign_screen.data.SignRepositoryImpl
import com.example.instagramapp.sign_screen.domain.repository.SignRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideLoginRep(userApi: UserApi): LoginRepository = LoginRepositoryImpl(userApi = userApi)

    @Provides
    @Singleton
    fun provideSignRep(userApi: UserApi): SignRepository = SignRepositoryImpl(userApi = userApi)

    @Provides
    @Singleton
    fun provideHomeRep(postApi: PostApi): HomeRepository = HomeRepositoryImpl(postApi = postApi)

    @Provides
    @Singleton
    fun provideAddRep(postApi: PostApi): AddPostRepository =
        AddPostRepositoryImpl(postApi = postApi)


}