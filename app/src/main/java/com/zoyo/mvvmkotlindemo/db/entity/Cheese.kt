package com.zoyo.mvvmkotlindemo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
@Entity
data class Cheese(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)