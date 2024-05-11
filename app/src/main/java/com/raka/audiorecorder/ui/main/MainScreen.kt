package com.raka.audiorecorder.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import com.raka.audiorecorder.R

/**
 * Component of Main Screen
 * @param onStartClicked higher function to handle on play button clicked
 * @param onStopClicked higher function to handle on stop button clicked
 * @param ticker duration value of the recording, type String
 * @param toListScreen higher function to handle on recordings button clicked
 */
@Composable
fun MainScreen(
    onStartClicked: (String) -> Unit,
    onStopClicked: (String) -> Unit,
    ticker: String,
    toListScreen: () -> Unit
) {

    val context = LocalContext.current

    // Launcher to request for permission to the user
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(
                context,
                context.resources.getText(R.string.permission_warning),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val warningText = stringResource(id = R.string.recording_warning)
    var isRecording by remember {
        mutableStateOf(false)
    }
    // icon resource for the button
    val iconRes: Painter = if (!isRecording) {
        painterResource(id = R.drawable.ic_record)
    } else {
        painterResource(id = R.drawable.ic_stop)
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (tickerRef, playButtonRef, recordingsButtonRef) = createRefs()
        // Text to display duration of the recording
        Text(
            fontSize = dimensionResource(id = R.dimen.text_size_large).value.sp,
            text = ticker,
            modifier = Modifier.constrainAs(tickerRef) {
                centerVerticallyTo(parent)
                centerHorizontallyTo(parent)
            }
        )

        // Play/Stop button
        OutlinedIconButton(
            onClick = {
                /**
                 * Asking for permission to the user
                 */
                when(PackageManager.PERMISSION_GRANTED){
                    ContextCompat.checkSelfPermission(context,
                        Manifest.permission.RECORD_AUDIO
                    )-> {
                        // when permission is granted
                        if (!isRecording) {
                            isRecording = true
                            onStartClicked(context.cacheDir.toString())
                        } else {
                            isRecording = false
                            onStopClicked(ticker)
                        }
                    }
                    else -> {
                        // when permission is not granted
                        launcher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }

            }, modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                .size(dimensionResource(id = R.dimen.outline_button_size))
                .constrainAs(playButtonRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            colors = IconButtonColors(
                containerColor = Color.Red, disabledContainerColor = Color.Gray,
                contentColor = Color.Black, disabledContentColor = Color.Gray
            )
        ) {
            Icon(
                iconRes,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
            )
        }

        // Recordings button
        OutlinedIconButton(
            onClick = {
                if (isRecording) {
                    Toast.makeText(
                        context,
                        warningText,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    toListScreen()
                }
            }, modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    bottom = dimensionResource(id = R.dimen.padding_medium)
                )
                .size(dimensionResource(id = R.dimen.outline_button_size))
                .constrainAs(recordingsButtonRef) {
                    start.linkTo(playButtonRef.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Icon(
                painterResource(id = R.drawable.ic_menu),
                contentDescription = null
            )
        }
    }
}