package com.gaby.logininstagram.login.ui


import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaby.logininstagram.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {

    //EN EL VIEW MODEL ES DONDE DEBE ESTAR TODA LA LOGICA DEL PROYECTO

    // Construimos una variable MutableLiveData privada que solo se pueda acceder desde dentro del ViewModel
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _isLoginEnabled = MutableLiveData<Boolean>()
    private val _isLoading = MutableLiveData<Boolean>()

    //Construimos otro que este si que podrá ser llamado desde otro sitio.
    val email : LiveData<String> = _email
    val password : LiveData<String> = _password
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChanged(email:String,password:String){

        _email.value = email
        _password.value = password
        //Comprobamos mail y password con el metodo enableLogin
        _isLoginEnabled.value = enableLogin(email,password)
    }

    fun enableLogin(email:String, password: String):Boolean{

        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 5

    }

    fun onLoginSelected(){
        viewModelScope.launch {

            _isLoading.value = true
            val result = loginUseCase(email.value!!,password.value!!)
            if(result){
                //Navegar a la siguiente pantalla
                Log.i("Gaby","Result OK")
            }
            _isLoading.value = false
        }
    }


}