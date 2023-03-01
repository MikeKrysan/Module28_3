package com.mikekrysan.module28_3

import android.graphics.Rect
import android.transition.CircularPropagation
import android.transition.Transition
import android.transition.TransitionManager.*
import android.transition.TransitionPropagation
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator

class PropagationTransition(
    val sceneRoot: ViewGroup,
    var startingView: View? = null,
    val transition: Transition,
    duration: Long = 600,
    interpolator: DecelerateInterpolator = DecelerateInterpolator(),
    propagation: TransitionPropagation = CircularPropagation()) {
    val targets: Collection<View>

    init {
        targets = (0 until sceneRoot.childCount).map { sceneRoot.getChildAt(it) }
        transition.interpolator = interpolator
        transition.duration = duration
        transition.propagation = propagation
    }

    fun start() {
        if (startingView == null && sceneRoot.childCount > 0) {
            startingView = sceneRoot.getChildAt(0)
        }

        // Устанавливаем реализацию получения эпицентра перехода
        transition.epicenterCallback = (startingView ?: sceneRoot).asEpicenter()

        // Перед началом перехода прячем все view
        targets.forEach { it.visibility = View.INVISIBLE }

        // Завершаем настройки перехода
        beginDelayedTransition(sceneRoot, transition)

        // Для всех целей анимации меняем видимость
        targets.forEach { it.visibility = View.VISIBLE }
    }

    //Функция для получения реализации эпицентра
    private fun View.asEpicenter() : Transition.EpicenterCallback {
        val viewRect = Rect()
        getGlobalVisibleRect(viewRect)
        return object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition?): Rect = viewRect
        }
    }
}