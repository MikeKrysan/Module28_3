package com.mikekrysan.module28_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.activity_main.*

//TRANSITIONSET - позволяет определить несколько переходов в один и воспроизводить их одновременно или последовательно

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TransitonSet:
//        val setIn = TransitionSet()
//        //Fade:
//        setIn.addTransition(Fade())
//        //Slide:
//        setIn.addTransition(Slide().apply {
//            slideEdge = Gravity.BOTTOM;
//            duration = 1500
//        })
//        //Выполнятся они будут одновременно
//        setIn.ordering = TransitionSet.ORDERING_TOGETHER
//
//        val scene1 = Scene.getSceneForLayout(root, R.layout.scene1, this)
//        root.setOnClickListener {
//            TransitionManager.go(scene1, setIn)
//        }

        //Propagation-распространение
        swipeRefreshLayout.setOnRefreshListener {
            PropagationTransition(root as ViewGroup,
                startingView = imageIcon,
                transition = TransitionSet()
                    .addTransition(Fade(Fade.IN)
                        .setInterpolator(AccelerateInterpolator()))
                    .addTransition(Slide(Gravity.BOTTOM)
                        .setInterpolator(DecelerateInterpolator())
                    ))
                .start()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}