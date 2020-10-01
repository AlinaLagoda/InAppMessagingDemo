package com.example.inappmessagingdemo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.inappmessagingdemo.R
import com.example.inappmessagingdemo.controllers.Analytics
import com.example.inappmessagingdemo.controllers.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_events.*

class EventsActivity : AppCompatActivity() {

    private val analytics: Analytics by lazy { FirebaseAnalytics(this) }

    private val eventButtonClickListener = View.OnClickListener {
        val event = when (val id = it.id) {
            R.id.button_event1 -> "event_1"
            R.id.button_event2 -> "event_2"
            R.id.button_event3 -> "event_3"
            else -> error("Unsupported button: $id")
        }
        analytics.log(event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        button_event1.setOnClickListener(eventButtonClickListener)
        button_event2.setOnClickListener(eventButtonClickListener)
        button_event3.setOnClickListener(eventButtonClickListener)

        button_crash.setOnClickListener {
            throw Exception("Crash event")
        }
    }

    companion object {

        fun prepareIntent(context: Context) = Intent(context, EventsActivity::class.java)

    }
}