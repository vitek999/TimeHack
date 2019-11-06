package ru.visdom.hackemptyapplication.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.visdom.hackemptyapplication.data.user.exceptions.UserNotAuthException
import ru.visdom.hackemptyapplication.data.user.network.dto.UserDto
import ru.visdom.hackemptyapplication.data.user.repository.UserRepository
import timber.log.Timber
import java.net.ConnectException

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    enum class AuthStatus {
        NONE, OK, WRONG_AUTH_INFORMATION, CONNECTION_ERROR
    }

    private val repository: UserRepository = UserRepository()

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _authStatus = MutableLiveData<AuthStatus>()

    val authStatus: LiveData<AuthStatus>
        get() = _authStatus

    private var _user: MutableLiveData<UserDto> = repository._user

    private val user: LiveData<UserDto>
        get() = _user

    init {
        _authStatus.value = AuthStatus.NONE
    }

    fun authUser(phone: String, password: String) {
        viewModelScope.launch {
            try {
                repository.login(phone, password)
                _authStatus.value = AuthStatus.OK
            } catch (connectionException: ConnectException) {
                Timber.e(connectionException)
                _authStatus.value = AuthStatus.CONNECTION_ERROR
            } catch (userNotAuthException: UserNotAuthException) {
                Timber.e(userNotAuthException)
                _authStatus.value = AuthStatus.WRONG_AUTH_INFORMATION
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}