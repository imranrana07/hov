package com.imran.hov.data.api

import com.imran.hov.utils.ApiException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException

object ApiResponse {
    suspend fun <T : Any> getResult(call: suspend () -> Response<T>): T {

        val response = try {
            call.invoke()
        } catch (e: SocketException) {
            throw ApiException(e.localizedMessage!!)
        } catch (e: IOException) {
            throw ApiException(e.localizedMessage!!)
        } catch (e: SocketTimeoutException) {
            throw ApiException(e.localizedMessage!!)
        } catch (e: Exception) {
            throw ApiException(e.localizedMessage!!)
        }

        if (response.isSuccessful) {
            return response.body()!!
        } else if (response.code() == 503) {
            throw ApiException("Service temporarily unavailable")
        }else if (
            response.code() == 400 || response.code() == 403 || response.code() == 404 ||
            response.code() == 406 || response.code() == 422 || response.code() == 503 || response.code() == 501||
            response.code() == 500
        ){
            throw ApiException("Something went wrong")
        } else {
            val errorBody = JSONObject(response.errorBody()?.string()!!)
            if (errorBody.has("error_description")) {
                val errorMessage = errorBody.optString("error_description")
                throw ApiException(errorMessage)
            } else {
                val errorTitle = errorBody.keys().next()
                val errorMessage = errorBody.optString(errorTitle)
                throw ApiException(errorMessage.toString())
            }
        }
    }
}