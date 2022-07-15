package com.sirius.test_app.ui.game_detail

import android.text.BoringLayout
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirius.test_app.data.impl.StubPageDataRepository
import com.sirius.test_app.domain.PageDataRepository
import com.sirius.test_app.domain.model.Action
import com.sirius.test_app.domain.model.GameDetails
import com.sirius.test_app.domain.model.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Реализация шаблона MVVM
// Засчет использования ViewModel отслеживаем состояния и реагируем на их изменения
// и перестаем напрямую зависеть от жизненного цикла UI компонентов, связывая данные
// c ViewModel;
// Воспользуемся Hilt для внедрения зависимостей и, таким образом, сокращения объема кода
// и повышения его читаемости
@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val dataRepository: PageDataRepository,
    // Как единая точка доступа для управления (завершения) подписок
    // используется CompositeDisposable - предоставленный Hilt статический экземпляр
    // (см. ActivityRetainedComponent)
    private val compositeDisposable: CompositeDisposable
): ViewModel() {
    companion object{
        private val TAG = "GameDetailsVM"
    }

    //FIXME публичный mutable state

    // LiveData используется для подписки на изменения полей state
    val state = MutableLiveData<GameDetailsState>()

    // Класс для описания текущего состояния экрана
    data class GameDetailsState(
        // TODO обработка процесса загрузки
        val loadingDetails: Boolean = true,
        val loadingReviews: Boolean = true,
        val gameDetails: GameDetails = GameDetails(
            image = "NONE", logo="NONE", name="", description="",
            gradeCnt = "-", rating = 0.0F, action = Action("", "none"),
            tags = emptyList(), videos = emptyList(), reviews = emptyList()),
        val reviews: List<Review> = emptyList()
    )

    // Инициализируем начальное пустое состояние и подписываемся на изменения
    // интересующих данных
    init {
        state.value = GameDetailsState()
        subscribeToPageDataUpdate()
        subscribeToReviewsUpdate()
    }

    //Получаем от репозитория Flowable и объявляем Consumer для обработки изменений
    //в главном потоке
    private fun subscribeToReviewsUpdate() {
        //Log.d(TAG, "test1")
        dataRepository.getReviews()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {onReviewListUpdate(it)},{onError(it)}
            ).addTo(compositeDisposable)
    }

    private fun subscribeToPageDataUpdate() {
        dataRepository.getDescription()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {onGameDetailsUpdate(it)},{onError(it)}
            ).addTo(compositeDisposable)
    }

    // Создаем копию state.value с указанными изменениями, чтобы быть уверенными в том,
    // что они будут обработаны
    private fun onGameDetailsUpdate(gameDetails: GameDetails) {
        Log.d(TAG, "onDetailsUpd")
        state.value = state.value!!.copy(gameDetails = gameDetails)
    }

    private fun onReviewListUpdate(list: List<Review>) {
        Log.d(TAG, "onReviewsUpd")
        state.value = state.value!!.copy(reviews = list)
    }

    private fun onError(e: Throwable){
        Log.e(TAG, e.toString())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}