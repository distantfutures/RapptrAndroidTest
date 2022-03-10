package com.datechnologies.androidtest.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.datechnologies.androidtest.MainActivity
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.api.ChatApi
import com.datechnologies.androidtest.api.LoginResponse
import com.datechnologies.androidtest.databinding.ActivityChatBinding
import com.datechnologies.androidtest.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        binding.lifecycleOwner = this

        loginViewModel = LoginViewModel()

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.

        binding.buttonLogIn.setOnClickListener {
            val email = binding.userNameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            loginViewModel.sendLoginInfo(email, password)
            Log.i("LoginActTest", "Email: $email Password: $password")
        }
        // if responseTime changes & loginResponse exists. Alert is triggered.
        // if loginResponse is null, means invalid
        loginViewModel.responseTime.observe(this, Observer { respTime ->
            val loginResponse = loginViewModel.loginResponse.value
            if (loginResponse != null) {
                loginAlert(respTime, loginResponse)
            } else {
                Toast.makeText(this, "User Name and/or Password Invalid!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loginAlert(respTime: Long, response: LoginResponse) {
        val loginDialog = AlertDialog.Builder(this)
            .setTitle("Login Status")
            .setMessage("Code: ${response.code} \n Message: ${response.message} \n API Call Time: $respTime")
            .setPositiveButton("OK") { _, _ ->
                onBackPressed()
                loginViewModel.clear()
            }.create()
        loginDialog.show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        //==============================================================================================
        // Static Class Methods
        //==============================================================================================
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
}