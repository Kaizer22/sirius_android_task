package com.sirius.test_app.test

import com.sirius.test_app.data.api.model.ApiAction
import com.sirius.test_app.data.api.model.ApiGameDetails
import com.sirius.test_app.data.api.model.ApiReview
import com.sirius.test_app.data.api.model.ApiVideo
// Источник тестовых данных
class SimpleTestDataProvider {
    companion object {
        var apiReviews: MutableList<ApiReview> = mutableListOf(
            ApiReview(
                userImage = "https://i.ibb.co/55HSwqy/img-user-1.png",
                userName = "Auguste Conte",
                date = "February 14, 2019",
                message = "“Once you start to learn its secrets, there’s a wild and exciting variety of play here that’s unmatched, even by its peers.”"
            ),
            ApiReview(
                userImage = "https://i.ibb.co/j8t3zGZ/img-user-2.png",
                userName = "Jang Marcelino",
                date = "February 14, 2019",
                message =
                "“Once you start to learn its secrets, there’s a wild and exciting variety of play here that’s unmatched, even by its peers.”"
            )
        )
        val apiGameDetails: ApiGameDetails = ApiGameDetails(
            image = "https://i.ibb.co/g3YVWD2/img-background.png",//"https://ibb.co/37KzF03",
            logo = "https://www.pngkit.com/png/full/500-5001548_league-legends-logo-of-game-dota-video-clipart.png",//"https://ibb.co/GpbZg5t",
            name = "DoTA 2",
            rating = 4.9f,
            gradeCnt = "70M",
            tags = listOf("MOBA", "MULTIPLAYER", "STRATEGY"),
            videos = listOf(
                ApiVideo(id = "video_1", image = "https://ibb.co/q7dyBVH"),
                ApiVideo(id = "video_2", image = "https://ibb.co/S3GymhJ")
            ),
            description = "Dota 2 is a multiplayer online battle arena (MOBA) game which has two teams of five players compete to collectively destroy a large structure defended by the opposing team known as the \"Ancient\", whilst defending their own.",
            reviews = listOf(),
            action = ApiAction(
                name = "Install",
                action = "action_install"
            )
        )
    }
}