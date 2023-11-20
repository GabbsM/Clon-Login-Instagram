package com.gaby.logininstagram.login.data

import com.gaby.logininstagram.login.data.network.response.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api:LoginService){

    suspend fun doLogin(user:String,password:String):Boolean{

        return api.doLogin(user,password)
    }

}