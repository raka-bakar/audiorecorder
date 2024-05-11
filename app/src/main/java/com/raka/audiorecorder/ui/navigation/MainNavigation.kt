package com.raka.audiorecorder.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raka.audiorecorder.ui.main.MainScreen
import com.raka.audiorecorder.ui.main.MainViewModel
import com.raka.audiorecorder.ui.navigation.MainNavigation.Recordings.navigateTo
import com.raka.audiorecorder.ui.recordings.RecordingsScreen
import com.raka.audiorecorder.ui.recordings.RecordingsViewModel
import com.raka.audiorecorder.utils.CallState

/**
 * MainNavigation graph that contains the flow map of the app
 */
sealed class MainNavigation(override val route: String) : Navigation(route = route) {
    override val graphId = "Main"

    companion object {
        /**
         * get all destinations on this graph
         */
        fun getAllNavigation() = setOf(Main, Recordings)
    }

    object Main : MainNavigation("MAIN") {
        context(NavGraphBuilder) override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {

                val viewModel: MainViewModel = hiltViewModel()
                val ticker by viewModel.ticker.collectAsStateWithLifecycle()

                MainScreen(
                    onStartClicked = viewModel::startRecording,
                    onStopClicked = viewModel::stopRecording,
                    ticker = ticker
                ) {
                    controller.navigateTo()
                }
            }
        }
    }

    object Recordings : MainNavigation("RECORDINGS") {
        fun NavController.navigateTo() {
            navigate(route = route)
        }

        context(NavGraphBuilder) override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {
                val viewModel: RecordingsViewModel = hiltViewModel()
                val callState by viewModel.recordingList.data.collectAsStateWithLifecycle(CallState.loading())
                RecordingsScreen(
                    callState = callState, onItemClicked = viewModel::onItemClicked,
                    onDeleteClicked = viewModel::deleteAudio
                ) {
                    viewModel.stopAudio()
                    controller.popBackStack()
                }
            }
        }
    }
}