package com.anwesh.uiprojects.kotlinlinkedtiltedlineview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.linkedtitledlineview.LinkedTiltedView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LinkedTiltedView.create(this)
    }
}
