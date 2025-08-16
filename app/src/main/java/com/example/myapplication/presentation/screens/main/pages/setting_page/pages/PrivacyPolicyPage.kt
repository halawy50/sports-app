package com.example.myapplication.presentation.screens.main.pages.setting_page.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.*
import com.example.myapplication.R
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.HeaderTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyPage(
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = grayWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 15.dp)
        ) {

            HeaderTopBar(
                onClick = {
                    navController.popBackStack()
                },
                title = stringResource(R.string.privacy_policy)
            )//end Header

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = white),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState)
                ) {

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = stringResource(R.string.last_update),
                        fontSize = 14.sp,
                        color = blue,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    SectionTitle(stringResource(R.string.privacy_section1_title))
                    Paragraph(stringResource(R.string.privacy_section1_text))

                    SectionTitle(stringResource(R.string.privacy_section2_title))
                    Paragraph(stringResource(R.string.privacy_section2_text))

                    SectionTitle(stringResource(R.string.privacy_section3_title))
                    Paragraph(stringResource(R.string.privacy_section3_text))

                    SectionTitle(stringResource(R.string.privacy_section4_title))
                    Paragraph(stringResource(R.string.privacy_section4_text))

                    SectionTitle(stringResource(R.string.privacy_section5_title))
                    Paragraph(stringResource(R.string.privacy_section5_text))

                    SectionTitle(stringResource(R.string.privacy_section6_title))
                    Paragraph(stringResource(R.string.privacy_section6_text))

                    SectionTitle(stringResource(R.string.privacy_section7_title))
                    Paragraph(stringResource(R.string.privacy_section7_text))

                    SectionTitle(stringResource(R.string.privacy_section8_title))
                    Paragraph(stringResource(R.string.privacy_section8_text))

                    SectionTitle(stringResource(R.string.privacy_section9_title))
                    Paragraph(stringResource(R.string.privacy_section9_text))

                    SectionTitle(stringResource(R.string.privacy_section10_title))
                    Paragraph(stringResource(R.string.privacy_section10_text))

                    SectionTitle(stringResource(R.string.privacy_section11_title))
                    Paragraph(stringResource(R.string.privacy_section11_text))
                }

                Spacer(modifier = Modifier.height(15.dp))

            }

        }
    }
}


@Composable
fun SectionTitle(text: String) {
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = black
    )
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun Paragraph(text: String) {
    Text(
        text = text.trim(),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = gray
    )
    Spacer(modifier = Modifier.height(8.dp))
}
