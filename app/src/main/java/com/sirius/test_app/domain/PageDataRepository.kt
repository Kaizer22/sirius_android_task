package com.sirius.test_app.domain

import com.sirius.test_app.domain.model.GameDetails
import com.sirius.test_app.domain.model.Review
import io.reactivex.Flowable

// Изолируем реализации репозитория и уменьшим связанность с помощью интерфейса
interface PageDataRepository {
    fun getDescription(): Flowable<GameDetails>
    fun getReviews(): Flowable<List<Review>>
}