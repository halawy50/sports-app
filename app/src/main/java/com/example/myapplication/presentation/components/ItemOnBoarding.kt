package com.example.myapplication.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.domain.model.OnBoardingModel
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonWithBorder
import com.example.myapplication.ui.theme.white
import kotlinx.coroutines.launch


@Composable
fun ItemOnBoarding(
    onBoardingModel: OnBoardingModel,
    isFinish: Boolean,
    padding: PaddingValues,
    pagerState: PagerState,
    onFinish: () -> Unit
){
    val scope = rememberCoroutineScope()


    Box(
        modifier = Modifier.background(white).fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {

        // صورة الخلفية
        Image(
            painter = painterResource(id = onBoardingModel.image),
            contentDescription = "on Boarding Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(modifier = Modifier.padding(padding)){
            Card(
                modifier = Modifier
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
            ) {
                Column(
                    modifier = Modifier.background(white)
                        .height(350.dp)
                        .padding(horizontal = 20.dp, vertical = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Column(
                        modifier = Modifier.weight(0.7f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        HeaderText(onBoardingModel.title)
                        Spacer(modifier = Modifier.height(20.dp))
                        ParagraphText(onBoardingModel.description)

                    }

                    //Next
                    ButtonFill(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2f),

                        onClick = {
                            scope.launch {
                                if (isFinish) {
                                    onFinish()
                                } else {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },

                        text = if (isFinish) {
                            stringResource(R.string.finish)
                        } else {
                            stringResource(R.string.next)
                        },
                    )

                    if (pagerState.currentPage>0)
                    //Previous
                        ButtonWithBorder(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.2f),

                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            },

                            text = stringResource(R.string.previous)
                        )

                }
            }
        }

    }
}
