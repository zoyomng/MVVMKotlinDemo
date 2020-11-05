package com.zoyo.mvvmkotlindemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zoyo.mvvmkotlindemo.constant.Constant
import com.zoyo.mvvmkotlindemo.db.entity.Cheese
import com.zoyo.mvvmkotlindemo.db.entity.Subject
import com.zoyo.mvvmkotlindemo.db.dao.CheeseDao
import com.zoyo.mvvmkotlindemo.db.dao.SubjectDao

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
@Database(entities = arrayOf(Subject::class, Cheese::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun cheeseDao(): CheeseDao


    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "zoyomngDb"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        fillInDb(context.applicationContext)
                    }

                }).build()
            }
            return instance!!
        }

        private fun fillInDb(context: Context) {
            ioThread {
                get(context).cheeseDao()
                    .insert(Constant.CHEESE_DATA.map { Cheese(id = 0, name = it) })

                get(context).subjectDao().insert(Constant.SUBJECT_DATA)
            }
        }
    }
}
