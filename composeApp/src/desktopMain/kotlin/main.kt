import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.shared.cache.DatabaseDriverFactory

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Column {
            App(databaseDriverFactory = DatabaseDriverFactory())
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    //App()
}