package com.org.home.ui

sealed interface HomeEffect {

    data object OnOpenBottomSheet : HomeEffect
    data object ShowConnectionError : HomeEffect
}
