package com.example.myapplication.presentation.components.HomeComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.viewmodel.FilterViewModel
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.ui.theme.white


@Composable
fun HeaderHome(
    onClick : () -> Unit,
    isFilter: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
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

            // الرقم أعلى الصندوق بالكامل
            Box(
                modifier = Modifier
                    .weight(0.1f)
                    .height(40.dp) // ارتفاع الصندوق الرمادي
            ) {
                // الصندوق الرمادي مع أيقونة الفلتر
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(5.dp))
//                        .background(gray)
                ) {
                    IconButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = onClick
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.filters),
                            contentDescription = stringResource(R.string.filter),
                            tint = gray
                        )
                    }
                }

                if (isFilter)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(blue, RoundedCornerShape(50))
                        .size(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "1",
                        color = white,
                        style = TextStyle(fontSize = 10.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }

    }
}
