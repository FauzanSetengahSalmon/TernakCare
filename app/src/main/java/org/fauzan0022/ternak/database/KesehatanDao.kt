package org.fauzan0022.ternak.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.fauzan0022.ternak.model.Kesehatan

@Dao
interface KesehatanDao {
    @Query("SELECT * FROM kesehatan WHERE ternakId=:id ORDER BY id DESC")
    fun getByTernak(id: Long): Flow<List<Kesehatan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: Kesehatan)

    @Update
    suspend fun update(data: Kesehatan)

    @Query("DELETE FROM kesehatan WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM kesehatan WHERE ternakId = :ternakId")
    suspend fun deleteByTernakId(ternakId: Long)
}