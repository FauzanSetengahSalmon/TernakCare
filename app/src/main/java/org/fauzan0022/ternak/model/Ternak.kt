package org.fauzan0022.ternak.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ternak")
data class Ternak(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nama: String,
    val jenis: String,
    val umur: Int
)