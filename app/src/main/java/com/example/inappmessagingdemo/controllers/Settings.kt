package com.example.inappmessagingdemo.controllers

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.inappmessagingdemo.model.UserGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface Settings {

    suspend fun setUserGroup(value: UserGroup)

    fun getUserGroup(): Flow<UserGroup>

}

class SettingsImpl(context: Context) : Settings {

    private val dataStore: DataStore<Preferences> by lazy { context.createDataStore(name = "settings") }

    override suspend fun setUserGroup(value: UserGroup) {
        dataStore.edit { it[USER_GROUP] = value.name }
    }

    override fun getUserGroup(): Flow<UserGroup> =
        dataStore.data.map { prefs -> prefs[USER_GROUP]?.let { enumValueOf<UserGroup>(it) } ?: UserGroup.GROUP_A }

    companion object {
        private val USER_GROUP = preferencesKey<String>("user_group")
    }
}