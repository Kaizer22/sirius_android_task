package com.sirius.test_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Связывание Hilt с жизненным циклом приложения
@HiltAndroidApp
class GameDetailsApplication: Application() {}