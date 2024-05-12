package com.raka.audiorecorder.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.raka.audiorecorder.R
import com.raka.audiorecorder.data.database.AudioRecord

@Composable
fun AlertDialogView(
    onConfirmClicked: () -> Unit,
    title: String,
    description: String,
    confirmLabel: String,
    dismissLabel: String,
    onDismissClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismissClicked()
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = description)
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_200)),
                onClick = {
                    onConfirmClicked()
                    onDismissClicked()
                }) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_200)),
                onClick = {
                    onDismissClicked()
                }) {
                Text(dismissLabel)
            }
        }
    )
}