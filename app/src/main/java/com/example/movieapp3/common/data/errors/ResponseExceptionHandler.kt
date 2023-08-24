package com.example.movieapp3.common.data.errors

import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

/**
 * Convert error returned from http call to custom exception
 *
 * @return
 * [NetworkException]
 *
 * [HTTP_UNAUTHORIZED]
 *
 * [UnknownException]
 */
fun Exception.handleHttpException(): Throwable {
    return when (this) {
        is HttpException -> {
            when {
                code() == HTTP_GATEWAY_TIMEOUT -> {
                    NetworkException()
                }

                code() == HTTP_UNAUTHORIZED -> {
                    UnauthorizedException()
                }

                else -> {
                    UnknownException(this.message ?: this.stackTraceToString())
                }
            }
        }

        is IOException -> {
            NetworkException()
        }

        else -> {
            UnknownException(this.message ?: this.stackTraceToString())
        }
    }
}
