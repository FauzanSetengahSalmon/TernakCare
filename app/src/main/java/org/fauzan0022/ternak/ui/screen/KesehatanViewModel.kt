package org.fauzan0022.ternak.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.fauzan0022.ternak.database.KesehatanDao
import org.fauzan0022.ternak.model.Kesehatan

class KesehatanViewModel(private val dao: KesehatanDao) : ViewModel() {

    fun getByTernak(id: Long) = dao.getByTernak(id)

    fun insert(data: Kesehatan) {
        viewModelScope.launch { dao.insert(data) }
    }

    fun delete(data: Kesehatan) {
        viewModelScope.launch { dao.deleteById(data) }
    }
}