package com.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.shared.SpaceXSDK
import com.example.project.shared.entity.RocketLaunch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val spaceXSDK: SpaceXSDK) : ViewModel() {

    private var _launches = MutableStateFlow<List<RocketLaunch>>(listOf())
    val launches: StateFlow<List<RocketLaunch>> get() = _launches

    init {
        viewModelScope.launch {
            getLaunches().collect { items ->
                _launches.update { list -> list + items }
            }
        }
    }

    private fun getLaunches(): Flow<List<RocketLaunch>> = flow {
        emit(spaceXSDK.getLaunches(false))
    }
}