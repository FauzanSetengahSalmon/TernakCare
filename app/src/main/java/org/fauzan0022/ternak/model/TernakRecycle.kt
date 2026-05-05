package org.fauzan0022.ternak.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recycle")
data class TernakRecycle(
    @PrimaryKey val id: Long,
    val nama: String,
    val jenis: String,
    val umur: Int
)