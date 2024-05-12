package com.raka.audiorecorder.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.raka.audiorecorder.R
import com.raka.audiorecorder.data.database.AudioRecord

/**
 * Component that shows item of audio record view
 * @param item of AudioRecord
 * @param onItemClick higher function to handle on item click
 * @param onDeleteClick higher function to handle on delete icon click
 */
@Composable
fun RecordingItemView(
    item: AudioRecord,
    onItemClick: (AudioRecord) -> Unit,
    onDeleteClick: (AudioRecord) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    val fileName = stringResource(id = R.string.name_format, item.id)

    ConstraintLayout(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium_large))
            .fillMaxSize()
            .clickable { onItemClick(item) }
    ) {
        val (imageRef, nameRef, countryRef) = createRefs()
        // Text to display the filename
        Text(
            text = fileName,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .constrainAs(nameRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(
                    top = dimensionResource(id = R.dimen.padding_extra_large),
                    start = dimensionResource(id = R.dimen.padding_small)
                )
        )
        // Text to display the duration of the recording
        Text(
            text = stringResource(id = R.string.duration_format, item.duration),
            color = Color.Gray,
            modifier = Modifier
                .constrainAs(countryRef) {
                    start.linkTo(parent.start)
                    top.linkTo(nameRef.bottom)
                }
                .padding(
                    top = dimensionResource(id = R.dimen.padding_small),
                    start = dimensionResource(id = R.dimen.padding_small)
                )
        )
        // Button delete
        Image(painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = stringResource(
                id = R.string.desc_img
            ),
            modifier = Modifier
                .size(30.dp)
                .constrainAs(imageRef) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable { openDialog.value = true }
        )
        // Showing alert dialog when button delete is pressed
        if (openDialog.value) {
            AlertDialogView(
                onConfirmClicked = { onDeleteClick(item) },
                dismissLabel = stringResource(id = R.string.lbl_negative),
                description = stringResource(id = R.string.delete_description_format, fileName),
                confirmLabel = stringResource(id = R.string.lbl_positive),
                title = stringResource(id = R.string.lbl_warning)
            ) {
                openDialog.value = false
            }
        }
    }
}