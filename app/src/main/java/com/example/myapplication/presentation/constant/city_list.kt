package com.example.myapplication.presentation.constant

import android.content.Context
import com.example.myapplication.domain.model.EntryModel
import com.example.myapplication.R


fun cityList(context: Context) = listOf<EntryModel>(
        EntryModel(0, context.getString(R.string.city_cairo)),
        EntryModel(1, context.getString(R.string.city_giza)),
        EntryModel(2, context.getString(R.string.city_alexandria)),
        EntryModel(3, context.getString(R.string.city_mansoura)),
        EntryModel(4, context.getString(R.string.city_tanta)),
        EntryModel(5, context.getString(R.string.city_monufia)),
        EntryModel(6, context.getString(R.string.city_sharqia)),
        EntryModel(7, context.getString(R.string.city_dakahlia)),
        EntryModel(8, context.getString(R.string.city_gharbia)),
        EntryModel(9, context.getString(R.string.city_kafr_sheikh)),
        EntryModel(10, context.getString(R.string.city_damietta)),
        EntryModel(11, context.getString(R.string.city_portsaid)),
        EntryModel(12, context.getString(R.string.city_ismailia)),
        EntryModel(13, context.getString(R.string.city_suez)),
        EntryModel(14, context.getString(R.string.city_beheira)),
        EntryModel(15, context.getString(R.string.city_matrouh)),
        EntryModel(16, context.getString(R.string.city_fayoum)),
        EntryModel(17, context.getString(R.string.city_beni_suef)),
        EntryModel(18, context.getString(R.string.city_minya)),
        EntryModel(19, context.getString(R.string.city_assiut)),
        EntryModel(20, context.getString(R.string.city_sohag)),
        EntryModel(21, context.getString(R.string.city_qena)),
        EntryModel(22, context.getString(R.string.city_luxor)),
        EntryModel(23, context.getString(R.string.city_aswan)),
        EntryModel(24, context.getString(R.string.city_new_valley)),
        EntryModel(25, context.getString(R.string.city_north_sinai)),
        EntryModel(26, context.getString(R.string.city_south_sinai)),
        EntryModel(27, context.getString(R.string.city_red_sea)))

