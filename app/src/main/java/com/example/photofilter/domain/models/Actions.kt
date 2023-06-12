package com.example.photofilter.domain.models

sealed class Action{

    object filters : Action()
    object draw : Action()
    object loading:Action()
}
