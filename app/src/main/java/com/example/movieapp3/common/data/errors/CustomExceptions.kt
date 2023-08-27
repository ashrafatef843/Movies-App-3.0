package com.example.movieapp3.common.data.errors

import java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT

/**
 * Exception represents IO exception or Http exception with code [HTTP_GATEWAY_TIMEOUT]
 */
class NetworkException : Exception("Network Exception")

/**
 * Exception represents unauthorized user
 */
class UnauthorizedException : Exception("Unauthorized Exception")

/**
 * Exception represents any unknown Exception
 */
class UnknownException(message: String) : Exception(message)
