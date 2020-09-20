package com.avast.inappmessagingdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val dataStore: DataStore<Preferences> by lazy { createDataStore(name = "prefs") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val analytics = FirebaseAnalytics.getInstance(this)

        groups.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.group_a -> "group_a"
                R.id.group_b -> "group_b"
                R.id.group_c -> "group_c"
                else -> error("Unknown id")
            }.let { group ->
                lifecycleScope.launch {
                    dataStore.edit { preferences ->
                        preferences[USER_GROUP] = group
                    }
                }
            }
        }

        event1.setOnClickListener {
            analytics.logEvent("event_1", null)
        }

        event2.setOnClickListener {
            analytics.logEvent("event_2", null)
        }

        event3.setOnClickListener {
            analytics.logEvent("event_3", null)
        }

        lifecycleScope.launch {
            dataStore.data.map { preferences ->
                preferences[USER_GROUP] ?: "group_a"
            }.collect {
                when (it) {
                    "group_a" -> R.id.group_a
                    "group_b" -> R.id.group_b
                    "group_c" -> R.id.group_c
                    else -> error("Unknown group")
                }.let {
                    groups.check(it)
                }

                analytics.setUserProperty("group", it)
            }
        }
    }

    companion object {
        private val USER_GROUP = preferencesKey<String>("user_group")
    }
}