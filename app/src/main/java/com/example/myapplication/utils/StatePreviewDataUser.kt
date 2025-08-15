package com.example.myapplication.utils

import com.example.myapplication.domain.model.PreviewDataUser
import com.example.myapplication.domain.model.login_model.LoginResponse

sealed class StatePreviewDataUser {
    object Idle : StatePreviewDataUser()
    object Loading : StatePreviewDataUser()
    data class Success(val data: PreviewDataUser) : StatePreviewDataUser()
    data class Failure(val data: PreviewDataUser) : StatePreviewDataUser()
}
