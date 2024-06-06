package com.example.musicmobileapp.network.api

import retrofit2.Response


suspend fun <T, R> handleApiResponse(
    call: suspend () -> Response<T>,
    map: (T) -> R
): ApiResponse<R> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                ApiResponse.Success(map(it))
            } ?: ApiResponse.Error("Empty body", response.code())
        } else {
            ApiResponse.Error("API call failed with error: ${response.message()}", response.code())
        }
    } catch (e: Exception) {
        ApiResponse.Error("Exception occurred: ${e.message}")
    }
}

suspend fun <T> handleApiResponse(
    call: suspend () -> Response<T>
): ApiResponse<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                ApiResponse.Success(it)
            } ?: ApiResponse.Error("Empty body", response.code())
        } else {
            ApiResponse.Error("API call failed with error: ${response.message()}", response.code())
        }
    } catch (e: Exception) {
        ApiResponse.Error("Exception occurred: ${e.message}")
    }
}

suspend fun <T, R> handleMultipleApiResponses(
    calls: List<suspend () -> Response<T>>,
    map: (List<T>) -> R
): ApiResponse<R> {
    return try {
        val results = mutableListOf<T>()

        for (call in calls) {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    results.add(it)
                } ?: return ApiResponse.Error("Empty body", response.code())
            } else {
                return ApiResponse.Error("API call failed with error: ${response.message()}", response.code())
            }
        }

        ApiResponse.Success(map(results))
    } catch (e: Exception) {
        ApiResponse.Error("Exception occurred: ${e.message}")
    }
}


