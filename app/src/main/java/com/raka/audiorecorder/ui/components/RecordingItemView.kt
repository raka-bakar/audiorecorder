package com.raka.audiorecorder.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    ConstraintLayout(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .clickable { onItemClick(item) }
    ) {
        val (imageRef, nameRef, countryRef) = createRefs()
        Text(
            text = stringResource(id = R.string.name_format, item.id),
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
        Text(
            text =  stringResource(id = R.string.duration_format, item.duration),
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
                .clickable { onDeleteClick(item) }
        )
    }
}