package ru.visdom.hackemptyapplication.preferences

import android.content.Context
import android.content.SharedPreferences
import ru.visdom.hackemptyapplication.data.user.network.dto.UserDto

object UserPreferences {
    private lateinit var preferences: SharedPreferences

    private const val PREFERENCES_NAME = "user_preferences"

    private const val PREFERENCES_USER_PHONE = "user_phone"
    private const val PREFERENCES_USER_PASSWORD = "user_password"
    private const val PREFERENCES_USER_FIRST_NAME = "user_first_name"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun save(userDto: UserDto, password: String) {
        val editor = preferences.edit()
        with(editor) {
            putString(PREFERENCES_USER_PHONE, userDto.phone)
            putString(PREFERENCES_USER_PASSWORD, password)
            putString(PREFERENCES_USER_FIRST_NAME, userDto.firstName)
            apply()
        }
    }

    fun get() = UserDto(
        preferences.getString(PREFERENCES_USER_PHONE, "")!!,
        preferences.getString(PREFERENCES_USER_PASSWORD, "")!!,
        preferences.getString(PREFERENCES_USER_FIRST_NAME, "")!!
    )

    fun clear() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}