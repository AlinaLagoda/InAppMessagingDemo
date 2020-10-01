package com.example.inappmessagingdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.inappmessagingdemo.R
import com.example.inappmessagingdemo.controllers.Analytics
import com.example.inappmessagingdemo.controllers.FirebaseAnalytics
import com.example.inappmessagingdemo.controllers.Settings
import com.example.inappmessagingdemo.controllers.SettingsImpl
import com.example.inappmessagingdemo.model.UserGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val analytics: Analytics by lazy { FirebaseAnalytics(this) }

    private val settings: Settings by lazy { SettingsImpl(this) }

    private lateinit var currentUserGroup: UserGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_switchGroup.setOnClickListener {
            showGroupSwitchDialog()
        }

        button_generateEvents.setOnClickListener {
            startActivity(EventsActivity.prepareIntent(this))
        }

        lifecycleScope.launch {
            settings.getUserGroup().collect {
                currentUserGroup = it
                textView_currentGroup.text = getString(R.string.current_user_group_label_template, getString(it.labelRes))
                analytics.setUserGroup(it)
            }
        }
    }

    private fun showGroupSwitchDialog() {
        val groups = UserGroup.values()
        val items = groups.map { getString(it.labelRes) }.toTypedArray()
        val current = groups.indexOf(currentUserGroup)
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.switch_group_dialog_title)
            .setSingleChoiceItems(items, current) { dialog, index ->
                dialog.dismiss()
                lifecycleScope.launch {
                    settings.setUserGroup(groups[index])
                }
            }
            .show()
    }
}