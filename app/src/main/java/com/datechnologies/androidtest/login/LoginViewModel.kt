package com.datechnologies.androidtest.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.datechnologies.androidtest.api.AppApi
import com.datechnologies.androidtest.api.data.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    private val _responseTime = MutableLiveData<Long>()
    val responseTime: LiveData<Long>
        get() = _responseTime

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse

    // Sends Login Info to serve and receives responses that sets to LiveData
    fun sendLoginInfo(email: String, pw: String) {
        Log.i("LoginVMTest", "Api Called! $email $pw")
        coroutineScope.launch {
            val response = AppApi.RETROFIT_SERVICE.sendLoginInfo(email, pw)
            if (response.isSuccessful) {
                _loginResponse.value = response.body()
                _responseTime.value = apiResponseMilli(response)
            } else {
                _responseTime.value = apiResponseMilli(response)
                clear()
                Log.i("LoginVMTest", "Failed!")
            }
        }
    }

    fun clear() {
        _loginResponse.value = null
    }

    private fun apiResponseMilli(response: Response<LoginResponse>) =
        response.raw().receivedResponseAtMillis() - response.raw().sentRequestAtMillis()
}