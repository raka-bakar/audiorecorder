package com.raka.audiorecorder.ui.recordings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raka.audiorecorder.R
import com.raka.audiorecorder.data.database.AudioRecord
import com.raka.audiorecorder.ui.components.DisplayErrorView
import com.raka.audiorecorder.ui.components.LoadingView
import com.raka.audiorecorder.ui.components.RecordingItemView
import com.raka.audiorecorder.utils.CallState


/**
 * Component of Recordings Screen
 * @param callState result state of the call to get a list of Audio record
 * @param onItemClicked higher function to play/stop an audio record
 * @param onDeleteClicked higher function to handle on delete icon clicked
 * @param onBackClicked higher function to handle on back button clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingsScreen(
    callState: CallState<List<AudioRecord>>,
    onItemClicked: (AudioRecord) -> Unit,
    onDeleteClicked: (AudioRecord) -> Unit,
    onBackClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.desc_img),
                        tint = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(id = R.string.title),
                    color = colorResource(id = R.color.white)
                )

            },
            colors = TopAppBarDefaults
                .topAppBarColors(containerColor = colorResource(id = R.color.teal_200))
        )
        // Handling each status of call state
        when (callState.status) {
            CallState.Status.SUCCESS -> {
                val list = callState.data
                if (list.isNullOrEmpty()) {
                    DisplayErrorView(message = stringResource(id = R.string.lbl_data_empty))
                } else {
                    RecordingListView(
                        list = list,
                        onItemClicked = onItemClicked,
                        onDeleteClicked = onDeleteClicked
                    )
                }
            }

            CallState.Status.LOADING -> {
                LoadingView()
            }

            CallState.Status.ERROR -> {
                DisplayErrorView(message = stringResource(id = R.string.lbl_error))
            }
        }
    }
}

/**
 *  Component that composes All Recordings Data
 *  @param list data of All Recordings Teams
 *  @param onItemClicked handle for playing the record
 *  @param onDeleteClicked handle for deleting the record
 */
@Composable
private fun RecordingListView(
    list: List<AudioRecord>,
    onItemClicked: (AudioRecord) -> Unit,
    onDeleteClicked: (AudioRecord) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .testTag("HomeList")
    ) {
        itemsIndexed(items = list) { index, team ->
            RecordingItemView(
                item = team,
                onItemClick = onItemClicked,
                onDeleteClick = onDeleteClicked
            )
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        }
    }
}