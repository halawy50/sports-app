package com.example.myapplication.presentation.constant

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.model.EntryModel


//Gender List
fun genderList(context: Context) = listOf<EntryModel>(
        EntryModel(index = 0, title= context.getString(R.string.male)),
        EntryModel(index = 1, title= context.getString(R.string.female)),
)

