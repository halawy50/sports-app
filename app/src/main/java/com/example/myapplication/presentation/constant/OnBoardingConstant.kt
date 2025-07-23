package com.example.myapplication.presentation.constant

import android.content.Context
import com.example.myapplication.domain.model.OnBoardingModel
import com.example.myapplication.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OnBoardingConstant @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val list = listOf<OnBoardingModel>(
            OnBoardingModel(
                title = context.getString(R.string.onboarding_title1) ,
                description = context.getString(R.string.onboarding_description1),
                image = R.drawable.onboarding1
            ),
            OnBoardingModel(
                title = context.getString(R.string.onboarding_title2) ,
                description = context.getString(R.string.onboarding_description2),
                image = R.drawable.onboarding2
            ),
            OnBoardingModel(
                title = context.getString(R.string.onboarding_title3) ,
                description = context.getString(R.string.onboarding_description3),
                image = R.drawable.onboarding3
            ),
        )
}