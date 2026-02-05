package com.example.shopmagazine.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImagePainter
import com.example.shopmagazine.data.auth.TokenManager
import com.example.shopmagazine.data.network.AuthApi
import com.example.shopmagazine.data.remote.dto.LoginRequestDto
import com.example.shopmagazine.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
): ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()
    fun login(username:String,password: String){
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = authApi.login(LoginRequestDto(username, password))
                tokenManager.saveToken(response.token)
                _loginState.value = LoginState.Success
            }catch (e: Exception){
                _loginState.value = LoginState.Error("Error ${e.message}")
            }
        }
    }
}

sealed class LoginState{
    object Idle: LoginState()
    object Loading: LoginState()
    object Success: LoginState()
    data class Error(val message: String): LoginState()
}