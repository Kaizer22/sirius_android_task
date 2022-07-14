package com.sirius.test_app.common

import android.content.res.Resources

// Конвертер dp в пиксели
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()