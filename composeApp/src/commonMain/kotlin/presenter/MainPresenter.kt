package presenter

import SpaceXSDK
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.shared.entity.RocketLaunch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val scope: CoroutineScope,
    private val spaceXSDK: SpaceXSDK
) {

    private val _launchesRockets = mutableStateOf<List<RocketLaunch>>(listOf())
    val launchesRockets: State<List<RocketLaunch>> get() = _launchesRockets

    init {
        updateLaunches(true)
    }

    private fun updateLaunches(forceLoad: Boolean) {
        scope.launch {
            _launchesRockets.value = spaceXSDK.getLaunches(forceLoad)
        }
    }
}