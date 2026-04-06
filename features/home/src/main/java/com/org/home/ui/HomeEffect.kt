package com.org.home.ui

sealed interface HomeEffect {

    data object OnOpenBottomSheet : HomeEffect
    data class ShowConnectionError(val message: String) : HomeEffect
}