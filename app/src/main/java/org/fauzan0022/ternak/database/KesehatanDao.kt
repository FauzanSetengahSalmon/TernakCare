package org.fauzan0022.ternak.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.fauzan0022.ternak.model.Kesehatan

@Dao
interface KesehatanDao {
    @Insert
    suspend fun insert(kesehatan: Kesehatan)

    @Query("SELECT * FROM kesehatan WHERE ternakId = :id ORDER BY id DESC")
    fun getByTernak(id: Long): Flow<List<Kesehatan>>

    @Query("DELETE FROM kesehatan WHERE id = :id")
    suspend fun deleteById(id: Long)
}
