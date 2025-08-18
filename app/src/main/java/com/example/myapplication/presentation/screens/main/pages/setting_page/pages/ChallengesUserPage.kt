package com.example.myapplication.presentation.screens.main.pages.setting_page.pages


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.HomeComponents.EmptyChallenges
import com.example.myapplication.presentation.components.HomeComponents.ItemChallenger
import com.example.myapplication.presentation.components.HomeComponents.LoadingShimmer
import com.example.myapplication.presentation.viewmodel.ChallengesUserViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.myapplication.R
import com.example.myapplication.presentation.components.AlertDialog
import com.example.myapplication.presentation.components.HeaderTopBar
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.presentation.components.SnackBar
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.presentation.viewmodel.DeleteChallengeViewModel
import com.example.myapplication.presentation.viewmodel.HomeChallengesViewModel
import com.example.myapplication.utils.GlobalState
import com.example.myapplication.utils.RemoveItemState

@Composable
fun ChallengesUserPage(
        challengesUserViewModel: ChallengesUserViewModel = hiltViewModel(),
        navController: NavController,
        deleteChallengeViewModel: DeleteChallengeViewModel = hiltViewModel(),
    ){

    val listState = rememberLazyListState()
    val stateGetPosts by challengesUserViewModel.stateGetChallenges.collectAsState()
    val allPosts by challengesUserViewModel.challenges.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = stateGetPosts == GlobalState.LOADING && allPosts.isEmpty()
    )
    val context = LocalContext.current

    val removeItemState by deleteChallengeViewModel.removeChallengeState.collectAsState()
    val selectChallengeID by deleteChallengeViewModel.selectChallengeID.collectAsState()
    var isProgress by remember { mutableStateOf(false) }
    val initialState by challengesUserViewModel.initialState.collectAsState()


    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val threshold = 3
                val isLoading = stateGetPosts == GlobalState.LOADING

                if (!isLoading && lastVisibleIndex != null && lastVisibleIndex >= totalItems - threshold) {
                    challengesUserViewModel.getAllChallengesUser()
                }
            }
    }

    Scaffold { innerPadding->
        BackHandler(enabled = isProgress) {

        }
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .padding(start = 20.dp, end = 10.dp)
        ){
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    if (initialState == true){
                        deleteChallengeViewModel.setStateRemove(stateRemove = RemoveItemState.OFF_REMOVE)
                        challengesUserViewModel.restartCounterPage()
                    }
                },
            ) {

                LazyColumn(state = listState) {

                    item {

                        //Header
                        HeaderTopBar(
                            onClick = {
                                navController.popBackStack()
                            },
                            title = stringResource(R.string.my_challenges)
                        )//end Header

                    }

                    itemsIndexed(allPosts) { index, item ->
                        Box(
                            modifier = Modifier.padding(end = 0.dp)
                        ){
                            ItemChallenger(challenge = item,
                                onRemove = {
                                    deleteChallengeViewModel.setStateRemove(
                                        RemoveItemState.I_WANT_TO_REMOVE,
                                        selectChallengeId = item.challengeId
                                    )
                                },
                                onEdit = {
                                    navController.navigate(Routes.challengeID(item.challengeId))
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item {
                        when (stateGetPosts) {
                             GlobalState.LOADING -> {
                                LoadingShimmer()
                            }

                            GlobalState.EMPTY -> {
                                EmptyChallenges(appNavController = navController)
                            }

                            GlobalState.ERROR -> {
                                if (allPosts.isNotEmpty()) {
                                } else {
                                    Text(stringResource(R.string.something_wrong), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                                    Spacer(Modifier.height(15.dp))
                                    ButtonFill(
                                        onClick = {
                                            challengesUserViewModel.getAllChallengesUser()
                                        },
                                        label = stringResource(R.string.retry)
                                    )
                                }
                            }

                            else -> {}
                        }
                    }


                }
            }

            //State Remove Challenge
            when(removeItemState){

                    RemoveItemState.I_WANT_TO_REMOVE -> {

                    isProgress = true

                    AlertDialog(
                        messageAlert = stringResource(id = R.string.delete_challenge_message),
                        titleButtonOne = stringResource(id = R.string.delete_button),

                        //Delete Button
                        onClickButtonOne = {
                            deleteChallengeViewModel.deleteChallenge(challengeID = selectChallengeID)
                        },
                        titleButtonTwo = stringResource(id = R.string.cancel_button),

                        //Cansel Button
                        onClickButtonTwo = {
                            deleteChallengeViewModel.setStateRemove(RemoveItemState.OFF_REMOVE)
                        },
                        isButtonOne = true,
                        isButtonTwo = true,
                    )
                }

                RemoveItemState.REMOVING -> {

                    isProgress = true

                    LoadingDialog(
                        message = stringResource(id = R.string.deleting_challenge)
                    )
                }

                RemoveItemState.REMOVED -> {

                    LaunchedEffect(true) {
                        challengesUserViewModel.removeChallenge(challengeID = selectChallengeID)
                        snackBarHostState.showSnackbar(message = context.getString(R.string.deleted_successfull))
                        deleteChallengeViewModel.setStateRemove(stateRemove = RemoveItemState.OFF_REMOVE)

                    }

                }

                RemoveItemState.OFF_REMOVE -> {
                    isProgress = false
                }

                RemoveItemState.WRONG_WHEN_REMOVE -> {

                    LaunchedEffect(removeItemState) {

                        snackBarHostState.showSnackbar(message = context.getString(R.string.delete_wrong))

                        isProgress = false

                    }
                }

                RemoveItemState.UNAuthorization -> {

                    AlertDialog(
                        messageAlert = stringResource(R.string.session_expired_message),
                        titleButtonOne = stringResource(R.string.back_to_login_button),
                        isButtonOne = true,
                        onClickButtonOne = {

                            navController.navigate(Routes.authScreen){
                                popUpTo(0){inclusive = true}

                            }
                        },

                        )
                }

                else -> {}
            }

            SnackBar(snackBarHostState = snackBarHostState , modifier = Modifier.align(Alignment.BottomCenter))

        }

    }

}