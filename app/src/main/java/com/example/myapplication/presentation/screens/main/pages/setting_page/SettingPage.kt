package com.example.myapplication.presentation.screens.main.settingPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.R
import com.example.myapplication.presentation.components.HomeComponents.CardHomePageShimmer
import com.example.myapplication.presentation.viewmodel.SettingViewModel
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.ui.theme.black
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.utils.StateGetPosts
import com.example.myapplication.utils.StatePreviewDataUser
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.HomeComponents.EmptyChallenges
import com.example.myapplication.presentation.components.HomeComponents.HeaderHome
import com.example.myapplication.presentation.components.HomeComponents.ItemChallenger
import com.example.myapplication.presentation.components.HomeComponents.LoadingHome
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SettingPage(
    settingViewModel: SettingViewModel = hiltViewModel()
){

    val statePreviewDataUser by settingViewModel.statePreviewDataUser.collectAsState()
    val listState = rememberLazyListState()
    val isRefreshing = remember { mutableStateOf(false) }
    val stateGetPosts by settingViewModel.stateGetPosts.collectAsState()
    val allPosts by settingViewModel.posts.collectAsState()


    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val threshold = 3
                val isLoading = stateGetPosts is StateGetPosts.Loading

                if (!isLoading && lastVisibleIndex != null && lastVisibleIndex >= totalItems - threshold) {
                    settingViewModel.getAllPostUser()
                }
            }
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing.value),
        onRefresh = {
            isRefreshing.value = true
            settingViewModel.restartCounterPage()
            settingViewModel.getAllPostUser()
            isRefreshing.value = false
        },
        modifier = Modifier.fillMaxSize()

    ) {

            LazyColumn(state = listState) {
                item {
                    Spacer(Modifier.height(30.dp))

                    when (val state = statePreviewDataUser) {
                        is StatePreviewDataUser.Loading -> {
                            CardHomePageShimmer()
                        }

                        is StatePreviewDataUser.Success -> {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {


                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(10000.dp))
                                ) {
                                    if (state.data.gender == 0) {
                                        Image(
                                            painter = painterResource(R.drawable.male_avatar),
                                            contentDescription = "male_avatar.png",
                                            contentScale = ContentScale.Crop
                                        )
                                    } else if (state.data.gender == 1) {
                                        Image(
                                            painter = painterResource(R.drawable.female_avater),
                                            contentDescription = "female_avater.png",
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(R.drawable.account),
                                            contentDescription = "account.png",
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }

                                Spacer(Modifier.width(8.dp))

                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    //name
                                    Text(
                                        text = state.data.name,
                                        style = TextStyle(
                                            fontFamily = almarai_regular,
                                            color = black,
                                            fontSize = 16.sp,

                                            ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Spacer(modifier = Modifier.height(3.dp))

                                    //date public post
                                    Text(
                                        text = state.data.email,
                                        style = TextStyle(
                                            fontFamily = almarai_regular,
                                            color = gray,
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }
                        }

                        is StatePreviewDataUser.Failure -> {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {


                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(10000.dp))
                                ) {

                                    Image(
                                        painter = painterResource(R.drawable.account),
                                        contentDescription = "account.png",
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Spacer(Modifier.width(8.dp))

                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    //name
                                    Text(
                                        text = stringResource(R.string.undefined),
                                        style = TextStyle(
                                            fontFamily = almarai_regular,
                                            color = black,
                                            fontSize = 16.sp,

                                            ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    //date public post
                                    Text(
                                        text = stringResource(R.string.undefined),
                                        style = TextStyle(
                                            fontFamily = almarai_regular,
                                            color = gray,
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }
                        }

                        else -> {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {


                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(10000.dp))
                                ) {

                                    Image(
                                        painter = painterResource(R.drawable.account),
                                        contentDescription = "account.png",
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Spacer(Modifier.width(8.dp))

                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    //name
                                    Text(
                                        text = stringResource(R.string.undefined),
                                        style = TextStyle(
                                            fontFamily = almarai_regular,
                                            color = black,
                                            fontSize = 16.sp,

                                            ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    //date public post
                                    Text(
                                        text = stringResource(R.string.undefined),
                                        style = TextStyle(
                                            fontFamily = almarai_regular,
                                            color = gray,
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }
                        }
                    }

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
                                        settingViewModel.getAllPostUser()
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