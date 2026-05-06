package org.fauzan0022.ternak.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.fauzan0022.ternak.database.KesehatanDao
import org.fauzan0022.ternak.model.Kesehatan

class KesehatanViewModel(private val dao: KesehatanDao) : ViewModel() {

    fun getByTernak(id: Long): Flow<List<Kesehatan>> = dao.getByTernak(id)

    fun insert(data: Kesehatan) {
        if (data.diagnosa.isBlank() || data.tindakan.isBlank()) {
            return
        }

        viewModelScope.launch {
            dao.insert(data)
        }
    }
    fun delete(id: Int) {
        viewModelScope.launch {
            dao.delete(id)
        }
    }
}