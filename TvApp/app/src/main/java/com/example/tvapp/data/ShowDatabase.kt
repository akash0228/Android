package com.example.tvapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tvapp.Show


@Database(entities = arrayOf(Show::class), version = 1)
abstract class ShowDatabase: RoomDatabase() {
    abstract fun showDao():ShowDao

    companion object{
        @Volatile
        private var INSTANCE: ShowDatabase?=null

        fun getInstance(context: Context): ShowDatabase {
            return INSTANCE ?: synchronized(this){

                val MIGRATION_1_2: Migration = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Drop the existing table if it exists
                        database.execSQL("DROP TABLE IF EXISTS `show_row_table`")

                        // Create the new table with the corrected structure
                        database.execSQL("CREATE TABLE IF NOT EXISTS `show_row_table` " +
                                "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                "`header` TEXT NOT NULL DEFAULT 'undefined', " +
                                "`listshow` TEXT NOT NULL DEFAULT 'undefined')")
                    }
                }

                val MIGRATION_2_3: Migration = object : Migration(2, 3) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Drop the existing table if it exists
                        database.execSQL("DROP TABLE IF EXISTS `show_row_table`")

                        // Create the new table with the corrected structure
                        database.execSQL("CREATE TABLE IF NOT EXISTS `show_table` " +
                                "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                "`title` TEXT NOT NULL DEFAULT 'undefined', " +
                                "`description` TEXT NOT NULL DEFAULT 'undefined', " +
                                "`category` TEXT NOT NULL DEFAULT 'undefined', " +
                                "`year` INTEGER NOT NULL DEFAULT 0, " +
                                "`duration` TEXT NOT NULL DEFAULT 'undefined', " +
                                "`videoUrl` TEXT NOT NULL DEFAULT '', " +
                                "`imageUrl` TEXT NOT NULL DEFAULT '', " +
                                "`header` TEXT NOT NULL DEFAULT 'undefined', " +
                                "`inWatchList` INTEGER NOT NULL DEFAULT 0, " +
                                "`isWatched` INTEGER NOT NULL DEFAULT 0)")

                    }
                }


                val instance= Room.databaseBuilder(context.applicationContext,
                    ShowDatabase::class.java,"myshow_database").build()
                INSTANCE =instance
                instance
            }
        }
    }
}