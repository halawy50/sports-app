package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.presentation.constant.CitiesConstant
import com.example.myapplication.presentation.constant.GendersConstant
import com.example.myapplication.presentation.constant.OnBoardingConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConstantDataViewModel @Inject constructor(
    private val dataConstant: GendersConstant,
    private val onBoardingConstant: OnBoardingConstant,
    private val citiesConstant: CitiesConstant
): ViewModel() {
    val genderList = dataConstant.list
    val onBoardingList = onBoardingConstant.list
    val citiesList = citiesConstant.list
}