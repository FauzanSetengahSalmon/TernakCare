package org.fauzan0022.ternak.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.fauzan0022.ternak.database.RecycleDao
import org.fauzan0022.ternak.database.TernakDao
import org.fauzan0022.ternak.model.Ternak
import org.fauzan0022.ternak.model.TernakRecycle
import org.fauzan0022.ternak.util.SettingsDataStore

class MainViewModel(
    private val ternakDao: TernakDao,
    private val recycleDao: RecycleDao,
    dataStore: SettingsDataStore
) : ViewModel() {

    val data: StateFlow<List<Ternak>> = ternakDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recycleData: StateFlow<List<TernakRecycle>> = recycleDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteToRecycle(item: Ternak) {
        viewModelScope.launch {
            recycleDao.insert(
                TernakRecycle(
                    id = item.id,
                    kodeTernak = item.kodeTernak,
                    namaHewan = item.namaHewan,
                    jenisHewan = item.jenisHewan,
                    jenisKelamin = item.jenisKelamin,
                    umurBulan = item.umurBulan,
                    beratKg = item.beratKg,
                    statusSehat = item.statusSehat,
                    tanggalMasuk = item.tanggalMasuk,
                    catatan = item.catatan
                )
            )
            ternakDao.deleteById(item.id)
        }
    }

    fun restore(item: TernakRecycle) {
        viewModelScope.launch {
            ternakDao.insert(
                Ternak(
                    id = item.id,
                    kodeTernak = item.kodeTernak,
                    namaHewan = item.namaHewan,
                    jenisHewan = item.jenisHewan,
                    jenisKelamin = item.jenisKelamin,
                    umurBulan = item.umurBulan,
                    beratKg = item.beratKg,
                    statusSehat = item.statusSehat,
                    tanggalMasuk = item.tanggalMasuk,
                    catatan = item.catatan
                )
            )
            recycleDao.deleteById(item.id)
        }
    }

    fun deletePermanently(item: TernakRecycle) {
        viewModelScope.launch {
            recycleDao.deleteById(item.id)
        }
    }
}