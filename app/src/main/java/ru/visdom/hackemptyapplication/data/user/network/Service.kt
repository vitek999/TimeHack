package ru.visdom.hackemptyapplication.data.user.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import ru.visdom.hackemptyapplication.data.user.network.dto.UserDto
import ru.visdom.hackemptyapplication.utils.NetworkUtil

interface UserService {
    @GET("users/login")
    fun loginAsync(@Header("Authorization") token: String): Deferred<Response<UserDto>>
}

object UserNetwork {
    private val retrofit = Retrofit.Builder()
        .baseUrl(NetworkUtil.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val userService = retrofit.create(UserService::class.java)
}