package com.example.myapplication.presentation.screens.main.homePage

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.components.GifFromDrawable
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.R
import com.example.myapplication.presentation.components.HomeComponents.CardHomePageShimmer
import com.example.myapplication.presentation.components.HomeComponents.EmptyChallenges
import com.example.myapplication.presentation.components.HomeComponents.ItemChallenger
import com.example.myapplication.presentation.components.HomeComponents.LoadingHome
import com.example.myapplication.presentation.viewmodel.HomeViewModel
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.ui.theme.white
import com.example.myapplication.utils.FetchDataState
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomePage(homeViewModel: HomeViewModel = hiltViewModel()){
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val fetchDataState by homeViewModel.fetchDataState.collectAsState()


    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(Modifier.height(40.dp))

            //Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(0.7f)) {
                    HeaderText(
                        text = stringResource(R.string.app_name),
                        textAlign = TextAlign.Start
                    )
                    Spacer(Modifier.height(8.dp))
                    ParagraphText(
                        text = stringResource(id = R.string.intro_paragraph),
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(modifier = Modifier.weight(0.05f))

                IconButton(
                    modifier = Modifier.weight(0.1f)
                        .size(40.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(gray),
                    onClick = { }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filters),
                        contentDescription = stringResource(R.string.filter),
                        tint = white
                        )
                }

            }//end Header



            Spacer(Modifier.height(40.dp))


//            if (fetchDataState == FetchDataState.LOADING)
//                LoadingHome()
//            else if(fetchDataState == FetchDataState.EMPTY)
//                EmptyChallenges()
//            else
                ItemChallenger()

            Spacer(Modifier.height(40.dp))

        }
    }

}