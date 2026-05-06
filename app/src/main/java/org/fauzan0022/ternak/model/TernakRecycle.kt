package org.fauzan0022.ternak.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recycle")
data class TernakRecycle(
    @PrimaryKey val id: Long,
    val kodeTernak: String,
    val namaHewan: String,
    val jenisHewan: String,
    val jenisKelamin: String,
    val umurBulan: Int,
    val beratKg: Double,
    val statusSehat: Boolean,
    val tanggalMasuk: String,
    val catatan: String
)