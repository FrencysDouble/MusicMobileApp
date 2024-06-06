package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.musicmobileapp.models.dto.UserModel
import com.example.musicmobileapp.network.AuthApiInterface
import com.example.musicmobileapp.network.api.ApiResponse
import com.example.musicmobileapp.security.UserSecurityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import okhttp3.ResponseBody
import retrofit2.Response

class AuthController(
    private val authApiInterface: AuthApiInterface,
    private val userSecurityManager: UserSecurityManager
)
    : ViewModel()
{
    private val cScope = CoroutineScope(Dispatchers.IO)


    fun auth()
    {
        val user = UserModel("zgleb@bk.ru","Glebas","24092002")
        cScope.launch {
            authApiInterface.auth(user).collect() { response ->
                withContext(Dispatchers.Main)
                {
                    when(response){
                        is ApiResponse.Success ->{
                            userSecurityManager.saveUserSession(
                             response.data.id,response.data.uuid
                            )
                        }
                        is ApiResponse.Error ->{
                            Log.d("AuthController",response.errorMessage)
                        }
                        is ApiResponse.Loading ->{
                            TODO()
                        }
                    }
                }
            }
        }
    }
}