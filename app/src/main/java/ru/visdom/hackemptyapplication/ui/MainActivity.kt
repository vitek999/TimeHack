package ru.visdom.hackemptyapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.visdom.hackemptyapplication.R
import ru.visdom.hackemptyapplication.preferences.UserPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UserPreferences.init(applicationContext)
    }
}
