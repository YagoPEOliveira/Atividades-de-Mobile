package com.example.aulacaiquethiago

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // Mutável internamente
    private val _uiState = MutableStateFlow(TrakinasMentindo())

    // Exposição imutável para a UI
    val uiState: StateFlow<TrakinasMentindo> = _uiState.asStateFlow()

    // 1. Ação vinda da UI
    fun aumentar() {
        // 2. Processamento e 3. emissão do novo estado com .copy()
        _uiState.update { it.copy(contador = it.contador + 1) }
    }

    // Simula uma requisição para demonstrar isLoading, items e error
    fun carregarItens() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(2000)
            _uiState.update {
                it.copy(isLoading = false, items = listOf("Produto A", "Produto B", "Produto C"))
            }
        }
    }
}