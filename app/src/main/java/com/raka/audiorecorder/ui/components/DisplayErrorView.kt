package com.raka.audiorecorder.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.raka.audiorecorder.R

/**
 * Component that shows Error message view
 * @param message error message of type String
 */
@Composable
fun DisplayErrorView(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            color = colorResource(id = R.color.error),
            modifier = Modifier.align(Alignment.Center),
        )
    }
}