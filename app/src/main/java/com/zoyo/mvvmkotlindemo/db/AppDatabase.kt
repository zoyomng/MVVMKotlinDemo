package com.zoyo.mvvmkotlindemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zoyo.mvvmkotlindemo.db.Entity.Subject
import com.zoyo.mvvmkotlindemo.db.dao.SubjectDao

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
@Database(entities = arrayOf(Subject::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
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
                get(context).subjectDao().insert(Subject_Data)
            }
        }
    }
}

private val Subject_Data = arrayListOf(
    Subject(id = 1,subjectTitle = "Page分页","架构组件-list的Item动态更新",1,"")

)
