package com.example.roomdatabase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriber_dataTable")
data class SubscriberEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_id")
    var id: Int,
    @ColumnInfo(name = "subscriber_name")
    var name: String,
    @ColumnInfo(name = "subscriber_email")
    var email: String
)
