package org.fauzan0022.ternak.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.fauzan0022.ternak.model.TernakRecycle

@Dao
interface RecycleDao {
    @Insert
    suspend fun insert(recycle: TernakRecycle)

    @Query("SELECT * FROM recycle ORDER BY id DESC")
    fun getAll(): Flow<List<TernakRecycle>>

    @Query("DELETE FROM recycle WHERE id = :id")
    suspend fun deleteById(id: Long)
}
