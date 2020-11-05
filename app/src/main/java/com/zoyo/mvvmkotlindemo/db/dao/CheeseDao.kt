package com.zoyo.mvvmkotlindemo.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zoyo.mvvmkotlindemo.db.entity.Cheese

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */

@Dao
interface CheeseDao {
    @Insert
    fun insert(cheese: Cheese)

    @Insert
    fun insert(cheeses: List<Cheese>)

    @Delete
    fun remove(cheese: Cheese)

    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
    fun allCheesesByName(): PagingSource<Int,Cheese>

}