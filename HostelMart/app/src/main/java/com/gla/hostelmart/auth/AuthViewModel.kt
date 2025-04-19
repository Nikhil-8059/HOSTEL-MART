package com.gla.hostelmart.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.GoTrueApi
import io.github.jan.supabase.gotrue.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val supabaseClient: SupabaseClient) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val goTrueApi: GoTrueApi = supabaseClient.gotrue

    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = goTrueApi.signUp(email, password)
                if (response.user != null) {
                    _user.value = response.user
                    onResult(true, null)
                } else {
                    onResult(false, response.error?.message)
                }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun signIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = goTrueApi.signIn(email, password)
                if (response.user != null) {
                    _user.value = response.user
                    onResult(true, null)
                } else {
                    onResult(false, response.error?.message)
                }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            goTrueApi.signOut()
            _user.value = null
        }
    }
}
