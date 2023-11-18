import androidx.compose.ui.window.ComposeUIViewController
import com.example.shared.cache.DatabaseDriverFactory

fun MainViewController() =
    ComposeUIViewController { App(databaseDriverFactory = DatabaseDriverFactory()) }
