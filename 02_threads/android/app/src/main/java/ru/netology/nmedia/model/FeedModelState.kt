package ru.netology.nmedia.model

data class FeedModelState(
    val Idle: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false,
    val refreshing: Boolean = false,
)