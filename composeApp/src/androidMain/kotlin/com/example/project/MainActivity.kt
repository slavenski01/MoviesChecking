package com.example.project

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.shared.cache.DatabaseDriverFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(databaseDriverFactory = DatabaseDriverFactory(context = this))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    //App()
}