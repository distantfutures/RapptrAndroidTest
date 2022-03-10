package com.datechnologies.androidtest.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.datechnologies.androidtest.api.ChatApi
import com.datechnologies.androidtest.api.ChatLogMessageModel
import com.datechnologies.androidtest.api.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel(){
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    private val _responseTime = MutableLiveData<Long>()
    val responseTime: LiveData<Long>
        get() = _responseTime

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse


    fun sendLoginInfo(email: String, pw: String) {
        Log.i("LoginVMTest", "Api Called! $email $pw")
        coroutineScope.launch {
            val response = ChatApi.retrofitService.sendLoginInfo(email, pw)
            if (response.isSuccessful) {
                _loginResponse.value = response.body()
                _responseTime.value = apiResponseMilli(response)
//                val endpoint = response.body()
//                Log.i("LoginVMTest", "Code: ${endpoint?.code} Message: ${endpoint?.message}")
//                val responseMilli = apiResponseMilli(response)
//                Log.i("LoginVMTest", "Response Time: $responseMilli milli")
            } else {
                _responseTime.value = apiResponseMilli(response)
                clear()
                Log.i("LoginVMTest", "Failed!")
            }
        }
    }
    fun clear(){
        _loginResponse.value = null
    }
    private fun apiResponseMilli(response: Response<LoginResponse>) =
        response.raw().receivedResponseAtMillis() - response.raw().sentRequestAtMillis()
}