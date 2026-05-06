package org.fauzan0022.ternak.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kesehatan")
data class Kesehatan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ternakId: Long,
    val tanggalPeriksa: String,
    val kondisi: String,
    val suhuTubuh: Double,
    val diagnosa: String,
    val tindakan: String,
    val dokter: String
)