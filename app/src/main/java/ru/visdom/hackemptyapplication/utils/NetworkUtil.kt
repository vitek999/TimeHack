package ru.visdom.hackemptyapplication.utils

import okhttp3.Credentials

object NetworkUtil {
    const val BASE_URL = "http://79.170.167.31:8080/b2doc_SS/"
}

fun getTokenFromPhoneAndPassword(phone: String, password: String) =
    Credentials.basic(phone, password)