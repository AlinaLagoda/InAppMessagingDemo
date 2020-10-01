package com.example.inappmessagingdemo

import android.app.Application
import android.widget.Toast
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseInAppMessaging.getInstance().addClickListener(InAppMessagingClickListener())
    }

    private inner class InAppMessagingClickListener : FirebaseInAppMessagingClickListener {
        override fun messageClicked(inAppMessage: InAppMessage, action: Action) {
            when (action.actionUrl) {
                "sample-app://actions/action-result" -> {
                    Toast.makeText(this@App, "Action clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}