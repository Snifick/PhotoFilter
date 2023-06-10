package com.example.photofilter.domain.usecase

sealed class ScreenState{

    object homeScreen:ScreenState()
    object filterScreen:ScreenState()
    object resultScreen:ScreenState()

}
