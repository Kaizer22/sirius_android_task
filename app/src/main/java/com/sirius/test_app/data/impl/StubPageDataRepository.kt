package com.sirius.test_app.data.impl

import android.util.Log
import com.sirius.test_app.data.api.model.ApiGameDetails
import com.sirius.test_app.data.api.model.ApiReview
import com.sirius.test_app.domain.PageDataRepository
import com.sirius.test_app.domain.model.GameDetails
import com.sirius.test_app.domain.model.Review
import com.sirius.test_app.test.SimpleTestDataProvider
import io.reactivex.Emitter
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// Тестовая реализация репозитория для доступа к подготовленным заранее данным
class StubPageDataRepository @Inject constructor(): PageDataRepository {

    // Некоторый источник (БД, ORM или API посредством Retrofit)
    // предоставляет возможность подписаться на изменения данных
    companion object {
        private val random = Random()

        // Симуляция изменения данных во внешнем источнике

        // Каждые 5с меняется рейтинг
        private val apiGameDetailsFlowable: Flowable<ApiGameDetails> =
            Flowable.interval(1,5, TimeUnit.SECONDS).flatMap {
                val rate: Float = Math.round((random.nextFloat() * 5 * 10.0)).toFloat() / 10
                Log.d("gen", rate.toString())
                Single.just(SimpleTestDataProvider.apiGameDetails.copy(rating = rate)).toFlowable()
            }
        // Каждую минуту добавляется комментарий
        private val reviewsFlowable: Flowable<List<ApiReview>> = //Flowable.fromIterable(r)
            Flowable.interval(2,5, TimeUnit.SECONDS).flatMap {
                val rate: Float = Math.round((random.nextFloat() * 5 * 10.0)).toFloat() / 10
                Log.d("reviews", rate.toString())
                val r = ApiReview(userImage = "TEST", userName = "TEST TEST${rate}", date = "July 14, 2022", message = "Hello world")
                val l = concatenate(SimpleTestDataProvider.apiReviews, mutableListOf(r))
                SimpleTestDataProvider.apiReviews = l as MutableList<ApiReview>
                Single.just(l).toFlowable()
            }
        fun <T> concatenate(vararg lists: List<T>): List<T> {
            return listOf(*lists).flatten()
        }
    }

    override fun getDescription(): Flowable<GameDetails> {
        // Распаковка Flowable и выполнение преобразования объектов классов
        // API к классам бизнес-логики
        return apiGameDetailsFlowable.distinctUntilChanged()
            .map { d -> ApiGameDetails.mapToDomain(d) }
    }

    override fun getReviews(): Flowable<List<Review>> {
        return reviewsFlowable.distinctUntilChanged()
            .map { l ->
                l.map {
                    r -> ApiReview.mapToDomain(r)
            }
            }
    }
}