package com.example.photofilter.domain.models

sealed class ScreenState{

    object HomeScreen: ScreenState()
    object FilterScreen: ScreenState()
    object ResultScreen: ScreenState()

}
