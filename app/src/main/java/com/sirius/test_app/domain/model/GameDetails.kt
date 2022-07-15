package com.sirius.test_app.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// В пакете domain.model представлены классы моделей бизнес-логики
data class GameDetails(
    val image: String,
    val logo: String,
    val name: String,
    val rating: Float,
    val gradeCnt: String,
    val tags: List<String>,
    val videos: List<Video>,
    val description: String,
    val reviews: List<Review>,
    val action: Action
)