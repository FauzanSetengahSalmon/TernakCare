package org.fauzan0022.ternak.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.fauzan0022.ternak.database.TernakDb
import org.fauzan0022.ternak.ui.screen.DetailViewModel
import org.fauzan0022.ternak.ui.screen.KesehatanViewModel
import org.fauzan0022.ternak.ui.screen.MainViewModel

class ViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    private val db = TernakDb.getInstance(context)
    private val dataStore = SettingsDataStore(context)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(db.ternakDao(), db.recycleDao(), dataStore) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(db.ternakDao(), db.recycleDao()) as T

            modelClass.isAssignableFrom(KesehatanViewModel::class.java) ->
                KesehatanViewModel(db.kesehatanDao()) as T

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}