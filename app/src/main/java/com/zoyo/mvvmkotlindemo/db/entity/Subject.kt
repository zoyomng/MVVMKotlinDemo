package com.zoyo.mvvmkotlindemo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */

data class Subject(

    val id: Int,

    val subjectTitle: String,

    val subjectDesc: String = "",

    val destinationId: Int = 0
)