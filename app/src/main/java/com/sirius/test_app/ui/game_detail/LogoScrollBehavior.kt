package com.sirius.test_app.ui.game_detail
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginStart
import com.sirius.test_app.common.px

class LogoScrollBehavior(
    val movingText: TextView,
    val dynamicShadow: View
) : CoordinatorLayout.Behavior<ConstraintLayout>() {

    private var currentAlpha = 1F
    // FIXME более универсальная отностительная формула
    private val movingTextStart = 102
    private val animationTriggerHeight = 500F

    // Скрытие логотипа при скроллинге и перемещение названия на его место +
    // проявление динамического затенения строки состояния
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: ConstraintLayout,
        dependency: View
    ): Boolean {
        Log.d("BEHAVIOR_onDependent", child.y.toString())

        currentAlpha = child.y / animationTriggerHeight
        if (child.y < animationTriggerHeight) {
            val p = (movingText.layoutParams as ViewGroup.MarginLayoutParams)
            p.marginStart = (movingTextStart.px * currentAlpha).toInt()
            movingText.layoutParams = p
        }

        child.alpha = currentAlpha
        dynamicShadow.alpha = 1 - currentAlpha
        return true
    }
}