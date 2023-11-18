import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shared.cache.DatabaseDriverFactory
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format
import org.example.library.MR
import presenter.MainPresenter

@Composable
fun App(
    modifier: Modifier = Modifier,
    databaseDriverFactory: DatabaseDriverFactory
) {
    val mainPresenter = MainPresenter(
        scope = rememberCoroutineScope(),
        spaceXSDK = SpaceXSDK(databaseDriverFactory)
    )
    val launches = remember { mainPresenter.launchesRockets }
    MaterialTheme {
        Column(
            modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = StringDesc.Resource(MR.strings.title).localized())
            LazyColumn {
                items(
                    items = launches.value,
                    itemContent = {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = MR.strings.launch_name.format(it.missionName).localized()
                            )
                            Text(text = MR.strings.launch_year.format(it.launchYear).localized())
                            Text(
                                text = MR.strings.launch_details.format(it.details ?: "")
                                    .localized()
                            )
                            Text(
                                text = StringDesc
                                    .Resource(
                                        if (it.launchSuccess == true) {
                                            MR.strings.successful
                                        } else {
                                            MR.strings.unsuccessful
                                        }
                                    ).localized(),

                                color = if (it.launchSuccess == true) Color.Green else Color.Red
                            )
                        }
                    })
            }
        }
    }
}