package com.sirius.test_app.data.api.model

import com.sirius.test_app.domain.model.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiReview(
    @field:Json(name = "userImage") val userImage: String?,
    @field:Json(name = "userName") val userName: String?,
    @field:Json(name = "date") val date: String?,
    @field:Json(name = "message") val message: String?
) {
    companion object {
        fun mapToDomain(apiReview: ApiReview?): Review {
            return Review(
                userImage = apiReview?.userImage.orEmpty(),
                userName = apiReview?.userName.orEmpty(),
                date = apiReview?.date.orEmpty(),
                message = apiReview?.message.orEmpty()
            )
        }
    }
}