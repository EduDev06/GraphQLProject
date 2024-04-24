package com.example.graphqlproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlproject.domain.model.User
import com.example.graphqlproject.domain.use_case.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    private val _userState: MutableStateFlow<UserState> = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    fun fetchUser(text: String) {
        if (text.isEmpty()) return

        viewModelScope.launch {
            _userState.update { it.copy(isLoading = true) }
            getUserUseCase(text.toInt()).collect { result ->
                result.onSuccess { user ->
                    _userState.update { it.copy(user = user, isLoading = false) }
                }.onFailure {
                    _userState.update { it.copy(user = null, isLoading = false) }
                }
            }
        }
    }
}

data class UserState(
    val user: User? = null,
    val isLoading: Boolean = false
)