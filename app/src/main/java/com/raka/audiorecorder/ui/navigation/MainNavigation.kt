package com.raka.audiorecorder.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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

    /**
     *  Object Main that represent Main Screen
     */
    object Main : MainNavigation("MAIN") {
        /**
         *  Compose function to draw all composable components in the Main Screen
         */
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

    /**
     *  Object Recordings that represent Recording Screen
     */
    object Recordings : MainNavigation("RECORDINGS") {
        /**
         * function to navigate to Recordings Screen
         */
        fun NavController.navigateTo() {
            navigate(route = route)
        }

        /**
         *  Compose function to draw all composable components in the Recording Screen
         */
        context(NavGraphBuilder) override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments(),
                // sliding transition to enter or exit Recordings screen
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(700)
                    )
                }

            ) {
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