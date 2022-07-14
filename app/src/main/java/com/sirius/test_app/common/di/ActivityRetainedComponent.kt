package com.sirius.test_app.common.di

import com.sirius.test_app.data.impl.StubPageDataRepository
import com.sirius.test_app.domain.PageDataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    // Предоставляется экземпляр заданного класса репозитория, реализующего интерфейс
    @Binds
    @ActivityRetainedScoped
    abstract fun bindPageDataRepository(repository: StubPageDataRepository): PageDataRepository

    // Экземпляр CompositeDisposable для внедрения во viewModels и удобного управления подписками
    companion object {
        @Provides
        fun provideCompositeDisposable() = CompositeDisposable()
    }
}