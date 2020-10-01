package com.example.inappmessagingdemo.controllers

import android.content.Context
import com.example.inappmessagingdemo.model.UserGroup
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.Locale

interface Analytics {

    fun setUserGroup(value: UserGroup)

    fun log(event: String)
}

class FirebaseAnalytics(context: Context) : Analytics {

    private val analytics by lazy { FirebaseAnalytics.getInstance(context) }

    override fun setUserGroup(value: UserGroup) {
        analytics.setUserProperty("group", value.name.toLowerCase(Locale.getDefault()))
    }

    override fun log(event: String) {
        analytics.logEvent(event, null)
    }
}