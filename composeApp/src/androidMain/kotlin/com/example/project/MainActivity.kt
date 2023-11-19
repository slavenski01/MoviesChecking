package com.example.project

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import com.example.project.shared.SpaceXSDK
import com.example.project.shared.cache.DatabaseDriverFactory
import com.example.project.viewmodel.MainViewModel
import com.example.project.viewmodel.factory.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private val spaceXSDK = SpaceXSDK(databaseDriverFactory = DatabaseDriverFactory(this))
    private val mainViewModel by viewModels<MainViewModel> { MainViewModelFactory(spaceXSDK) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val launches = remember { mainViewModel.launches.value }
            App(launchesList = launches)
        }
    }
}