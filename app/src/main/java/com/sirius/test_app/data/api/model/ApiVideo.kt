package com.sirius.test_app.data.api.model

import com.sirius.test_app.domain.model.Video
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiVideo(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "image") val image: String?
) {
    companion object {
        fun mapToDomain(apiVideo: ApiVideo?): Video {
            return Video(
                id = apiVideo?.id.orEmpty(),
                image = apiVideo?.image.orEmpty()
            )
        }
    }
}