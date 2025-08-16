package com.example.myapplication.presentation.screens.main.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.HomeComponents.EmptyChallenges
import com.example.myapplication.presentation.components.HomeComponents.HeaderHome
import com.example.myapplication.presentation.components.HomeComponents.ItemChallenger
import com.example.myapplication.presentation.components.HomeComponents.LoadingShimmer
import com.example.myapplication.presentation.viewmodel.HomeChallengesViewModel
import com.example.myapplication.utils.StateGetChallenges
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.myapplication.R
import com.example.myapplication.presentation.components.AlertDialog
import com.example.myapplication.presentation.components.BottomSheetComponent
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.components.SnackBar
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.presentation.viewmodel.CityAndGovernorateViewModel
import com.example.myapplication.presentation.viewmodel.DeleteChallengeViewModel
import com.example.myapplication.presentation.viewmodel.FilterViewModel
import com.example.myapplication.utils.GlobalState
import com.example.myapplication.utils.RemoveItemState

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    appNavController: NavController,
    homeChallengesViewModel: HomeChallengesViewModel,
    deleteChallengeViewModel: DeleteChallengeViewModel = hiltViewModel(),
    cityAndGovernorateViewModel: CityAndGovernorateViewModel,
    filterViewModel: FilterViewModel,
) {
    val listState = rememberLazyListState()

    val initialState by homeChallengesViewModel.initialState.collectAsState()
    val stateGetPosts by homeChallengesViewModel.stateGetChallenges.collectAsState()
    val allPosts by homeChallengesViewModel.challenges.collectAsState()
    val removeItemState by deleteChallengeViewModel.removeChallengeState.collectAsState()
    val selectChallengeID by deleteChallengeViewModel.selectChallengeID.collectAsState()
    var isProgress by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    val isFilter by homeChallengesViewModel.isFilter.collectAsState()
    val challengesFilter by homeChallengesViewModel.challengesFilter.collectAsState()
    val stateFilter by homeChallengesViewModel.stateFilter.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = stateGetPosts is StateGetChallenges.Loading && allPosts.isEmpty()
    )

        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleIndex ->
                    val totalItems = listState.layoutInfo.totalItemsCount
                    val threshold = 3

                    if (isFilter){
                        val isLoading = stateFilter == GlobalState.LOADING

                        if (!isLoading && stateFilter != GlobalState.EMPTY && stateFilter != GlobalState.ERROR && lastVisibleIndex != null && lastVisibleIndex >= totalItems - threshold) {
                            homeChallengesViewModel.filterChallenges()
                        }
                    }else{
                        val isLoading = stateGetPosts is StateGetChallenges.Loading

                        if (!isLoading && lastVisibleIndex != null && lastVisibleIndex >= totalItems - threshold) {
                            homeChallengesViewModel.getAllChallenges()
                        }
                    }


                }
        }


        Box{
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    if(isFilter){
                        homeChallengesViewModel.resetFilter()
                    }else{
                        if (initialState == true){
                            deleteChallengeViewModel.setStateRemove(stateRemove = RemoveItemState.OFF_REMOVE)
                            cityAndGovernorateViewModel.getGovernorate()
                            homeChallengesViewModel.restartCounterPage()
                        }
                    }

                }

            ) {
                LazyColumn(state = listState) {
                    item {
                        Spacer(Modifier.height(20.dp))

                        Box(
                            modifier = Modifier.padding(
                                horizontal = 20.dp
                            )
                        ){
                            HeaderHome({
                                showSheet = true
                            },
                                isFilter = isFilter
                            )
                        }

                        Spacer(Modifier.height(30.dp))
                    }

                    itemsIndexed(if (isFilter) challengesFilter else allPosts) { index, item ->
                        Box(
                            modifier = Modifier.fillMaxSize().padding(start = 20.dp, end = 5.dp)
                        ){
                            ItemChallenger(
                                challenge = item,
                                onRemove = {
                                    deleteChallengeViewModel.setStateRemove(
                                        RemoveItemState.I_WANT_TO_REMOVE,
                                        selectChallengeId = item.challengeID
                                    )
                                },
                                onEdit = {
                                    appNavController.navigate(Routes.challengeID(item.challengeID))
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }



                    item {
                        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
                            if (isFilter){
                                when(stateFilter){

                                    GlobalState.LOADING -> {
                                        LoadingShimmer()
                                    }

                                    GlobalState.EMPTY -> {
                                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                                            Image(modifier = Modifier.fillMaxWidth().height(250.dp), painter = painterResource(R.drawable.image_empty_filter) , contentDescription = "")

                                            Spacer(modifier = Modifier.height(10.dp))


                                            ParagraphText(text = stringResource(id = R.string.no_challenges))
                                        }
                                    }

                                    GlobalState.ERROR -> {
                                        Column {
                                            Text(
                                                stringResource(R.string.something_wrong),
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center
                                            )
                                            Spacer(Modifier.height(15.dp))
                                            ButtonFill(
                                                onClick = {
                                                    homeChallengesViewModel.resetFilter()
                                                },
                                                label = stringResource(R.string.retry)
                                            )
                                        }
                                    }

                                    else -> {}
                                }
                            }else{
                                when (val state = stateGetPosts) {
                                    is StateGetChallenges.Loading -> {
                                        Column {
                                            LoadingShimmer()
                                        }
                                    }

                                    is StateGetChallenges.NULL -> {
                                        EmptyChallenges(appNavController = appNavController)
                                    }

                                    is StateGetChallenges.Failure -> {
                                        if (allPosts.isEmpty()) {
                                            Column {
                                                Text(
                                                    stringResource(R.string.something_wrong),
                                                    modifier = Modifier.fillMaxWidth(),
                                                    textAlign = TextAlign.Center
                                                )
                                                Spacer(Modifier.height(15.dp))
                                                ButtonFill(
                                                    onClick = {
                                                        homeChallengesViewModel.getAllChallenges()
                                                    },
                                                    label = stringResource(R.string.retry)
                                                )
                                            }

                                        }
                                    }

                                    else -> {}
                                }
                            }
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
                        homeChallengesViewModel.removeChallenge(challengeID = selectChallengeID)
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

                            appNavController.navigate(Routes.authScreen){
                                popUpTo(0){inclusive = true}

                            }
                        },

                        )
                }

                else -> {}
            }

            SnackBar(snackBarHostState = snackBarHostState , modifier = Modifier.align(Alignment.BottomCenter))

            BottomSheetComponent(
                filterViewModel = filterViewModel,
                showBottomSheet = showSheet,
                isFilter = isFilter,
                onDismiss = { showSheet = false },
                cityAndGovernorateViewModel = cityAndGovernorateViewModel,
                onClickFilter = { filterRequest ->
                    showSheet = false
                    homeChallengesViewModel.setInitialFilter(filterRequest = filterRequest)
                },
                cancelFilter = {
                    homeChallengesViewModel.discordFilter()
                    filterViewModel.resetToDefaultFilter()
                    showSheet = false
                }
            )


        }

}