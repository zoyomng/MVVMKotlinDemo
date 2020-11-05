package com.zoyo.mvvmkotlindemo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */

@Entity
data class Subject(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "subject_title")
    val subjectTitle: String,

    @ColumnInfo(name = "subject_desc")
    val subjectDesc: String = "",

    @ColumnInfo(name = "subject_order")
    val subjectOrder: Int?,

    @ColumnInfo(name = "destination_id")
    val destinationId: Int?
)