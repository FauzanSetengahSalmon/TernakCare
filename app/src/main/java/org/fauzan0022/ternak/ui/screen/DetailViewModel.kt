package org.fauzan0022.ternak.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.fauzan0022.ternak.database.TernakDao
import org.fauzan0022.ternak.model.Ternak

class DetailViewModel(private val dao: TernakDao) : ViewModel() {

    suspend fun getById(id: Long) = dao.getById(id)

    fun insert(data: Ternak) {
        viewModelScope.launch { dao.insert(data) }
    }

    fun update(data: Ternak) {
        viewModelScope.launch { dao.update(data) }
    }
}