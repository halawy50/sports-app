package com.example.myapplication.presentation.screens.main.homePage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.HomeComponents.EmptyChallenges
import com.example.myapplication.presentation.components.HomeComponents.HeaderHome
import com.example.myapplication.presentation.components.HomeComponents.ItemChallenger
import com.example.myapplication.presentation.components.HomeComponents.LoadingHome
import com.example.myapplication.presentation.viewmodel.HomePostViewModel
import com.example.myapplication.utils.StateGetPosts
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomePage(homePostViewModel: HomePostViewModel = hiltViewModel()) {
    val listState = rememberLazyListState()
    val isRefreshing = remember { mutableStateOf(false) }

    val stateGetPosts by homePostViewModel.stateGetPosts.collectAsState()
    val allPosts by homePostViewModel.posts.collectAsState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val threshold = 3
                val isLoading = stateGetPosts is StateGetPosts.Loading

                if (!isLoading && lastVisibleIndex != null && lastVisibleIndex >= totalItems - threshold) {
                    homePostViewModel.getAllPost()
                }
            }
    }


    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing.value),
        onRefresh = {
            isRefreshing.value = true
            homePostViewModel.restartCounterPage()
            homePostViewModel.getAllPost()
            isRefreshing.value = false
        }

    ) {
        LazyColumn(state = listState) {
            item {
                Spacer(Modifier.height(30.dp))

                HeaderHome()
                Spacer(Modifier.height(30.dp))
            }

            itemsIndexed(allPosts) { index, item ->
                ItemChallenger(post = item)
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
                            // عرض رسالة صغيرة مثلاً
                        } else {
                            Text("حدث خطأ ما", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                            Spacer(Modifier.height(15.dp))
                            ButtonFill(
                                onClick = {
                                    homePostViewModel.getAllPost()
                                },
                                label = "اعد المحاولة"
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}