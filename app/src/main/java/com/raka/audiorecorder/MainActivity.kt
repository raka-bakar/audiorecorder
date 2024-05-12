package com.raka.audiorecorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.raka.audiorecorder.ui.navigation.MainNavigation
import com.raka.audiorecorder.ui.theme.AudioRecorderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioRecorderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = MainNavigation.Main.route,
                        route = MainNavigation.Main.graphId
                    ) {
                        // all screens listed in the Main Navigation will be navigated here
                        MainNavigation.getAllNavigation().forEach {
                            it.compose(navController)
                        }
                    }
                }
            }
        }
    }
}

