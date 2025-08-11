package com.example.myapplication.presentation.screens.main.pages.setting_page.pages


import android.app.Activity
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.HomeComponents.EmptyChallenges
import com.example.myapplication.presentation.components.HomeComponents.ItemChallenger
import com.example.myapplication.presentation.components.HomeComponents.LoadingHome
import com.example.myapplication.presentation.viewmodel.PostsUserViewModel
import com.example.myapplication.utils.StateGetPosts
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.myapplication.R
import com.example.myapplication.presentation.components.HideStatusBar

@Composable
fun PostsUserPage(
    postsUserViewModel: PostsUserViewModel = hiltViewModel(),
    navController: NavController,
    activity: Activity

){

    val listState = rememberLazyListState()
    val isRefreshing = remember { mutableStateOf(false) }
    val stateGetPosts by postsUserViewModel.stateGetPosts.collectAsState()
    val allPosts by postsUserViewModel.posts.collectAsState()

    HideStatusBar(activity = activity)

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val threshold = 3
                val isLoading = stateGetPosts is StateGetPosts.Loading

                if (!isLoading && lastVisibleIndex != null && lastVisibleIndex >= totalItems - threshold) {
                    postsUserViewModel.getAllPostUser()
                }
            }
    }

    Scaffold { innerPadding->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing.value),
            onRefresh = {
                isRefreshing.value = true
                postsUserViewModel.restartCounterPage()
                postsUserViewModel.getAllPostUser()
                isRefreshing.value = false
            },
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .padding(start = 20.dp, end = 10.dp)

        ) {

            LazyColumn(state = listState) {

                item {
                    Spacer(Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        HeaderText(text = stringResource(R.string.my_challenges))

                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.graphicsLayer(rotationZ = 180f) // يدور السهم 180 درجة

                            )
                        }

                    }

                    Spacer(Modifier.height(20.dp))


                }



                itemsIndexed(allPosts) { index, item ->
                    Box(
                        modifier = Modifier.padding(end = 0.dp)
                    ){
                        ItemChallenger(post = item)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    when (val state = stateGetPosts) {
                        is StateGetPosts.Loading -> {
                            LoadingHome()
                        }

                        is StateGetPosts.NULL -> {
                            EmptyChallenges()
                        }

                        is StateGetPosts.Failure -> {
                            if (allPosts.isNotEmpty()) {
                            } else {
                                Text(stringResource(R.string.something_wrong), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                                Spacer(Modifier.height(15.dp))
                                ButtonFill(
                                    onClick = {
                                        postsUserViewModel.getAllPostUser()
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
    }

}