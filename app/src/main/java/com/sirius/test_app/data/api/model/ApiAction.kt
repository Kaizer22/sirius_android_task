package com.sirius.test_app.data.api.model

import com.sirius.test_app.domain.model.Action
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiAction(
    @field:Json(name = "name")   val name: String?,
    @field:Json(name = "action") val action: String?
) {
    companion object {
        fun mapToDomain(apiAction: ApiAction?): Action{
            return Action(
                name = apiAction?.name.orEmpty(),
                action = apiAction?.action.orEmpty()
            )
        }
    }
}