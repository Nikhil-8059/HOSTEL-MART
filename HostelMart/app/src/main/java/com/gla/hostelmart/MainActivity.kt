package com.gla.hostelmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.gla.hostelmart.auth.AuthScreen
import com.gla.hostelmart.auth.AuthViewModel
import com.gla.hostelmart.ui.theme.HostelMartTheme
import io.github.jan.supabase.SupabaseClient

class MainActivity : ComponentActivity() {

    private val supabaseClient = SupabaseClient(
        supabaseUrl = "https://your-supabase-url.supabase.co",
        supabaseKey = "your-anon-key"
    )

    private val authViewModel: AuthViewModel by viewModels {
        object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(supabaseClient) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HostelMartTheme {
                val user by authViewModel.user.collectAsState()
                if (user == null) {
                    AuthScreen(authViewModel = authViewModel) {
                        // On auth success callback
                    }
                } else {
                    // TODO: Show main app screen
                }
            }
        }
    }
}
