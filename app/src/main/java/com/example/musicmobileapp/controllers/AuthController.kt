package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.musicmobileapp.models.UserModel
import com.example.musicmobileapp.network.AuthApiInterface
import com.example.musicmobileapp.network.MainAPIController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthController(private val authApiInterface: AuthApiInterface) : ViewModel()
{
    private val cScope = CoroutineScope(Dispatchers.IO)

    private  var data : Response<ResponseBody>? = null

    fun test()
    {
        val user = UserModel("zgleb222asddssad3@bk.ru","Kabanasda","11111111")
        cScope.launch {
            try {
                data = authApiInterface.reg(user)
            }
            catch (e: Exception)
            {
                e.printStackTrace()
            }
            withContext(Dispatchers.IO)
            {
                println(data?.raw().toString())
            }
        }
    }
}