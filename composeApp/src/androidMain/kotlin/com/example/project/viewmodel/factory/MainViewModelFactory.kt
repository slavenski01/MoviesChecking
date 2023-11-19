package com.example.project.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project.shared.SpaceXSDK
import com.example.project.viewmodel.MainViewModel


class MainViewModelFactory(spaceXSDK: SpaceXSDK) : ViewModelProvider.Factory {
    private val spaceXSDK: SpaceXSDK

    init {
        this.spaceXSDK = spaceXSDK
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(spaceXSDK) as T
    }
}