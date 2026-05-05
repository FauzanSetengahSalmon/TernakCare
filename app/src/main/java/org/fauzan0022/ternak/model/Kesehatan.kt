package org.fauzan0022.ternak.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kesehatan")
data class Kesehatan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ternakId: Long,
    val kondisi: String,
    val catatan: String
)