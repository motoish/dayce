package com.motoish.dayce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.motoish.dayce.ui.DayceApp
import com.motoish.dayce.ui.theme.DayceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DayceTheme {
                DayceApp()
            }
        }
    }
}
