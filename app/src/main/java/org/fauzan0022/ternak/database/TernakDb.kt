package org.fauzan0022.ternak.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.fauzan0022.ternak.model.Kesehatan
import org.fauzan0022.ternak.model.Ternak
import org.fauzan0022.ternak.model.TernakRecycle

@Database(
    entities = [Ternak::class, TernakRecycle::class, Kesehatan::class],
    version = 2,
    exportSchema = false
)
abstract class TernakDb : RoomDatabase() {

    abstract fun ternakDao(): TernakDao
    abstract fun recycleDao(): RecycleDao
    abstract fun kesehatanDao(): KesehatanDao

    companion object {
        @Volatile
        private var INSTANCE: TernakDb? = null

        fun getInstance(context: Context): TernakDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TernakDb::class.java,
                    "ternak_db"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}