package com.example.aulacaiquethiago

data class TrakinasMentindo(
    val isLoading: Boolean = false,
    val items: List<String> = emptyList(),
    val error: String? = null,
    val contador: Int = 0
)
