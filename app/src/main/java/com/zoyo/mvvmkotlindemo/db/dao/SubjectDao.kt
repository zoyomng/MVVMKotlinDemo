package com.zoyo.mvvmkotlindemo.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zoyo.mvvmkotlindemo.db.Entity.Subject

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subject")
    fun getAll(): List<Subject>

    @Insert
    fun insert(subject: Subject)

    @Insert
    fun insert(subjects: List<Subject>)


    @Delete
    fun delete(subject: Subject)

    @Query("SELECT * FROM subject WHERE subject_title LIKE :title")
    fun findByTitle(title: String): Subject


}