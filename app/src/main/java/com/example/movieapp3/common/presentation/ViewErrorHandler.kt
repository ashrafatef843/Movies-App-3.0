package com.example.movieapp3.common.presentation

import com.example.movieapp3.R
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import com.example.movieapp3.common.data.errors.NetworkException
import com.example.movieapp3.common.data.errors.UnauthorizedException

/**
 * Handle custom http errors
 */
fun Context.handleHttpError(e: Throwable) {
    Toast.makeText(this,
    when (e) {
        is NetworkException -> getString(R.string.msg_network_error)
        is UnauthorizedException -> getString(R.string.msg_unauthorized)
        else -> getString(R.string.msg_something_wrong)
    },
        Toast.LENGTH_LONG
    ).show()
}
