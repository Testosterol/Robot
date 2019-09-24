package com.testosterol.apps.roboto.robot

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.testosterol.apps.roboto.R
import com.testosterol.apps.roboto.util.ArcView

/*
 * Denis created this class on the 24/09/2019
 */

class TerminatorStory : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terminator_story_layout)

        val buttonNext = findViewById<FloatingActionButton>(R.id.start_mission)

        val arcLayout = findViewById<ArcView>(R.id.arcLayout)
        if (arcLayout != null) {

            ValueAnimator.ofFloat(200f, 0f, 200f).apply {
                addUpdateListener { animation -> arcLayout.arcHeight = (animation.animatedValue as Float) }
                duration = 10000
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }.start()
        }
        val clickListener = View.OnClickListener { view ->
            when (view.getId()) {
                R.id.start_mission -> {
                    val intent = Intent(this@TerminatorStory, TerminatorActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        buttonNext.setOnClickListener(clickListener)
    }
}