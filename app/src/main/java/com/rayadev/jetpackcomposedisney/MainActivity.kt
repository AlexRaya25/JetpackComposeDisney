package com.rayadev.jetpackcomposedisney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.rayadev.jetpackcomposedisney.data.local.AppDatabase
import com.rayadev.jetpackcomposedisney.presentation.navigation.Navigation
import com.rayadev.jetpackcomposedisney.utils.Constants.DATABASE_NAME
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    companion object {
        lateinit var database: AppDatabase
            private set
    }
    @OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
        setContent {
            JetpackComposeDisneyApp {
                Navigation()
            }
        }
    }
}