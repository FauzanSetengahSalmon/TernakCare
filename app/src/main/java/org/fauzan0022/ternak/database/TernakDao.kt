package org.fauzan0022.ternak.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.fauzan0022.ternak.model.Ternak

@Dao
interface TernakDao {
    @Insert
    suspend fun insert(ternak: Ternak)

    @Update
    suspend fun update(ternak: Ternak)

    @Query("SELECT * FROM ternak ORDER BY id DESC")
    fun getAll(): Flow<List<Ternak>>

    @Query("SELECT * FROM ternak WHERE id = :id")
    suspend fun getById(id: Long): Ternak?

    @Query("DELETE FROM ternak WHERE id = :id")
    suspend fun deleteById(id: Ternak)
}
