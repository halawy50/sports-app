package com.example.myapplication.utils.validate

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.domain.model.register_model.RegisterRequest

fun validateSignUpInputs(
    fullName: String,
    age: Int,
    gender: Gender,
    city: City,
    email: String,
    password: String,
    setFullNameError: (WrongVerify) -> Unit,
    setAgeError: (WrongVerify) -> Unit,
    setGenderError: (WrongVerify) -> Unit,
    setCityError: (WrongVerify) -> Unit,
    setEmailError: (WrongVerify) -> Unit,
    setPasswordError: (WrongVerify) -> Unit,
    context: Context
): RegisterRequest? {
    var hasError = false

    // Validate Full Name
    when {
        fullName.isBlank() -> {
            setFullNameError(WrongVerify(true, context.getString(R.string.error_empty_full_name)))
            hasError = true
        }
        fullName.length < 2 -> {
            setFullNameError(WrongVerify(true, context.getString(R.string.error_short_full_name)))
            hasError = true
        }
        else -> setFullNameError(WrongVerify(false, ""))
    }

    // Validate Age
    when {
        age <= 0 -> {
            setAgeError(WrongVerify(true, context.getString(R.string.error_empty_age)))
            hasError = true
        }
        age < 16 -> {
            setAgeError(WrongVerify(true, context.getString(R.string.error_underage)))
            hasError = true
        }
        age > 100 -> {
            setAgeError(WrongVerify(true, context.getString(R.string.error_invalid_age)))
            hasError = true
        }
        else -> setAgeError(WrongVerify(false, ""))
    }

    // Validate Gender
    if (gender.gender.isBlank()) {
        setGenderError(WrongVerify(true, context.getString(R.string.error_empty_gender)))
        hasError = true
    } else {
        setGenderError(WrongVerify(false, ""))
    }

    // Validate City
    if (city.city.isBlank()) {
        setCityError(WrongVerify(true, context.getString(R.string.error_empty_city)))
        hasError = true
    } else {
        setCityError(WrongVerify(false, ""))
    }

    // Validate Email
    when {
        email.isBlank() -> {
            setEmailError(WrongVerify(true, context.getString(R.string.error_empty_email)))
            hasError = true
        }
        !isValidEmail(email) -> {
            setEmailError(WrongVerify(true, context.getString(R.string.error_invalid_email)))
            hasError = true
        }
        else -> setEmailError(WrongVerify(false, ""))
    }

    // Validate Password
    when {
        password.isBlank() -> {
            setPasswordError(WrongVerify(true, context.getString(R.string.error_empty_password)))
            hasError = true
        }
        password.length < 8 -> {
            setPasswordError(WrongVerify(true, context.getString(R.string.error_short_password)))
            hasError = true
        }
        !isStrongPassword(password) -> {
            setPasswordError(WrongVerify(true, context.getString(R.string.error_weak_password)))
            hasError = true
        }
        else -> setPasswordError(WrongVerify(false, ""))
    }

    return if (hasError) null else RegisterRequest(
        fullName = fullName.trim(),
        gender = gender,
        city = city,
        age = age,
        email = email.trim().lowercase(),
        password = password
    )
}

private fun isValidEmail(email: String): Boolean {
    return email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
}

private fun isStrongPassword(password: String): Boolean {
    val hasLetter = password.any { it.isLetter() }
    val hasDigit = password.any { it.isDigit() }
    return hasLetter && hasDigit
}
