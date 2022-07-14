package com.sirius.test_app.data.api.model

import com.sirius.test_app.domain.model.GameDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Представим, что получаем объекты в пакете data.api
// посредством общения с внешним API,
// поэтому мы не всегда можем знать наверняка, что в ответе придут все ожидаемые поля
// (и воспользуемся Moshi для гипотетического:) парсинга из/в JSON),
@JsonClass(generateAdapter = true)
data class ApiGameDetails(
    @field:Json(name = "image") val image: String?,
    @field:Json(name = "logo")val logo: String?,
    @field:Json(name = "name")val name: String?,
    @field:Json(name = "rating")val rating: Float?,
    @field:Json(name = "gradeCnt")val gradeCnt: String?,
    @field:Json(name = "tags")val tags: List<String>?,
    @field:Json(name = "videos")val videos: List<ApiVideo>?,
    @field:Json(name = "description")val description: String?,
    @field:Json(name = "reviews")val reviews: List<ApiReview>?,
    @field:Json(name = "action")val action: ApiAction?
) {

    // Преобразование к классу бизнес-логики и обработка nullable полей
    companion object{
        fun mapToDomain(apiGameDetails: ApiGameDetails?): GameDetails{
                return GameDetails(
                    image = apiGameDetails?.image.orEmpty(),
                    logo = apiGameDetails?.logo.orEmpty(),
                    name = apiGameDetails?.name.orEmpty(),
                    rating = apiGameDetails?.rating ?: 0f,
                    gradeCnt = apiGameDetails?.gradeCnt.orEmpty(),
                    tags = apiGameDetails?.tags ?: listOf(),
                    videos = apiGameDetails?.videos?.map { v -> ApiVideo.mapToDomain(v) } ?: listOf(),
                    description = apiGameDetails?.description.orEmpty(),
                    reviews = apiGameDetails?.reviews?.map { r -> ApiReview.mapToDomain(r)} ?: listOf(),
                    action = ApiAction.mapToDomain(apiGameDetails?.action)
                )
        }
    }
}