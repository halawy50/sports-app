package com.example.myapplication.presentation.constant

import com.example.myapplication.domain.model.EntryModel


//Gender List
fun genderList() = listOf<EntryModel>(
        EntryModel(index = 0, titleAr = "ذكر", titleEn = "Male"),
        EntryModel(index = 1, titleAr = "أنثي", titleEn = "Female"),
)


fun challengeTeamList() = List(11) { index ->
        val number = index + 1
        EntryModel(
                index = index,
                titleAr = "$number × $number",
                titleEn = "$number × $number"
        )
}

fun orderList() = listOf<EntryModel>(
        EntryModel(index = 0, titleAr = "الاحدث للأقدم", titleEn = "newest to oldest"),
        EntryModel(index = 1, titleAr = "الاقدم للأحدث", titleEn = "oldest to newest"),
)

