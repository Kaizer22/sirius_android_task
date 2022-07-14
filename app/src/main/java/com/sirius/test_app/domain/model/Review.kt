package com.sirius.test_app.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Review(
    val userImage: String,
    val userName: String,
    val date: String,
    val message: String
)