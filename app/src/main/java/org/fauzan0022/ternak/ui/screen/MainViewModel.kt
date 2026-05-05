package org.fauzan0022.ternak.ui.screen

import org.fauzan0022.ternak.database.RecycleDao
import org.fauzan0022.ternak.database.TernakDao
import org.fauzan0022.ternak.model.Ternak
import org.fauzan0022.ternak.model.TernakRecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val ternakDao: TernakDao,
    private val recycleDao: RecycleDao
) : ViewModel() {

    val data: StateFlow<List<Ternak>> = ternakDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val recycleData: StateFlow<List<TernakRecycle>> = recycleDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun delete(item: Ternak) {
        viewModelScope.launch {
            recycleDao.insert(
                TernakRecycle(
                    id = item.id,
                    nama = item.nama,
                    jenis = item.jenis,
                    umur = item.umur
                )
            )
            ternakDao.deleteById(item)
        }
    }

    fun restore(item: TernakRecycle) {
        viewModelScope.launch {
            ternakDao.insert(
                Ternak(
                    id = item.id,
                    nama = item.nama,
                    jenis = item.jenis,
                    umur = item.umur
                )
            )
            recycleDao.deleteById(item)
        }
    }
}