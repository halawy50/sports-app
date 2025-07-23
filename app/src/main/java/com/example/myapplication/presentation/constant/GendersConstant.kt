package com.example.myapplication.presentation.constant

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.model.EntryModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GendersConstant @Inject constructor(
    @ApplicationContext private val context: Context
) {
    //Gender List
    val list = listOf<EntryModel>(
        EntryModel(index = 0, title= context.getString(R.string.male)),
        EntryModel(index = 1, title= context.getString(R.string.female)),
    )

}