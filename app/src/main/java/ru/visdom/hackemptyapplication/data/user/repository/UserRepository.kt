package ru.visdom.hackemptyapplication.data.user.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.visdom.hackemptyapplication.data.user.exceptions.UserNotAuthException
import ru.visdom.hackemptyapplication.data.user.network.UserNetwork
import ru.visdom.hackemptyapplication.data.user.network.dto.UserDto
import ru.visdom.hackemptyapplication.preferences.UserPreferences
import ru.visdom.hackemptyapplication.utils.getTokenFromPhoneAndPassword
import timber.log.Timber
import java.net.ConnectException

class UserRepository() {

    val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto>
        get() = _user

    suspend fun login(phone: String, password: String) {

        val userDto: UserDto? = null
        val response = UserNetwork.userService.loginAsync(
            getTokenFromPhoneAndPassword(
                phone, password
            )
        ).await()

        Timber.d("DEBUG: " + response.code().toString() + " " + response.message())

        when {
            response.code() == 200 -> UserPreferences.save(response.body()!!, password)
            response.code() == 401 -> throw UserNotAuthException()
            else -> throw ConnectException()
        }

        if (userDto != null)
            _user.value = userDto

    }

}