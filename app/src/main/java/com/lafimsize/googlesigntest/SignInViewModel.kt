package com.lafimsize.googlesigntest

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel:ViewModel() {

    private val _state= MutableStateFlow(SignInState())
    val state=_state.asStateFlow()

    fun onSignInResult(result: SignInResult){
        _state.update {
            it.copy(
                isSigned = result.userData != null,//burada veri null değilse true döner
                errorMsg = result.errorMsg
            )
        }
    }

    //başka bir yere navigate ederse kullanıcı geri geldiğinde hatalar olabilir
    //bu yüzden resetleyip default değerlere döndürmeliyiz.
    fun resetState(){
        _state.update { SignInState() }
    }



}