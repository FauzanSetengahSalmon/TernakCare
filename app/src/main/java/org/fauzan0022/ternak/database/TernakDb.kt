package org.fauzan0022.ternak.database

import org.fauzan0022.ternak.model.Ternak
import org.fauzan0022.ternak.model.TernakRecycle
import org.fauzan0022.ternak.model.Kesehatan
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Ternak::class, TernakRecycle::class, Kesehatan::class],
    version = 1,
    exportSchema = false
)
abstract class TernakDb : RoomDatabase() {

    abstract fun ternakDao(): TernakDao
    abstract fun recycleDao(): RecycleDao
    abstract fun kesehatanDao(): KesehatanDao

    companion object {
        @Volatile private var INSTANCE: TernakDb? = null

        fun getInstance(context: Context): TernakDb {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TernakDb::class.java,
                    "ternak_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
