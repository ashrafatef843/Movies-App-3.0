package com.example.movieapp3.common.presentation

import android.content.Context
import android.widget.Toast
import com.example.movieapp3.common.data.errors.NetworkException
import com.example.movieapp3.common.data.errors.UnauthorizedException

/**
 * Handle custom http errors
 */
fun Context.handleHttpError(e: Throwable) {
    Toast.makeText(this,
    when (e) {
        is NetworkException -> "Check your internet connection"
        is UnauthorizedException -> "Unauthorized Client"
        else -> "Unknown error please try again"
    },
        Toast.LENGTH_LONG
    ).show()
}
