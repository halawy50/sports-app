package com.example.myapplication.presentation.constant

import android.content.Context
import com.example.myapplication.domain.model.OnBoardingModel
import com.example.myapplication.R


fun onBoardingList(context: Context): List<OnBoardingModel> {
        return listOf(
            OnBoardingModel(
                title = context.getString(R.string.onboarding_title1),
                description = context.getString(R.string.onboarding_description1),
                image = R.drawable.onboarding1
            ),
            OnBoardingModel(
                title = context.getString(R.string.onboarding_title2),
                description = context.getString(R.string.onboarding_description2),
                image = R.drawable.onboarding2
            ),
            OnBoardingModel(
                title = context.getString(R.string.onboarding_title3),
                description = context.getString(R.string.onboarding_description3),
                image = R.drawable.onboarding3
            )
        )
}
