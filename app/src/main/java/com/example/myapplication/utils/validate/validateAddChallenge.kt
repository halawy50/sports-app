package com.example.myapplication.utils.validate

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest

fun validateAddChallenge(
    challengeRequest: ChallengeRequest,
    setGenderError: (WrongVerify) -> Unit,
    setGovernorateError: (WrongVerify) -> Unit,
    setCityError: (WrongVerify) -> Unit,
    setTeamChallengeError: (WrongVerify) -> Unit,
    setWhatsUpError: (WrongVerify) -> Unit,
    context: Context
): ChallengeRequest? {
    var hasError = false


    // Validate WhatsApp number
    val regex = Regex("^(010|011|012|015)\\d{8}$")
    if (!regex.matches(challengeRequest.whatsUpNumber)) {
        setWhatsUpError(
            WrongVerify(
                true,
                context.getString(R.string.error_invalid_whatsapp)
            )
        )
        hasError = true
    } else {
        setWhatsUpError(WrongVerify(false, ""))
    }


    // Validate Gender
    if (challengeRequest.genderChallengeIndex !in 0..1) {
        setGenderError(WrongVerify(true, context.getString(R.string.error_empty_gender)))
        hasError = true
    } else {
        setGenderError(WrongVerify(false, ""))
    }

    // Validate Governorate
    if (challengeRequest.governorateId<0) {
        setGovernorateError(WrongVerify(true, context.getString(R.string.error_empty_governorate)))
        hasError = true
    } else {
        setGovernorateError(WrongVerify(false, ""))
    }

    // Validate City
    if (challengeRequest.cityId<0) {
        setCityError(WrongVerify(true, context.getString(R.string.error_empty_city)))
        hasError = true
    } else {
        setCityError(WrongVerify(false, ""))
    }

    // Validate Team Challenge
    if (challengeRequest.team<0) {
        setTeamChallengeError(WrongVerify(true, context.getString(R.string.error_select_team_challenge)))
        hasError = true
    } else {
        setTeamChallengeError(WrongVerify(false, ""))
    }



    return if (hasError) null else challengeRequest
}

