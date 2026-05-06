package org.fauzan0022.ternak.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.fauzan0022.ternak.database.RecycleDao
import org.fauzan0022.ternak.database.TernakDao
import org.fauzan0022.ternak.model.Ternak
import org.fauzan0022.ternak.model.TernakRecycle

class DetailViewModel(
    private val dao: TernakDao,
    private val recycleDao: RecycleDao
) : ViewModel() {
    suspend fun getById(id: Long): Ternak? = dao.getById(id)

    fun insert(ternak: Ternak) {
        viewModelScope.launch {
            dao.insert(ternak)
        }
    }
    fun update(ternak: Ternak) {
        viewModelScope.launch {
            dao.update(ternak)
        }
    }
    fun delete(id: Long) {
        viewModelScope.launch {
            val dataTernak = dao.getById(id)
            if (dataTernak != null) {
                recycleDao.insert(
                    TernakRecycle(
                        id = dataTernak.id,
                        kodeTernak = dataTernak.kodeTernak,
                        namaHewan = dataTernak.namaHewan,
                        jenisHewan = dataTernak.jenisHewan,
                        jenisKelamin = dataTernak.jenisKelamin,
                        umurBulan = dataTernak.umurBulan,
                        beratKg = dataTernak.beratKg,
                        statusSehat = dataTernak.statusSehat,
                        tanggalMasuk = dataTernak.tanggalMasuk,
                        catatan = dataTernak.catatan
                    )
                )
                dao.deleteById(id)
            }
        }
    }
}